<template>
  <div class="board">
    <board-contents @click.native="getDetail"></board-contents>
    <board-contents></board-contents>
    <board-contents></board-contents>
  </div>
</template>
<script>
import BoardContents from '@/dashboard/BoardContents';
export default {
  name: 'DashBoard',
  components: { BoardContents },
  methods: {
    getDetail() {
      this.$router.push('/status/info');
    },
  },
};
</script>

<style scoped>
.board {
  margin-top: 20px;
}
</style>
