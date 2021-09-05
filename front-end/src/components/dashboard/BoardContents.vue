<template>
  <v-card style="display: block" class="contents">
    <ContentsHeader :title="title" :state="state"></ContentsHeader>
    <div class="md-layout md-gutter" v-if="!showLoading">
      <v-col cols="12">
        <v-card color="#385F73" dark>
          <v-row>
            <v-card-title class="text-h5">
              {{ rank }}위
              <v-icon large color="orange darken-2"
                >mdi-arrow-up-bold-box-outline</v-icon
              >
            </v-card-title>
          </v-row>
          <v-row justify="end">
            <span> 점</span>
            <br />
            <span>{{ progress }}%</span>
            <br />
            <span></span>
          </v-row>
        </v-card>
      </v-col>
      <!--<div
        class="md-layout-item md-layout md-gutter md-alignment-center-center md-size-30"
      >
        <div class="md-layout-item">

        </div>
        <div class="md-layout-item">

        </div>
      </div>
      <div
        class="md-layout-item md-layout md-gutter
            md-alignment-center-right"
      >
        <md-content>

        </md-content>
      </div>-->
    </div>
    <div class="text-center" v-else>
      <v-progress-circular
        :size="70"
        :width="7"
        color="purple"
        indeterminate
      ></v-progress-circular>
    </div>
  </v-card>
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
      showLoading: true,
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
      this.showLoading = false;
    },
  },
  async created() {
    try {
      const inquireRank = await ContestApi.inquire_rank(this.id);
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
