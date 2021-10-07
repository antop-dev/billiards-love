<template>
  <div>
    <game-tabs :title="title"></game-tabs>
    <div>
      <v-sheet>
        <v-container>
          <v-card min-height="200">
            <router-view></router-view>
          </v-card>
        </v-container>
      </v-sheet>
    </div>
  </div>
</template>

<script>
import GameTabs from '@/status/GameTabs';
import ContestApi from '../api/contest.api';
export default {
  name: 'GameStatus',
  components: { GameTabs },
  data() {
    return {
      id: '',
      title: '',
    };
  },
  methods: {},
  async created() {
    let query = this.$route.query;
    if (query) {
      // 아이디 받아오면 처리
      this.id = query.id;
      try {
        const contest = await ContestApi.inquire_contest(query.id);
        this.$store.dispatch('saveMatchInfo', contest);
        this.title = contest.title;
      } catch (e) {
        console.error(e);
      } finally {
        await this.$router.push({ name: 'info', params: { id: this.id } });
      }
    }
  },
};
</script>

<style scoped></style>
