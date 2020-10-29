<template>
  <div>
    <game-tabs></game-tabs>
    <router-view></router-view>
  </div>
</template>

<script>
import GameTabs from '../components/status/GameTabs';
export default {
  name: 'GameStatus',
  components: { GameTabs },
  methods: {},
};
</script>

<style scoped></style>
