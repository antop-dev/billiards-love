<template>
  <md-toolbar class="md-primary">
    <div class="md-toolbar-row">
      <div class="md-toolbar-section-start">
        <back-button v-if="backBtn"></back-button>
        <menu-button v-if="menuBtn"></menu-button>
      </div>
      <div class="md-layout-item title-align-center">
        <span class="md-title">{{ title }}</span>
      </div>
      <div class="md-toolbar-section-end text-align-right">
        <close-button v-if="closeBtn"></close-button>
      </div>
    </div>
  </md-toolbar>
</template>

<script>
import CloseButton from './CloseButton';
import BackButton from './BackButton';
import MenuButton from './MenuButton';

export default {
  name: 'AppHeader',
  props: {
    title: String,
    closeBtn: String,
    backBtn: String,
    menuBtn: String,
  },
  components: { CloseButton, BackButton, MenuButton },
};
</script>

<style>
.title-align-center {
  text-align: center;
}
.text-align-right {
  text-align: right;
}
</style>
