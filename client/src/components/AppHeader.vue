<template>
  <header class="container">
    <section class="bookstore-logo">
      <router-link to="/">
        <img
          src="@/assets/images/site/logo.png"
          alt="Good Books Logo"
          width="150px"
          height="auto"
        />
      </router-link>
      <router-link to="/">
        <h1 class="text-logo">Good Books</h1>
      </router-link>
    </section>
    <section>
      <div class="search-form">
        <input type="text" class="search-bar" placeholder="Search..." />
        <router-link to="/category/Fantasy">
          <div class="search-icon">
            <img src="@/assets/images/site/search.png" />
          </div>
        </router-link>
      </div>
    </section>
    <section class="header-dropdown-and-cart">
      <button class="login-button">Hi Matt!</button>
      <router-link to="/cart">
        <div class="shopping-cart-icon">
          <div class="shopping-cart">
            <img src="@/assets/images/site/shopping-cart.png" />
          </div>
          <div class="cart-count">{{ cart.numberOfItems }}</div>
        </div>
      </router-link>
      <header-dropdown-menu></header-dropdown-menu>
    </section>
  </header>
</template>

<script>
import HeaderDropdownMenu from "@/components/HeaderDropdown";
import { mapState } from "vuex";

export default {
  name: "AppHeader",
  components: { HeaderDropdownMenu },
  component: {
    HeaderDropdownMenu,
  },
  computed: {
    ...mapState(["cart"]),
  },
};
</script>

<style scoped>
header {
  background: var(--secondary-background-color);
  display: flex;
  align-items: center;
  padding: 1em;
  gap: 3em;
}

.bookstore-logo {
  display: flex;
  align-items: center;
  min-width: 20rem;
}

.text-logo {
  font-family: var(--title-font-family);
  color: white;
  font-size: 40px;
  text-decoration: none !important;
}

.search-form {
  position: relative;
  display: flex;
  align-items: center;
}

.search-bar {
  padding: 0.5em;
  width: 320px;
  border: 2px solid white;
  border-radius: 5px;
  outline: 0;
  background-color: #800000;
  color: white;
}

::placeholder {
  color: white;
}

.search-icon {
  position: absolute;
  top: 6px;
  right: 8px;
  width: 25px;
}

.login-button {
  background: none;
  border: none;
  margin: 0;
  padding: 0;
  font-family: var(--default-font-family);
  font-size: 30px;
  color: white;
  cursor: pointer;
}

.login-button:hover,
.login-button:active {
  color: rgb(150, 141, 141);
}

.shopping-cart-icon {
  text-align: center;
  padding-top: 20px;
  cursor: pointer;
}

.cart-count {
  color: white;
  transform: translateY(-59px);
}

.header-dropdown-and-cart {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 1em;
}

a {
  text-decoration: none;
}

@media (max-width: 930px) {
  header {
    flex-direction: column;
  }
}
</style>
