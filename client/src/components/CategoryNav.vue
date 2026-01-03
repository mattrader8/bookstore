<template>
  <nav class="category-nav">
    <ul class="category-buttons">
      <li
        v-for="category in categories"
        :key="category.categoryId"
        class="button"
        :class="
          category.name === selectedCategoryName
            ? 'selected-category-button'
            : 'unselected-category-button'
        "
      >
        <router-link :to="'/category/' + category.name">{{
          category.name
        }}</router-link>
      </li>
    </ul>
  </nav>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "CategoryNav",
  computed: {
    ...mapState(["categories", "selectedCategoryName"]),
  },
};
</script>

<style scoped>
.category-buttons {
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
  text-align: center;
  background-color: var(--neutral-color);
}

.button.selected-category-button {
  background-color: var(--primary-color);
}

.button.unselected-category-button,
.button.unselected-category-button:visited {
  background-color: var(--neutral-color);
}

.button.unselected-category-button:hover,
.button.unselected-category-button:active {
  background-color: var(--primary-color);
}

a,
a:active,
a:hover {
  color: white;
  text-decoration: none;
}
</style>
