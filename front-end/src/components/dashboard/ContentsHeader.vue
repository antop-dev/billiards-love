<template>
  <div>
    <md-toolbar :class="gameStatus()" :md-elevation="3">
      <div v-if="isNoData">
        <span class="md-subheading">{{ title }}</span>
      </div>
      <div v-else class="md-toolbar-row">
        <div class="md-toolbar-section-start">
          <span class="material-icons">polymer</span>
        </div>
        <span class="md-subheading">{{ title }}</span>
        <div class="md-toolbar-section-end">
          <md-button :class="gameStatus()">{{ stateMsg }}</md-button>
        </div>
      </div>
    </md-toolbar>
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
        return 'md-primary';
      } else if (this.state === '3' || this.state === '4') {
        this.stateMsg = '종료';
        return 'md-accent';
      } else {
        this.stateMsg = '접수중';
        return 'md-transparent';
      }
    },
  },
};
</script>

<style scoped></style>
