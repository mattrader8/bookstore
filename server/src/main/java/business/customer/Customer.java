package business.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;

public class Customer
{

    private long customerId;
    private String customerName;
    private String address;
    private String phone;
    private String email;
    private String ccNumber;
    private Date ccExpDate;

    public Customer(long customerId, String customerName, String address, String phone, String email, String ccNumber,
                    Date ccExpDate)
    {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.ccNumber = ccNumber;
        this.ccExpDate = ccExpDate;
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() { return address; }

    public String getPhone() { return phone; }

    public String getEmail() { return email; }

    @JsonIgnore
    public String getCcNumber() { return ccNumber; }

    public String getCcNumberForDisplay()
    {

        String newCreditCard = ccNumber.replaceAll("[\\s-]+", "");
        String newCcNumber = "";
        String lastFourDigits = "";

        for(int i = 0; i <= newCreditCard.length() - 5; i++) {
            newCcNumber += "*";
        }

        for(int i = newCreditCard.length() - 4; i < newCreditCard.length(); i++ ) {
            lastFourDigits += newCreditCard.charAt(i);
        }

        return newCcNumber + lastFourDigits;
    }

    public Date getCcExpDate() { return ccExpDate; }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", ccNumber='" + ccNumber + '\'' +
                ", ccExpDate=" + ccExpDate +
                '}';
    }
}
