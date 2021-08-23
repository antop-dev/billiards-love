<template>
  <div>
    <md-table
      hidden
      v-model="users"
      md-sort="rank"
      md-sort-order="asc"
      md-card
      md-fixed-header
    >
      <md-table-row slot="md-table-row" slot-scope="{ item }">
        <md-table-cell md-label="번호" md-sort-by="no" md-numeric>{{
          item.opponent.no
        }}</md-table-cell>
        <md-table-cell md-label="참가자명" md-sort-by="name">{{
          item.opponent.nickname
        }}</md-table-cell>
        <md-table-cell md-label="결과" md-sort-by="result">
          <a href="#" @click="showResult">{{ renderResult(item.result) }}</a>
        </md-table-cell>
        <md-table-cell md-label="확정" md-sort-by="confirm">{{
          item.closed
        }}</md-table-cell>
      </md-table-row>
    </md-table>
    <md-drawer
      class="md-bottom"
      md-permanent="full"
      :md-active.sync="showSidepanel"
    >
      <match-result match_id=""></match-result>
    </md-drawer>
    <md-button id="test"></md-button>
  </div>
</template>

<script>
import MatchApi from '../api/match.api';
import MatchResult from './MatchResult';
export default {
  name: 'GameRank',
  components: { MatchResult },
  data() {
    return {
      showSidepanel: false,
      users: [
        {
          id: 3823,
          opponent: {
            no: 2,
            id: 312,
            nickname: '홍길동',
          },
          result: ['WIN', 'LOSE', 'LOSE'],
          closed: true,
        },
        {
          id: 3824,
          opponent: {
            no: 3,
            id: 312,
            nickname: '배트맨',
          },
          result: ['NONE', 'NONE', 'NONE'],
          closed: false,
        },
      ],
    };
  },
  methods: {
    showResult() {
      if (this.showSidepanel) {
        this.showSidepanel = false;
      } else {
        this.showSidepanel = true;
      }
      //
    },
    renderResult(arr) {
      if (!Array.isArray(arr)) throw 'invalid Arr';
    },
    async created() {
      // RankApi.inquire();
      const id = this.$store.state.match_detail.id;
      const matches = await MatchApi.inquire_all(id);
      console.log(matches);
    },
  },
};
</script>

<style scoped></style>
