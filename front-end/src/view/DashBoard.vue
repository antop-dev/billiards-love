<template>
  <div>
    <app-header title="대시보드" menu-btn="true"></app-header>
    <v-container>
      <div v-if="showLoading" class="text-center">
        <v-progress-circular
          :size="70"
          :width="7"
          color="purple"
          indeterminate
        ></v-progress-circular>
      </div>
      <div v-else-if="contests.length > 0" outlined>
        <div
          class="board"
          v-for="content in contests"
          v-bind:key="content.id"
          :title="content.title"
        >
          <board-contents
            :content="content"
            @click.native="getDetail(content.id)"
          ></board-contents>
        </div>
      </div>
      <v-card v-else>
        <v-content>
          <no-data></no-data>
        </v-content>
      </v-card>
    </v-container>
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
      contests: [],
    };
  },
  components: { BoardContents, AppHeader, NoData },
  methods: {
    getDetail(id) {
      this.$router.push(`/contest/${id}`);
    },
    async getContests() {
      const contests = await ContestApi.inquire_contests();
      for (var i = 0; i < contests.length; i++) {
        const myContest = {};
        const contest = contests[i];
        myContest['id'] = contest.id;
        myContest['title'] = contest.title;
        myContest['description'] = contest.description;
        myContest['startDate'] = contest.startDate;
        myContest['startTime'] = contest.startTime;
        myContest['endDate'] = contest.endDate;
        myContest['endTime'] = contest.endTime;
        myContest['maxJoiner'] = contest.maxJoiner;
        myContest['currentJoiner'] = contest.currentJoiner;
        myContest['stateCode'] = contest.stateCode;
        myContest['stateName'] = contest.stateName;
        myContest['progress'] = contest.progress;
        myContest['player'] = contest.player;
        this.contests.push(myContest);
      }
      this.showLoading = false;
    },
  },
  created() {
    this.getContests();
  },
};
</script>

<style scoped>
.board {
  margin-top: 10px;
}
</style>
