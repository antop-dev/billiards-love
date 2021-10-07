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
      </v-container>
    </v-form>
  </div>
</template>

<script>
export default {
  name: 'GameInfo',
  data() {
    return {
      date: '',
      progress: '',
      maxJoiner: 0,
      currentJoiner: 0,
      showDialog: false,
    };
  },
  methods: {
    render() {
      const matchDetail = this.$store.state.match_detail;
      this.setDetail(matchDetail);
    },
    setDetail(detail) {
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
  },
  created() {
    this.render();
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
