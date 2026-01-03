<template>
  <div class="cart-page">
    <section class="cart-header">
      <h3 v-if="cart.numberOfItems == 0">Your cart is empty.</h3>
      <h3 v-else-if="cart.numberOfItems == 1">
        Your shopping cart contains 1 item.
      </h3>
      <h3 v-else>
        Your shopping cart contains {{ cart.numberOfItems }} items.
      </h3>
    </section>
    <section v-if="cart.numberOfItems > 0" class="clear-cart-section">
      <button @click="$store.dispatch('clearCart')" class="clear-cart">
        Clear Cart
      </button>
    </section>
    <section class="cart-table">
      <CartTable></CartTable>
    </section>
    <section v-if="cart.numberOfItems > 0" class="cart-subtotal-price">
      <h3>Cart Subtotal: <Price :amount="cart.subtotal" /></h3>
    </section>
    <section class="buttons">
      <router-link
        v-if="selectedCategoryName == ''"
        to="/category/Adventure"
        class="continue-shopping-button"
      >
        Continue Shopping
      </router-link>
      <router-link
        v-else
        :to="'/category/' + selectedCategoryName"
        class="continue-shopping-button"
      >
        Continue Shopping
      </router-link>
      <button
        v-if="cart.numberOfItems > 0"
        class="checkout-button"
        @click="proceedToCheckout"
      >
        Proceed to Checkout
      </button>
    </section>
  </div>
</template>

<script>
import CartTable from "@/components/CartTable";
import Price from "@/components/Price";
import { mapState } from "vuex";

export default {
  name: "Cart",
  components: { CartTable, Price },
  watch: {
    $route: {
      immediate: true,
      handler(to) {
        document.title = to.meta.title || "Cart";
      },
    },
  },
  methods: {
    proceedToCheckout() {
      this.$router.push({ name: "checkout" });
    },
  },
  computed: {
    ...mapState(["cart", "selectedCategoryName"]),
  },
};
</script>

<style scoped>
.cart-page {
  padding: 1em;
  display: flex;
  flex-direction: column;
  gap: 1em;
}

.cart-header {
  text-align: center;
  color: #800000;
}

.clear-cart-section {
  align-self: center;
}

.clear-cart {
  border: 1px solid #575555;
  display: inline-block;
  background: var(--primary-color);
  color: white;
  text-decoration: none;
  padding: 0.5em 1em;
  cursor: pointer;
  width: 150px;
}

.clear-cart:hover {
  background-color: #a36302;
  cursor: pointer;
}

.cart-table {
  align-self: center;
}

.cart-subtotal-price {
  align-self: center;
  color: #800000;
}

.buttons {
  padding: 0.5em 1em;
  align-self: center;
}

.continue-shopping-button {
  border: 1px solid #575555;
  background: var(--primary-color);
  color: white;
  text-decoration: none;
  padding: 0.5em 1em;
  cursor: pointer;
  width: 200px;
}

.continue-shopping-button:hover {
  background-color: #a36302;
  cursor: pointer;
}

.checkout-button {
  border: 1px solid #575555;
  display: inline-block;
  background: #800000;
  color: white;
  text-decoration: none;
  padding: 0.5em 1em;
  cursor: pointer;
  width: 200px;
  margin: 5px;
}

.checkout-button:hover {
  background-color: #550000;
  cursor: pointer;
}
</style>
