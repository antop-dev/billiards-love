<template>
  <md-toolbar class="md-primary">
    <div class="md-toolbar-row">
      <div class="md-layout-item">
        <back-button></back-button>
      </div>
      <div class="md-layout-item title">
        <span class="md-title">My Title</span>
      </div>
      <div class="md-layout-item left-btn">
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
.title {
  text-align: center;
}
.left-btn {
  text-align: right;
}
</style>
