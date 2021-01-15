<template>
  <div>
    <app-header></app-header>
    <div class="board">
      <board-contents @click.native="getDetail"></board-contents>
      <board-contents></board-contents>
      <board-contents></board-contents>
    </div>
  </div>
</template>
<script>
import BoardContents from '@/dashboard/BoardContents';
import AppHeader from '@/common/AppHeader';
export default {
  name: 'DashBoard',
  components: { BoardContents, AppHeader },
  methods: {
    getDetail() {
      this.$router.push('/game/info');
    },
  },
};
</script>

<style scoped>
.board {
  margin-top: 20px;
}
</style>
