<template>
  <div class="viewport">
    <md-toolbar :class="gameStatus" :md-elevation="0">
      <span class="material-icons">polymer</span>
      <span class="md-subheading">{{ title }}</span>
    </md-toolbar>
  </div>
</template>

<script>
export default {
  name: 'ContentsHeader',
  props: {
    title: String,
    isOpen: Boolean,
  },
  methods: {
    gameStatus: () => {
      console.log(this.isOpen);
      if (this.isOpen) {
        return 'md-transparent';
      }
      return 'md-accent';
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
