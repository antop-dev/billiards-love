<template>
  <div>
    <!-- TODO Id -> title -->
    <app-header :title="id" back-btn="true"></app-header>
    <div>
      <game-tabs></game-tabs>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
import GameTabs from '@/status/GameTabs';
import AppHeader from '@/common/AppHeader';
export default {
  name: 'GameStatus',
  components: { AppHeader, GameTabs },
  data() {
    return {
      id: '',
    };
  },
  methods: {},
  created() {
    let query = this.$route.query;
    if (query) {
      // 아이디 받아오면 처리
      this.id = query.id;
      this.$router.push({ name: 'info', params: { id: this.id } });
    }
  },
};
</script>

<style scoped></style>
