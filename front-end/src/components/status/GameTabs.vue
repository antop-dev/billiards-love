<template>
  <md-tabs md-sync-route md-alignment="fixed">
    <md-tab to="/status/info" id="info" md-label="정보" exact></md-tab>
    <md-tab to="/status/rank" id="rank" md-label="순위표"></md-tab>
    <md-tab id="match" md-label="대진표"></md-tab>
  </md-tabs>
</template>
<script>
export default {
  name: 'GameTabs',
};
</script>

<style scoped></style>
