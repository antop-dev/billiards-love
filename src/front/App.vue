<template>
  <div>
    <app-header></app-header>
    <div>
      <router-view></router-view>
    </div>
    <md-button v-on:click="moveHello">헬로</md-button>
    <md-button v-on:click="moveWorld">월드</md-button>
  </div>
</template>

<script>
import AppHeader from './components/AppHeader';

export default {
  components: { AppHeader },
  data() {
    return {};
  },
  methods: {
    moveHello: function () {
      this.$router.push('/hello');
    },
    moveWorld: function () {
      this.$router.push('/world');
    },
  },
};
</script>

<style>
#app {
  font-size: 18px;
  font-family: 'Roboto', sans-serif;
  color: blue;
}
</style>
