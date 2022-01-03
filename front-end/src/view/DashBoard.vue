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
              :title="content.title"
            >
              <board-contents
                :content="content"
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
      contests: [],
    };
  },
  components: { BoardContents, AppHeader, NoData },
  methods: {
    getDetail(id) {
      this.$router.push(`/contest/${id}`);
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
