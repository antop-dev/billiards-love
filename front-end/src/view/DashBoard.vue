<template>
  <div>
    <app-header title="대시보드" menu-btn="true"></app-header>
    <div v-if="showLoading">
      <md-progress-spinner md-mode="indeterminate"> </md-progress-spinner>
    </div>
    <div v-else>
      <div v-if="contentsList.length > 0">
        <div
          class="board"
          v-for="content in contentsList"
          v-bind:key="content.id"
        >
          <board-contents
            :title="content.name"
            :state="content.state.code"
            @click.native="getDetail(content.id)"
          ></board-contents>
        </div>
      </div>
      <div v-else>
        <no-data></no-data>
      </div>
    </div>
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
      contentsList: [
        /*{
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
        },*/
      ],
    };
  },
  components: { BoardContents, AppHeader, NoData },
  methods: {
    getDetail(id) {
      this.$router.push({ path: '/match/:id', query: { id } });
    },
  },
  async created() {
    this.contentsList = await ContestApi.inquire();
    this.showLoading = false;
  },
};
</script>

<style scoped>
.board {
  margin-top: 20px;
}
</style>
