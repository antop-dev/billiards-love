<template>
  <div>
    <div>
      <game-tabs></game-tabs>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
import GameTabs from '@/status/GameTabs';
export default {
  name: 'GameStatus',
  components: { GameTabs },
  methods: {},
  created() {
    this.$router.push('/game/info');
  },
};
</script>

<style scoped></style>
