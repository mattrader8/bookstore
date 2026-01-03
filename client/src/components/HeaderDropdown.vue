<template>
  <div class="header-dropdown">
    <div class="menu-icon">
      <img
        class="menu"
        @click="toggleMenu"
        src="@/assets/images/site/menu.png"
      />
    </div>
    <ul class="menu-panel" :class="{ 'menu-open': openMenu }">
      <li
        class="menu-item"
        v-for="category in $store.state.categories"
        :key="category.categoryId"
      >
        <router-link :to="'/category/' + category.name">{{
          category.name
        }}</router-link>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "HeaderDropdownMenu",
  data: function () {
    return {
      openMenu: false,
    };
  },
  watch: {
    $route() {
      this.openMenu = false;
    },
  },
  methods: {
    toggleMenu() {
      this.openMenu = !this.openMenu;
    },
  },
  computed: {
    ...mapState(["categories"]),
  },
};
</script>

<style scoped>
.header-dropdown {
  position: relative;
  min-width: 2.5rem;
  cursor: pointer;
}

.menu-panel {
  position: absolute;
  right: -1.4rem;
  margin-top: 1rem;
  min-width: 7.8em;
  z-index: 1;
  transform: translateX(100%);
  transition-duration: 500ms;
  transition-property: transform;
  transition-timing-function: ease-in-out;
  background-color: #b35353;
  color: black;
}

.menu-open {
  transform: translate(0);
  min-width: 7.8em;
  z-index: 1;
  cursor: pointer;
}

.menu-item {
  padding: 0.25em 0.5em;
  color: white;
  text-decoration: none;
}

.menu-item:hover {
  background: #cb7902;
}

a,
a:active,
a:hover {
  color: white;
  text-decoration: none;
}
</style>
