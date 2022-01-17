<template>
  <v-app-bar app fixed elevate-on-scroll dark>
    <v-app-bar-nav-icon></v-app-bar-nav-icon>
    <v-toolbar-title>{{ title }}</v-toolbar-title>
  </v-app-bar>
</template>

<script>
export default {
  name: 'AppHeader',
  props: {
    title: String,
  },
};
</script>

<style></style>
