<template>
  <div>
    <md-tabs md-sync-route md-alignment="fixed">
      <md-tab to="/game/info" id="info" md-label="정보" exact></md-tab>
      <md-tab to="/game/rank" id="rank" md-label="순위표"></md-tab>
      <md-tab id="match" md-label="대진표"></md-tab>
    </md-tabs>
  </div>
</template>
<script>
export default {
  name: 'GameTabs',
};
</script>

<style scoped></style>
