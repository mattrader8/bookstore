<template>
  <li class="book-box">
    <div class="book-image">
      <img
        :src="require('@/assets/images/books/' + bookImageFileName(book))"
        :alt="book.title"
      />
      <div class="read-now">
        <img
          class="read-now-background"
          v-if="book.isPublic"
          src="@/assets/images/site/read-now.png"
          title="Read Now"
        />
      </div>
    </div>
    <div class="book-data">
      <div class="book-title" :title="book.title">{{ book.title }}</div>
      <div class="book-author">{{ book.author }}</div>
      <div class="book-price">
        <Price :amount="book.price" />
      </div>
    </div>
    <div class="button-section">
      <button class="book-button" @click="$store.dispatch('addToCart', book)">
        Add to Cart
      </button>
    </div>
  </li>
</template>

<script>
import Price from "@/components/Price";

export default {
  name: "categoryBookListItem",
  props: {
    book: {
      type: Object,
      required: true,
    },
  },
  components: {
    Price,
  },
  methods: {
    bookImageFileName: function (book) {
      let name = book.title.toLowerCase();
      name = name
        .replace(/ /g, "")
        .replace(/'/g, "")
        .replace(/-/g, "")
        .replace(/\?/g, "");
      return `${name}.png`;
    },
  },
};
</script>

<style scoped>
.book-box {
  display: flex;
  flex-direction: column;
  background-color: var(--card-background-color);
  padding: 0.75em;
  width: 15em;
  gap: 0.25em;
}

.book-image {
  display: flex;
  position: relative;
  height: 200px;
  justify-content: center;
  padding-bottom: 0.5em;
}

.book-data {
  display: flex;
  flex-direction: column;
  margin-left: 10px;
}

.book-title {
  font-weight: bold;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
  text-decoration: underline;
}

.book-title:hover {
  cursor: pointer;
}

.book-author {
  font-style: italic;
  padding: 2.5px 0;
}

.book-price {
  color: #cb7902;
  font-weight: bold;
}

.button-section {
  margin: auto;
}

.book-button {
  background-color: #cb7902;
  color: white;
  padding: 0.5em 1em;
  border: 1px solid white;
  margin-top: 10px;
  width: 150px;
}

.book-button:hover {
  background-color: #a36302;
  cursor: pointer;
}

.read-now {
  position: absolute;
  top: 140px;
  right: 30px;
  cursor: pointer;
}

.read-now-background {
  background: #a9fdfd;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  text-align: center;
  line-height: 100px;
  vertical-align: middle;
  padding: 5px;
}
</style>
