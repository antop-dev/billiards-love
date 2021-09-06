<template>
  <div>
    <game-tabs :id="id"></game-tabs>
    <div>
      <v-sheet>
        <v-container>
          <v-card>
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
      name: '',
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
        this.name = contest.name;
      } catch (e) {
        // alert(e.message);
        // await this.$router.back();
      } finally {
        await this.$router.push({ name: 'info', params: { id: this.id } });
      }
    }
  },
};
</script>

<style scoped></style>
