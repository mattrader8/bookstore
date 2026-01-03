<template>
  <div class="home-page">
    <section class="shop-button">
      <router-link to="/category/Fantasy">
        <div class="shop-button">
          <button class="button">Shop Books</button>
        </div>
      </router-link>
    </section>
    <section class="hero-image-caption-container">
      <div class="hero-image-caption">
        <div class="hero-image-title">
          <b>Harry Potter and the Sorcerer's Stone</b>
        </div>
        <div class="hero-image-author">by J.K. Rowling</div>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: "Home",
  watch: {
    $route: {
      immediate: true,
      handler(to) {
        document.title = to.meta.title || "Home";
      },
    },
  },
};
</script>

<style scoped>
.home-page {
  display: flex;
  flex-direction: column;
  background-image: url("../assets/images/site/hero-image.jpg");
  position: relative;
  background-size: cover;
  background-repeat: no-repeat;
  background-size: 100% 100%;
}

.shop-button {
  padding: 10em 1.5vw;
  text-align: right;
}

.button {
  background-color: #cb7902;
  color: white;
  width: 160px;
  height: 50px;
  border: 1px solid white;
}

.button:hover {
  background-color: #a36302;
}

.hero-image-caption-container {
  position: absolute;
  left: 35px;
  bottom: 15px;
  text-align: left;
}

.hero-image-caption {
  display: flex;
  flex-direction: column;
  font-family: var(--default-font-family);
  color: white;
}
</style>
