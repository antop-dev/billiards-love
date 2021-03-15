<template>
  <div class="viewport">
    <md-toolbar :class="gameStatus()" :md-elevation="0">
      <div v-if="isNoData">
        <span class="md-subheading">{{ title }}</span>
      </div>
      <div v-else>
        <span class="material-icons">polymer</span>
        <span class="md-subheading">{{ title }}</span>
      </div>
    </md-toolbar>
  </div>
</template>

<script>
export default {
  name: 'ContentsHeader',
  props: {
    title: String,
    state: String,
    isNoData: String,
  },
  methods: {
    gameStatus: function() {
      if (this.state === '0' || this.state === '1') {
        return 'md-primary';
      } else if (this.state === '3' || this.state === '4') {
        return 'md-accent';
      } else {
        return 'md-transparent';
      }
    },
  },
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
