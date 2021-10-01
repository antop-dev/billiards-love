<template>
  <div>
    <v-toolbar :color="gameStatus()" dense flat>
      <v-toolbar-title>{{ title }}</v-toolbar-title>
      <v-spacer></v-spacer>
      <div>
        <v-btn elevation="2" :class="gameStatus()">{{ stateName }}</v-btn>
      </div>
    </v-toolbar>
  </div>
</template>

<script>
export default {
  name: 'ContentsHeader',
  props: {
    title: String,
    stateName: String,
    stateCode: String,
  },
  data() {
    return {
      stateMsg: null,
    };
  },
  methods: {
    gameStatus: function() {
      if (this.stateCode === '0' || this.stateCode === '1') {
        return 'blue';
      } else if (this.stateCode === '3' || this.stateCode === '4') {
        return 'read';
      } else {
        return 'orange';
      }
    },
  },
};
</script>

<style scoped></style>
