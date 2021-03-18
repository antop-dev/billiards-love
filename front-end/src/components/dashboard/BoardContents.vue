<template>
  <div class="contents">
    <md-content class="md-elevation-5">
      <ContentsHeader :title="title" :state="state"></ContentsHeader>
      <div>
        <div class="md-layout md-gutter">
          <div
            class="md-layout-item md-layout md-gutter
            md-alignment-center-center"
          >
            <div class="md-layout-item">
              <h1>{{ rank }}위</h1>
            </div>
            <div class="md-layout-item">
              <md-icon class="md-size-2x">arrow_upward</md-icon>
            </div>
          </div>
          <div
            class="md-layout-item md-layout md-gutter
            md-alignment-center-center"
          >
            <md-content>
              <span>210 점</span>
              <br />
              <span>89%</span>
              <br />
              <span>(110 / 220)</span>
            </md-content>
          </div>
        </div>
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
  created() {
    this.rank = 1;
  },
};
</script>

<style scoped>
.contents {
  padding: 6px;
  cursor: pointer;
}
.md-layout {
  padding: 10px;
}
</style>
