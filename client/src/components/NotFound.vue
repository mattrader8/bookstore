<template>
  <div class="not-found-page">
    <section class="header">
      <h3>The page you were looking for was not found.</h3>
    </section>
    <section class="return-home">
      <router-link to="/" class="return-home-button">
        Return to Home Page
      </router-link>
    </section>
  </div>
</template>

<script>
export default {
  name: "NotFound",
  watch: {
    $route: {
      immediate: true,
      handler(to) {
        document.title = to.meta.title || "Page Not Found";
      },
    },
  },
};
</script>

<style scoped>
.not-found-page {
  padding: 1em;
  display: flex;
  flex-direction: column;
  gap: 1em;
}

.header {
  text-align: center;
  color: #800000;
}

.return-home {
  align-self: center;
}

.return-home-button {
  border: 1px solid #575555;
  display: inline-block;
  background: var(--primary-color);
  color: white;
  text-decoration: none;
  padding: 0.5em 1em;
  cursor: pointer;
  width: 200px;
}

.return-home-button:hover {
  background-color: #a36302;
  cursor: pointer;
}
</style>
