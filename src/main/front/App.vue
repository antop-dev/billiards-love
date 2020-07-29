<template>
  <div>
    <app-header></app-header>
    <div>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import AppHeader from './components/AppHeader';

export default {
  components: { AppHeader },
  data() {
    return {};
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
