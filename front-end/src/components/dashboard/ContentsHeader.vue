<template>
  <div class="viewport">
    <md-toolbar class="md-accent" :md-elevation="0">
      <span class="material-icons">polymer</span>
      <span class="md-subheading">8월 챔프전</span>
    </md-toolbar>
  </div>
</template>

<script>
export default {
  name: 'ContentsHeader',
};
</script>

<style scoped>
.viewport {
  min-width: 100%;
  display: inline-block;
  vertical-align: top;
  overflow: auto;
}
</style>
