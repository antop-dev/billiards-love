<template>
  <div>
    <v-toolbar color="deep-purple accent-4" dark flat>
      <v-app-bar-nav-icon></v-app-bar-nav-icon>

      <v-toolbar-title>{{ title }}</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn icon>
        <v-icon to="dashboard">home</v-icon>
      </v-btn>

      <template v-slot:extension>
        <v-tabs v-model="tab" align-with-title>
          <v-tabs-slider color="red"></v-tabs-slider>
          <v-tab id="info" :to="{ name: 'info', params: { id } }">정보</v-tab>
          <v-tab id="rank" :to="{ name: 'rank', params: { id } }">순위표</v-tab>
          <v-tab id="match" :to="{ name: 'chart', params: { id } }"
            >대진표</v-tab
          >
        </v-tabs>
      </template>
    </v-toolbar>

    <v-tabs-items v-model="tab">
      <v-tab-item v-for="item in items" :key="item">
        <v-card flat>
          <v-card-text v-text="text"></v-card-text>
        </v-card>
      </v-tab-item>
    </v-tabs-items>
  </div>
</template>
<script>
export default {
  name: 'GameTabs',
  props: {
    title: String,
  },
  data() {
    return {
      tab: null,
      items: ['web', 'shopping', 'videos', 'images', 'news'],
    };
  },
  created() {
    // this.id = this.$route.params.id;
  },
};
</script>

<style scoped></style>
