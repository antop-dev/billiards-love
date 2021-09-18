<template>
  <v-card>
    <v-app-bar app fixed elevate-on-scroll color="deep-purple accent-4" dark>
      <v-app-bar-nav-icon></v-app-bar-nav-icon>
      <v-toolbar-title>{{ title }}</v-toolbar-title>
    </v-app-bar>
  </v-card>
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
