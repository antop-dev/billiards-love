<template>
  <div>
    <app-header></app-header>
    <md-tabs md-sync-route md-alignment="fixed">
      <md-tab to="/game/info" id="info" md-label="정보" exact></md-tab>
      <md-tab to="/game/rank" id="rank" md-label="순위표"></md-tab>
      <md-tab id="match" md-label="대진표"></md-tab>
    </md-tabs>
  </div>
</template>
<script>
import AppHeader from '@/common/AppHeader';
export default {
  name: 'GameTabs',
  components: { AppHeader },
};
</script>

<style scoped></style>
