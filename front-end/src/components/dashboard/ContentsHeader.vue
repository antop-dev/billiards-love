<template>
  <div>
    <v-toolbar :color="gameStatus()" dense flat>
      <v-toolbar-title>{{ title }}</v-toolbar-title>
      <v-spacer></v-spacer>
      <div class="md-toolbar-section-end">
        <v-btn elevation="2" :class="gameStatus()">{{ stateMsg }}</v-btn>
      </div>
    </v-toolbar>
  </div>
</template>

<script>
export default {
  name: 'ContentsHeader',
  props: {
    title: String,
    state: String,
    isNoData: String,
  },
  data() {
    return {
      stateMsg: null,
    };
  },
  methods: {
    gameStatus: function() {
      if (this.state === '0' || this.state === '1') {
        this.stateMsg = '참가중';
        return 'blue';
      } else if (this.state === '3' || this.state === '4') {
        this.stateMsg = '종료';
        return 'read';
      } else {
        this.stateMsg = '접수중';
        return 'orange';
      }
    },
  },
};
</script>

<style scoped></style>
