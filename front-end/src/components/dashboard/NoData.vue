<template>
  <div class="contents">
    <md-content class="md-elevation-2">
      <ContentsHeader
        is-no-data="true"
        title="데이터가 없습니다"
      ></ContentsHeader>
    </md-content>
  </div>
</template>
<script>
import ContentsHeader from './ContentsHeader';
export default {
  name: 'BoardContents',
  props: {
    title: String,
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
