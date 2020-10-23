<template>
  <div class="contents">
    <md-content class="md-elevation-2">
      <ContentsHeader></ContentsHeader>
      <div>
        <md-list class="md-double-line">
          <md-list-item>
            <div class="md-list-item-text">
              <h1>1위</h1>
            </div>
            <md-button class="md-list-action">
              <md-icon>arrow_upward</md-icon>
            </md-button>
          </md-list-item>
        </md-list>
      </div>
    </md-content>
  </div>
</template>
<script>
import ContentsHeader from './ContentsHeader';
export default {
  name: 'BoardContents',
  components: { ContentsHeader },
};
</script>

<style scoped>
.contents {
  padding: 6px;
}
</style>
