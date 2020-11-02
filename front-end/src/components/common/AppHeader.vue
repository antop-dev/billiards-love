<template>
  <md-toolbar class="md-primary">
    <div class="md-toolbar-row">
      <div class="md-layout-item">
        <back-button></back-button>
      </div>
      <div class="md-layout-item title-align-center">
        <span class="md-title">My Title</span>
      </div>
      <div class="md-layout-item text-align-right">
        <close-button></close-button>
      </div>
    </div>
  </md-toolbar>
</template>

<script>
import CloseButton from './CloseButton';
import BackButton from './BackButton';

export default {
  name: 'AppHeader',
  components: { CloseButton, BackButton },
};
</script>

<style scoped>
.title-align-center {
  text-align: center;
}
.text-align-right {
  text-align: right;
}
</style>
