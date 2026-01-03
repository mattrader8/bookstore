<template>
  <table class="confirmation-table">
    <tr>
      <th>Book</th>
      <th>Quantity</th>
      <th>Price</th>
    </tr>
    <tr
      v-for="(item, index) in this.orderDetails.lineItems"
      :key="item.productId"
    >
      <td>
        {{ orderDetails.books[index].title }}
      </td>
      <td>{{ item.quantity }}</td>
      <td>
        <Price :amount="orderDetails.books[index].price * item.quantity" />
      </td>
    </tr>
    <tr>
      <td colspan="2" class="delivery-surcharge">
        <i>-- Delivery Surcharge --</i>
      </td>
      <td class="price">
        <Price :amount="cart.surcharge" class="price" />
      </td>
    </tr>
    <tr>
      <td colspan="2" class="total">
        <strong class="total">Total</strong>
      </td>
      <td>
        <strong>
          <Price :amount="orderDetails.order.amount" />
        </strong>
      </td>
    </tr>
  </table>
</template>

<script>
import Price from "@/components/Price";
import { mapState } from "vuex";

export default {
  name: "ConfirmationTable",
  components: {
    Price,
  },
  computed: {
    ...mapState(["orderDetails", "cart"]),
  },
};
</script>

<style scoped>
.confirmation-table {
  border: 1px solid darkgray;
  border-collapse: collapse;
  background-color: #f6ecd3;
}

th {
  padding: 0.5em;
}

td {
  padding: 0.75em;
  border-top: 1px solid black;
  border-bottom: 1px solid black;
}

tr td:nth-child(1) {
  text-align: left;
}

tr td:nth-child(2) {
  text-align: center;
}

tr td:nth-child(3) {
  text-align: right;
}

.delivery-surcharge {
  text-align: center !important;
}

.price {
  text-align: right !important;
}

.total {
  text-align: center !important;
}
</style>
