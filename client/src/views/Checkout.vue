<template>
  <div class="checkout-page">
    <section class="empty-checkout" v-if="cart.empty">
      <h3 class="header">Please add an item before checking out.</h3>
      <router-link v-if="selectedCategoryName == ''" to="/category/Adventure">
        <button class="continue-shopping-button">Continue Shopping</button>
      </router-link>
    </section>

    <section class="checkout-page-body" v-if="!cart.empty">
      <form class="form" @submit.prevent="submitOrder">
        <div class="form-element">
          <div class="label-and-field">
            <label for="name">Name</label>
            <input
              type="text"
              size="20"
              id="name"
              name="name"
              v-model.lazy="$v.name.$model"
            />
          </div>
          <template v-if="$v.name.$error">
            <span class="error" v-if="!$v.name.required"
              >Name is required.</span
            >
            <span class="error" v-else-if="!$v.name.minLength">
              Name must have at least
              {{ $v.name.$params.minLength.min }} letters.
            </span>
            <span class="error" v-else-if="!$v.name.maxLength">
              Name can have at most
              {{ $v.name.$params.maxLength.max }} letters.
            </span>
            <span class="error" v-else> An unexpected error occurred. </span>
          </template>
        </div>

        <div class="form-element">
          <div class="label-and-field">
            <label for="address">Address</label>
            <input
              type="text"
              size="20"
              id="address"
              name="address"
              v-model.lazy="$v.address.$model"
            />
          </div>
          <template v-if="$v.address.$error">
            <span class="error" v-if="!$v.address.required"
              >Address is required.</span
            >
            <span class="error" v-else-if="!$v.address.minLength">
              Address must have at least
              {{ $v.address.$params.minLength.min }} letters.
            </span>
            <span class="error" v-else-if="!$v.address.maxLength">
              Address can have at most
              {{ $v.address.$params.maxLength.max }} letters.
            </span>
            <span class="error" v-else> An unexpected error occurred. </span>
          </template>
        </div>

        <div class="form-element">
          <div class="label-and-field">
            <label for="phone">Phone</label>
            <input
              class="textField"
              type="text"
              size="20"
              id="phone"
              name="phone"
              v-model.lazy="$v.phone.$model"
            />
          </div>
          <template v-if="$v.phone.$error">
            <span class="error" v-if="!$v.phone.required"
              >Phone number is required.</span
            >
            <span class="error" v-else-if="!$v.phone.phone"
              >Please enter a valid phone number.</span
            >
            <span class="error" v-else>An unexpected error occured.</span>
          </template>
        </div>

        <div class="form-element">
          <div class="label-and-field">
            <label for="email">Email</label>
            <input
              type="text"
              size="20"
              id="email"
              name="email"
              v-model.lazy="$v.email.$model"
            />
          </div>
          <template v-if="$v.email.$error">
            <span class="error" v-if="!$v.email.required"
              >Email is required.</span
            >
            <span class="error" v-else-if="!$v.email.email"
              >Please enter a valid email.</span
            >
            <span class="error" v-else> An unexpected error occurred. </span>
          </template>
        </div>

        <div class="form-element">
          <div class="label-and-field">
            <label for="ccNumber">Credit Card</label>
            <input
              type="text"
              size="20"
              id="ccNumber"
              name="ccNumber"
              v-model.lazy="$v.ccNumber.$model"
            />
          </div>
          <template v-if="$v.ccNumber.$error">
            <span class="error" v-if="!$v.ccNumber.required"
              >Credit card is required.</span
            >
            <span class="error" v-else-if="!$v.ccNumber.creditCard"
              >Please enter a valid credit card.</span
            >
            <span class="error" v-else> An unexpected error occurred. </span>
          </template>
        </div>

        <div class="form-element">
          <div class="label-and-field">
            <label>Expiration Date</label>

            <select class="expiration-dropdown" v-model="ccExpiryMonth">
              <option
                v-for="(month, index) in months"
                :key="index"
                :value="index + 1"
              >
                {{ month }} ({{ index + 1 }})
              </option>
            </select>
            &nbsp;
            <select class="expiration-dropdown" v-model="ccExpiryYear">
              <option
                v-for="index in 15"
                :key="index"
                :value="yearFrom(index - 1)"
              >
                {{ yearFrom(index) - 1 }}
              </option>
            </select>
          </div>
        </div>

        <section class="checkout-details">
          Your credit card will be charged:
          <b><Price :amount="cart.subtotal + cart.surcharge" /></b>.
          <br />
          <i
            >(Subtotal: <b><Price :amount="cart.subtotal" /></b> + Surcharge:
            <b><Price :amount="cart.surcharge" /></b>)</i
          >
        </section>

        <input
          type="submit"
          name="submit"
          class="checkout-button"
          :disabled="checkoutStatus === 'PENDING'"
          value="Complete Purchase"
        />
      </form>

      <section v-show="checkoutStatus !== ''" class="checkoutStatusBox">
        <div v-if="checkoutStatus === 'ERROR'">
          Error: Please fix the errors and try again.
        </div>

        <div v-else-if="checkoutStatus === 'PENDING'">Processing...</div>

        <div v-else-if="checkoutStatus === 'OK'">Order placed...</div>

        <div v-else>An unexpected error occurred, please try again.</div>
      </section>
    </section>
  </div>
</template>

<script>
import {
  required,
  email,
  minLength,
  maxLength,
} from "vuelidate/lib/validators";
import Price from "@/components/Price";
import isCreditCard from "validator/lib/isCreditCard";
import isMobilePhone from "validator/lib/isMobilePhone";
import { mapState } from "vuex";

const phone = (value) => isMobilePhone(value, "en-US");
const creditCard = (value) => isCreditCard(value);

export default {
  components: {
    Price,
  },
  watch: {
    $route: {
      immediate: true,
      handler(to) {
        document.title = to.meta.title || "Checkout";
      },
    },
  },
  data() {
    return {
      name: "",
      address: "",
      phone: "",
      email: "",
      ccNumber: "",
      ccExpiryMonth: new Date().getMonth() + 1,
      ccExpiryYear: new Date().getFullYear(),
      checkoutStatus: "",
    };
  },
  validations: {
    name: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(45),
    },
    address: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(45),
    },
    phone: {
      required,
      phone,
    },
    email: {
      required,
      email,
    },
    ccNumber: {
      required,
      creditCard,
    },
  },

  computed: {
    cart() {
      return this.$store.state.cart;
    },
    months() {
      return [
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December",
      ];
    },
    ...mapState(["selectedCategoryName"]),
  },

  methods: {
    submitOrder() {
      console.log("Submit order");
      this.$v.$touch(); // Ensures validators always run
      if (this.$v.$invalid) {
        this.checkoutStatus = "ERROR";
      } else {
        this.checkoutStatus = "PENDING";
        setTimeout(() => {
          this.checkoutStatus = "OK";
          this.$store
            .dispatch("placeOrder", {
              name: this.name,
              address: this.address,
              phone: this.phone,
              email: this.email,
              ccNumber: this.ccNumber,
              ccExpiryMonth: this.ccExpiryMonth,
              ccExpiryYear: this.ccExpiryYear,
            })
            .then(() => {
              this.checkoutStatus = "OK";
              this.$router.push({ name: "confirmation" });
            })
            .catch((reason) => {
              this.checkoutStatus = "SERVER_ERROR";
              console.log("Error placing order", reason);
            });
        }, 2000);
      }
    },
    yearFrom(index) {
      return new Date().getFullYear() + index;
    },
  },
};
</script>

<style scoped>
.checkout-page {
  display: flex;
  justify-content: center;
  background: white;
  color: #800000;
}

.checkout-page-body {
  display: flex;
  padding: 1em;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 1em;
}

.form-element {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}

.label-and-field label {
  padding-right: 0.5em;
  font-weight: bold;
}

.label-and-field input {
  background-color: var(--card-background-color);
  font-weight: bold;
}

.expiration-dropdown,
option {
  background-color: var(--card-background-color);
  font-weight: bold;
}

.error {
  color: red;
  font-weight: bold;
  font-style: italic;
  text-align: right;
}

.checkout-details {
  text-align: center;
}

.checkoutStatusBox {
  margin: 1em;
  padding: 1em;
  color: red;
  font-weight: bold;
  text-align: center;
}

.empty-checkout {
  text-align: center;
  padding: 1em;
}

.header {
  color: #800000;
}

.continue-shopping-button {
  border: 1px solid #575555;
  background: var(--primary-color);
  color: white;
  text-decoration: none;
  padding: 0.5em 1em;
  margin-top: 3em;
  margin-bottom: 0.5em;
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
  align-self: center;
  background: var(--primary-color);
  color: white;
  text-decoration: none;
  padding: 0.5em 1em;
  cursor: pointer;
  width: 180px;
  margin: 5px;
}

.checkout-button:hover {
  background-color: #a36302;
  cursor: pointer;
}
</style>
