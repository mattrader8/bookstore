<template>
  <table v-if="cart.numberOfItems > 0" class="cart-table">
    <tr v-for="item in cart.items" :key="item.bookId">
      <td>
        <img
          :src="
            require('@/assets/images/books/' +
              bookImageFileName(item.book.title))
          "
          :alt="item.book.title"
          class="book-image"
        />
      </td>
      <td>
        <b>{{ item.book.title }}</b>
      </td>
      <td>
        <i>
          <Price :amount="item.book.price" />
        </i>
      </td>
      <td>
        <span v-if="item.quantity > 1">(x{{ item.quantity }})</span>
      </td>
      <td>
        <div class="quantity-buttons">
          <img
            class="plus-button"
            src="@/assets/images/site/plus.png"
            alt="plus-button"
            @click="updateCart(item.book, item.quantity + 1)"
          />
          <img
            class="minus-button"
            src="@/assets/images/site/minus.png"
            alt="minus-button"
            @click="updateCart(item.book, item.quantity - 1)"
          />
        </div>
      </td>
    </tr>
  </table>
</template>

<script>
import Price from "@/components/Price";
import { mapState } from "vuex";

export default {
  name: "CartTable",
  components: {
    Price,
  },
  methods: {
    bookImageFileName: function (title) {
      let name = title.toLowerCase();
      name = name
        .replace(/ /g, "")
        .replace(/'/g, "")
        .replace(/-/g, "")
        .replace(/\?/g, "");
      return `${name}.png`;
    },
    updateCart: function (book, quantity) {
      this.$store.dispatch("updateCart", { book, quantity });
    },
  },
  computed: {
    ...mapState(["cart"]),
  },
};
</script>

<style scoped>
table {
  border: 1px solid darkgray;
  border-collapse: collapse;
}

td {
  padding: 0.5em;
}

tr td:nth-child(2) {
  text-align: left;
  max-width: 12em;
}

tr td:nth-child(3) {
  text-align: right;
}

tr td:nth-child(4) {
  text-align: left;
}

tr td:nth-child(5) {
  text-align: right;
}

table tr:nth-child(odd) {
  background-color: white;
}

table tr:nth-child(even) {
  background-color: var(--card-background-color);
}

.book-image {
  width: 80px;
  height: 130px;
  align-self: center;
}

.plus-button {
  margin: 5px 0;
}

.minus-button {
  margin: 5px 0;
}

.plus-button:hover {
  background: darkgray;
  cursor: pointer;
}

.minus-button:hover {
  background: darkgray;
  cursor: pointer;
}
</style>
