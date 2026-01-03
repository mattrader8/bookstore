<template>
  <footer class="container">
    <section class="copy-right">
      <h6>All Rights Reserved 2021 @Good Books</h6>
    </section>
    <section class="links">
      <router-link to="/">about</router-link>
      <router-link to="/">contact</router-link>
      <router-link to="/">directions</router-link>
    </section>
    <section class="social-media-icons">
      <router-link to="/">
        <div class="facebook-icon">
          <img src="@/assets/images/site/facebook.png" />
        </div>
      </router-link>
      <router-link to="/">
        <div class="twitter-icon">
          <img src="@/assets/images/site/twitter.png" />
        </div>
      </router-link>
    </section>
  </footer>
</template>

<script>
export default {
  name: "AppFooter",
};
</script>

<style scoped>
footer {
  background: var(--secondary-background-color);
  display: flex;
  justify-content: space-between;
  padding: 0.25em;
}

.copy-right {
  align-self: flex-start;
  color: white;
  padding-left: 18px;
  flex-basis: 25%;
}

.links {
  display: flex;
  align-self: center;
  gap: 0.5em;
}

.social-media-icons {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  padding-right: 18px;
  gap: 0.8em;
  flex-basis: 25%;
}

.facebook-icon {
  width: 25px;
}

.twitter-icon {
  width: 25px;
  border-radius: 3px;
}

@media (max-width: 907px) {
  footer {
    flex-direction: column;
    gap: 1em;
  }
}
</style>
