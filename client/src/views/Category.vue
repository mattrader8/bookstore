<template>
  <div>
    <category-nav></category-nav>
    <section class="category-header">
      <h1>{{ selectedCategoryName }} Books</h1>
    </section>
    <category-book-list></category-book-list>
  </div>
</template>

<script>
import CategoryNav from "@/components/CategoryNav";
import CategoryBookList from "@/components/CategoryBookList";
import { mapState } from "vuex";

export default {
  name: "category",
  components: {
    CategoryNav,
    CategoryBookList,
  },
  created: function () {
    const self = this;
    this.$store.dispatch("selectCategory", this.$route.params.name);
    this.$store.dispatch("fetchSelectedCategoryBooks").catch(function () {
      self.$router.push("/404");
    });
  },
  watch: {
    $route: {
      immediate: true,
      handler(to) {
        document.title = to.meta.title || "Categories";
      },
    },
  },
  computed: {
    ...mapState(["selectedCategoryName"]),
  },
};
</script>

<style scoped>
.category-header {
  padding: 8px;
  padding-top: 30px;
  padding-left: 170px;
  color: #800000;
}
</style>
