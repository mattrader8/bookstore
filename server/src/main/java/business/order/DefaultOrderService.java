package business.order;

import api.ApiException;
import business.BookstoreDbException;
import business.JdbcUtils;
import business.book.Book;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.cart.ShoppingCartItem;
import business.customer.Customer;
import business.customer.CustomerDao;
import business.customer.CustomerForm;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService
{

	private BookDao bookDao;
	private OrderDao orderDao;
	private LineItemDao lineItemDao;
	private CustomerDao customerDao;

	private Random random = new Random();

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}
	public void setOrderDao(OrderDao orderDao) { this.orderDao = orderDao; }
	public void setLineItemDao(LineItemDao lineItemDao) { this.lineItemDao = lineItemDao; }
	public void setCustomerDao(CustomerDao customerDao) { this.customerDao = customerDao; }

	@Override
	public OrderDetails getOrderDetails(long orderId)
	{
		Order order = orderDao.findByOrderId(orderId);
		Customer customer = customerDao.findByCustomerId(order.getCustomerId());
		List<LineItem> lineItems = lineItemDao.findByOrderId(orderId);
		List<Book> books = lineItems
				.stream()
				.map(lineItem -> bookDao.findByBookId(lineItem.getBookId()))
				.collect(Collectors.toList());
		return new OrderDetails(order, customer, lineItems, books);
	}

	@Override
    public long placeOrder(CustomerForm customerForm, ShoppingCart cart)
	{

		validateCustomer(customerForm);
		validateCart(cart);

		try (Connection connection = JdbcUtils.getConnection())
		{
			Date date = getDate(
					customerForm.getCcExpiryMonth(),
					customerForm.getCcExpiryYear());
			return performPlaceOrderTransaction(
					customerForm.getName(),
					customerForm.getAddress(),
					customerForm.getPhone(),
					customerForm.getEmail(),
					customerForm.getCcNumber(),
					date, cart, connection);
		}
		catch (SQLException e) {
			throw new BookstoreDbException("Error during close connection for customer order", e);
		}
	}

	private long performPlaceOrderTransaction(
			String name, String address, String phone,
			String email, String ccNumber, Date date,
			ShoppingCart cart, Connection connection)
	{
		try
		{
			connection.setAutoCommit(false);
			long customerId = customerDao.create(
					connection, name, address, phone, email,
					ccNumber, date);
			long customerOrderId = orderDao.create(
					connection,
					cart.getComputedSubtotal() + cart.getSurcharge(),
					generateConfirmationNumber(), customerId);
			for (ShoppingCartItem item : cart.getItems())
			{
				lineItemDao.create(connection, customerOrderId,
						item.getBookId(), item.getQuantity());
			}
			connection.commit();
			return customerOrderId;
		}
		catch (Exception e)
		{
			try
			{
				connection.rollback();
			}
			catch (SQLException e1)
			{
				throw new BookstoreDbException("Failed to roll back transaction", e1);
			}
			return 0;
		}
	}

	private void validateCustomer(CustomerForm customerForm)
	{

    	String name = customerForm.getName();
    	String address = customerForm.getAddress();
    	String phone = customerForm.getPhone();
    	String email = customerForm.getEmail();
    	String creditCard = customerForm.getCcNumber();

		if (name == null || name.equals("") || name.length() < 4 || name.length() > 45)
		{
			throw new ApiException.InvalidParameter("Invalid name field");
		}

		if (address == null || address.equals("") || address.length() < 4 || address.length() > 45)
		{
			throw new ApiException.InvalidParameter("Invalid address field");
		}

		if (phone == null || phone.equals("")) {
			throw new ApiException.InvalidParameter("Invalid phone field");
		}
		else
		{
			String newPhone = phone.replaceAll("[()\\s-]+", "");
			if (newPhone.length() != 10)
			{
				throw new ApiException.InvalidParameter("Invalid phone field");
			}
		}

		if (email == null || email.equals("") || email.matches(".*\\s.*") || !email.contains("@")
				|| email.charAt(email.length() - 1) == '.')
		{
			throw new ApiException.InvalidParameter("Invalid email field");
		}

		if (creditCard == null || creditCard.equals(""))
		{
			throw new ApiException.InvalidParameter("Invalid credit card field");
		}
		else
		{
			String newCreditCard = creditCard.replaceAll("[\\s-]+", "");
			if (newCreditCard.length() < 14 || newCreditCard.length() > 16)
			{
				throw new ApiException.InvalidParameter("Invalid credit card field");
			}
		}

		if (customerForm.getCcExpiryMonth() == null || customerForm.getCcExpiryMonth() == ""
				|| customerForm.getCcExpiryYear() == null || customerForm.getCcExpiryYear() == "")
		{
			throw new ApiException.InvalidParameter("Invalid expiry date");
		}

		if (expiryDateIsInvalid(customerForm.getCcExpiryMonth(), customerForm.getCcExpiryYear()))
		{
			throw new ApiException.InvalidParameter("Invalid expiry date");
		}
	}

	private boolean expiryDateIsInvalid(String ccExpiryMonth, String ccExpiryYear)
	{

		int newExpirationMonth = Integer.parseInt(ccExpiryMonth);
		int newExpirationYear = Integer.parseInt(ccExpiryYear);

		YearMonth yearMonth = YearMonth.now();

		return newExpirationMonth < 1 || newExpirationMonth > 12 || newExpirationYear < yearMonth.getYear()
				|| (newExpirationMonth < yearMonth.getMonthValue()
				&& newExpirationYear == yearMonth.getYear());
	}

	private void validateCart(ShoppingCart cart)
	{

		if (cart.getItems().size() <= 0)
		{
			throw new ApiException.InvalidParameter("Cart is empty.");
		}

		cart.getItems().forEach(item->
		{
			if (item.getQuantity() <= 0 || item.getQuantity() > 99)
			{
				throw new ApiException.InvalidParameter("Invalid quantity");
			}
			Book databaseBook = bookDao.findByBookId(item.getBookId());
			if (item.getBookForm().getPrice() != databaseBook.getPrice())
			{
				throw new ApiException.InvalidParameter("Invalid price");
			}
			if (item.getBookForm().getCategoryId() != databaseBook.getCategoryId())
			{
				throw new ApiException.InvalidParameter("Invalid category");
			}
		});
	}

	private Date getDate(String monthString, String yearString)
	{

		int monthInt = Integer.parseInt(monthString);
		int yearInt = Integer.parseInt(yearString);

		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.MONTH, monthInt - 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.YEAR, yearInt);

		Date date = calendar.getTime();

		return date;
	}

	private int generateConfirmationNumber()
	{

		return random.nextInt(999999999);
	}
}
