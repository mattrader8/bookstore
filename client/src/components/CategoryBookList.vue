<template>
  <ul>
    <category-book-list-item
      v-for="book in selectedCategoryBooks"
      :key="book.bookId"
      :book="book"
    ></category-book-list-item>
  </ul>
</template>

<script>
import CategoryBookListItem from "./CategoryBookListItem";
import { mapState } from "vuex";

export default {
  name: "CategoryBookList",
  components: {
    CategoryBookListItem,
  },
  computed: {
    ...mapState(["selectedCategoryBooks"]),
  },
};
</script>

<style scoped>
ul {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-evenly;
  padding: 1em;
  gap: 1em;
}
</style>
