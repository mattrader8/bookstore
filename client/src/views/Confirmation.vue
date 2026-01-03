<template>
  <div class="confirmation-page">
    <section v-if="!orderDetails" class="order-details-unavailable">
      <h3>No order details.</h3>
    </section>
    <section v-else class="order-details-available">
      <section class="confirmation-info">
        <ul>
          <li>
            Confirmation #:
            {{ orderDetails.order.confirmationNumber }}
          </li>
          <li>
            Date/Time:
            {{ new Date(orderDetails.order.dateCreated).toLocaleString() }}
          </li>
        </ul>
      </section>
      <section class="confirmation-table">
        <confirmation-table> </confirmation-table>
      </section>
      <section class="customer-info">
        <ul>
          <li>{{ orderDetails.customer.customerName }}</li>
          <li>{{ orderDetails.customer.address }}</li>
          <li>{{ orderDetails.customer.email }}</li>
          <li>{{ orderDetails.customer.phone }}</li>
          <li v-if="new Date(orderDetails.customer.ccExpDate).getMonth() < 9">
            {{ orderDetails.customer.ccNumberForDisplay }}
            (0{{ new Date(orderDetails.customer.ccExpDate).getMonth() + 1 }}/{{
              new Date(orderDetails.customer.ccExpDate).getFullYear()
            }})
          </li>
          <li v-else>
            {{ orderDetails.customer.ccNumberForDisplay }}
            ({{ new Date(orderDetails.customer.ccExpDate).getMonth() + 1 }}/{{
              new Date(orderDetails.customer.ccExpDate).getFullYear()
            }})
          </li>
        </ul>
      </section>
    </section>
    <section class="continue-shopping">
      <router-link to="/category/Adventure">
        <button class="continue-shopping-button">Continue Shopping</button>
      </router-link>
    </section>
  </div>
</template>

<script>
import ConfirmationTable from "@/components/ConfirmationTable";
import { mapState } from "vuex";

export default {
  name: "Confirmation",
  components: { ConfirmationTable },
  watch: {
    $route: {
      immediate: true,
      handler(to) {
        document.title = to.meta.title || "Confirmation";
      },
    },
  },
  computed: {
    ...mapState(["orderDetails", "selectedCategoryName"]),
  },
};
</script>

<style scoped>
.confirmation-page {
  padding: 1em;
  display: flex;
  flex-direction: column;
  gap: 1em;
}

.order-details-unavailable {
  text-align: center;
  color: #800000;
}

.order-details-available {
  display: flex;
  flex-direction: column;
  gap: 1em;
}

.confirmation-info {
  align-self: center;
  text-align: center;
  color: #800000;
}

.confirmation-table {
  align-self: center;
}

.customer-info {
  align-self: center;
  color: #800000;
}

.customer-info li {
  text-align: center;
}

.continue-shopping {
  align-self: center;
}

.continue-shopping-button {
  border: 1px solid #575555;
  display: inline-block;
  background: var(--primary-color);
  color: white;
  text-decoration: none;
  padding: 0.5em 1em;
  cursor: pointer;
  width: 200px;
  margin: 5px;
}

.continue-shopping-button:hover {
  background-color: #a36302;
  cursor: pointer;
}
</style>
