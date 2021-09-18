<template>
  <div>
    <app-header title="대시보드" menu-btn="true"></app-header>
    <v-sheet>
      <v-container>
        <div v-if="showLoading" class="text-center">
          <v-progress-circular
            :size="70"
            :width="7"
            color="purple"
            indeterminate
          ></v-progress-circular>
        </div>
        <div v-else>
          <div v-if="contests.length > 0">
            <div
              class="board"
              v-for="content in contests"
              v-bind:key="content.id"
            >
              <board-contents
                :id="content.id"
                :title="content.title"
                :state="content.stateCode"
                @click.native="getDetail(content.id)"
              ></board-contents>
            </div>
          </div>
          <div v-else>
            <no-data></no-data>
          </div>
        </div>
      </v-container>
    </v-sheet>
  </div>
</template>
<script>
import BoardContents from '@/dashboard/BoardContents';
import AppHeader from '@/common/AppHeader';
import ContestApi from '../api/contest.api';
import NoData from '@/dashboard/NoData';

export default {
  name: 'DashBoard',
  data: function() {
    return {
      showLoading: true,
      contests: [
        {
          id: 1,
          name: '2021 리그전',
          description: '2021.01.01~',
          start: {
            startDate: '2021-01-01',
            startTime: '00:00:00',
          },
          end: {
            endDate: '2021-12-30',
            endTime: '23:59:59',
          },
          state: {
            code: '0',
            name: 'PROCEEDING',
          },
          maximumParticipants: 32,
          participation: false,
        },
      ],
    };
  },
  components: { BoardContents, AppHeader, NoData },
  methods: {
    getDetail(id) {
      this.$store.commit('SAVE_MATCH_ID', id);
      this.$router.push({ path: '/match/:id', query: { id } });
    },
  },
  async created() {
    this.contests = await ContestApi.inquire_contests();
    this.showLoading = false;
  },
};
</script>

<style scoped>
.board {
  margin-top: 10px;
}
</style>
