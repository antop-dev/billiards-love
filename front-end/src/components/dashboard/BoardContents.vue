<template>
  <v-card color="#385F73" dark class="contents">
    <ContentsHeader
      :title="content.title"
      :state-name="content.stateName"
      :state-code="content.stateCode"
    ></ContentsHeader>
    <div v-if="content.player">
      <v-col cols="12">
        <v-card-title class="text-h4">
          {{ content.player.rank }}위
          <v-icon large color="orange darken-2">mdi-arrow-up-bold</v-icon>
        </v-card-title>
      </v-col>
      <v-col cols="12">
        <v-row justify="end">
          <span v-if="content.player.score"
            ><h3>{{ content.player.score }} 점</h3></span
          >
        </v-row>
        <v-row justify="end">
          <span v-if="content.progress"
            ><h3>
              {{ content.progress }}% ({{ 0 }} / {{ content.maxJoiner }})
            </h3></span
          >
        </v-row>
      </v-col>
    </div>
  </v-card>
</template>
<script>
import ContentsHeader from './ContentsHeader';
export default {
  name: 'BoardContents',
  components: { ContentsHeader },
  props: {
    content: Object,
  },
  data() {
    return {
      showLoading: true,
      rank: 1,
      score: '',
    };
  },
  methods: {},
  created() {},
};
</script>

<style scoped>
.contents {
  padding: 6px;
  cursor: pointer;
}
</style>
