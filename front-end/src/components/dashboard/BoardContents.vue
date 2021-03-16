<template>
  <div class="contents">
    <md-content class="md-elevation-2">
      <ContentsHeader :title="title" :state="state"></ContentsHeader>
      <div>
        <md-list class="md-double-line">
          <md-list-item>
            <div class="md-list-item-text">
              <h1>{{ rank }}위</h1>
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
  props: {
    title: String,
    rank: Number,
    state: String,
  },
  components: { ContentsHeader },
};
</script>

<style scoped>
.contents {
  padding: 6px;
  cursor: pointer;
}
</style>
