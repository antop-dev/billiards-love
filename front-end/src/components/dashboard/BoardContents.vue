<template>
  <v-card class="mx-auto" outlined>
    <v-row>
      <v-col cols="2" class="d-flex align-content-center flex-wrap">
        <v-btn text align="center">
          <span
            >{{ content.player != null ? content.player.rank : '-' }} 위</span
          >
        </v-btn>
      </v-col>
      <v-col cols="10">
        <v-card-title class="subtitle-1">
          <span>{{ content.title }}</span>
          <v-spacer></v-spacer>
          <span>
            {{ content.stateName }}
          </span>
        </v-card-title>
        <v-card-subtitle v-text="content.description"></v-card-subtitle>
        <v-card-actions class="caption">
          <v-spacer></v-spacer>
          {{ content.progress }}% ({{ content.maxJoiner || 0 }} /
          {{ content.currentJoiner || 0 }})
        </v-card-actions>
      </v-col>
    </v-row>

    <!--    <div v-if="content.player">
      <v-col cols="12">
        
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
    </div>-->
  </v-card>
</template>
<script>
export default {
  name: 'BoardContents',
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

<style scoped></style>
