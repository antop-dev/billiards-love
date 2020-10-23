<template>
  <div class="board">
    <board-contents></board-contents>
    <board-contents></board-contents>
    <board-contents></board-contents>
  </div>
</template>
<script>
import BoardContents from '../components/dashboard/BoardContents';
export default {
  name: 'DashBoard',
  components: { BoardContents },
};
</script>

<style scoped>
.board {
  margin-top: 20px;
}
</style>
