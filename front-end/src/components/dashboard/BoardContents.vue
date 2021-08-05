<template>
  <div class="contents">
    <md-content class="md-elevation-5">
      <ContentsHeader :title="title" :state="state"></ContentsHeader>
      <div>
        <div class="md-layout md-gutter">
          <div
            class="md-layout-item md-layout md-gutter md-alignment-center-center md-size-30"
          >
            <div class="md-layout-item">
              <h1>{{ rank }}위</h1>
            </div>
            <div class="md-layout-item">
              <md-icon class="md-size-2x">arrow_upward</md-icon>
            </div>
          </div>
          <div
            class="md-layout-item md-layout md-gutter
            md-alignment-center-right"
          >
            <md-content>
              <span>210 점</span>
              <br />
              <span>{{ progress }}%</span>
              <br />
              <span>(110 / 220)</span>
            </md-content>
          </div>
        </div>
      </div>
    </md-content>
  </div>
</template>
<script>
import ContestApi from '../../api/contest.api';
import ContentsHeader from './ContentsHeader';
export default {
  name: 'BoardContents',
  components: { ContentsHeader },
  props: {
    id: Number,
    title: String,
    state: String,
  },
  data() {
    return {
      rank: 1,
      participant: {
        id: '',
        name: '',
        handicap: '',
      },
      progress: '',
      score: '',
    };
  },
  methods: {
    setRank(rankInfo) {
      this.rank = rankInfo.rank;
      this.participant = rankInfo.participant;
      this.progress = rankInfo.progress;
      this.score = rankInfo.score;
    },
  },
  created() {
    try {
      const inquireRank = ContestApi.inquire_rank(this.id);
      this.setRank(inquireRank);
    } catch (e) {
      console.log(e);
    }
  },
};
</script>

<style scoped>
.contents {
  padding: 6px;
  cursor: pointer;
}
.md-layout {
  padding: 10px;
}
</style>
