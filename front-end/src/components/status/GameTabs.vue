<template>
  <div>
    <md-tabs md-sync-route md-alignment="fixed">
      <md-tab
        :to="{ name: 'info', params: { id } }"
        id="info"
        md-label="정보"
        exact
      ></md-tab>
      <md-tab
        :to="{ name: 'rank', params: { id } }"
        id="rank"
        md-label="순위표"
      ></md-tab>
      <md-tab
        :to="{ name: 'chart', params: { id } }"
        id="match"
        md-label="대진표"
      ></md-tab>
    </md-tabs>
  </div>
</template>
<script>
export default {
  name: 'GameTabs',
  data() {
    return {
      id: '',
    };
  },
  created() {
    this.id = this.$route.params.id;
    console.log(this.id);
  },
};
</script>

<style scoped></style>
