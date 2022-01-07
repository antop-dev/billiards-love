<template>
  <div>
    <v-row class="pa-4" align="center" justify="center">
      <v-col class="text-center">
        <h3 class="text-h5">
          상태
        </h3>
      </v-col>
    </v-row>
    <v-form>
      <v-container>
        <v-text-field
          v-model="date"
          filled
          color="blue-grey lighten-2"
          label="날짜"
        ></v-text-field>
        <v-text-field
          v-model="currentJoiner"
          filled
          color="blue-grey lighten-2"
          label="참가자"
        ></v-text-field>
        <v-text-field
          v-model="progress"
          filled
          color="blue-grey lighten-2"
          label="진행률"
        ></v-text-field>
        <div class="text-right">
          <div v-if="$store.state.login_info.manager">
            <v-btn
              class="ma-2"
              v-if="stateCode === 'PREPARING'"
              @click="openContest"
              >오픈</v-btn
            >
            <v-btn
              class="ma-2"
              v-if="stateCode === 'ACCEPTING' || stateCode === 'STOPPED'"
              @click="startContest"
              >시작</v-btn
            >
            <v-btn
              class="ma-2"
              v-if="stateCode === 'PROCEEDING' || stateCode === 'ACCEPTING'"
              @click="stopContest"
              >중지</v-btn
            >
            <v-btn
              class="ma-2"
              v-if="stateCode === 'STOPPED'"
              @click="endContest"
              >종료</v-btn
            >
          </div>
          <div>
            <v-btn class="ma-2" color="secondary" v-if="checkJoin" @click="join"
              >참가</v-btn
            >
            <v-btn class="ma-2" color="error" v-else @click="cancelJoin"
              >참가취소</v-btn
            >
          </div>
        </div>
      </v-container>
    </v-form>
  </div>
</template>

<script>
import ContestApi from '../api/contest.api';
export default {
  name: 'GameInfo',
  data() {
    return {
      id: '',
      date: '',
      stateCode: '',
      progress: '',
      maxJoiner: 0,
      player: {},
      currentJoiner: 0,
      showDialog: false,
      isJoin: false,
    };
  },
  methods: {
    async inquireContest() {
      try {
        const contest = await ContestApi.inquire_contest(this.id);
        this.setDetail(contest);
      } catch (e) {
        alert(e);
      }
    },
    setDetail(detail) {
      this.player = detail.player || {};
      this.stateCode = detail.stateCode;
      this.date =
        detail.startDate + (detail.endDate ? ' - ' + detail.endDate : '');
      this.progress =
        (detail.progress || 0) +
        '% (' +
        (detail.currentJoiner || 0) +
        '/' +
        detail.maxJoiner +
        ')';
      this.currentJoiner = (detail.currentJoiner || 0) + ' 명';
    },
    join() {
      this.$router.push(`/contest/${this.id}/join`);
    },
    toggleJoin() {
      this.isJoin = !this.isJoin;
    },
    async cancelJoin() {
      try {
        let contest = await ContestApi.cancel_join(this.id);
        this.setDetail(contest);
        this.$toast.error('취소되었습니다.');
      } catch (e) {
        alert(e);
      }
    },
    async openContest() {
      try {
        await ContestApi.open_contest(this.id);
        this.$toast.success('대회가 열렸습니다.');
      } catch (e) {
        alert(e);
      }
    },
    async stopContest() {
      try {
        await ContestApi.stop_contest(this.id);
        this.$toast.info('대회가 닫혔습니다.');
      } catch (e) {
        alert(e);
      }
    },
    async endContest() {
      try {
        await ContestApi.end_contest(this.id);
        this.$toast.info('대회가 열렸습니다.');
      } catch (e) {
        alert(e);
      }
    },
    async startContest() {
      try {
        await ContestApi.start_contest(this.id);
        this.$toast.info('대회가 열렸습니다.');
      } catch (e) {
        alert(e);
      }
    },
  },
  computed: {
    checkJoin() {
      return (
        Object.keys(this.player).length === 0 &&
        (this.stateCode === '2' || this.stateCode === '1')
      ); // 준비중 접수중일 때 표시
    },
  },
  created() {
    let params = this.$route.params;
    this.id = params.id;
    this.inquireContest();
  },
};
</script>

<style scoped>
.md-card {
  margin: 4px;
  display: inline-block;
  vertical-align: top;
}
</style>
