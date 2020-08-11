<template>
  <div>
    <app-header></app-header>
    <div>
      <div v-if="loading" class="md-layout md-gutter" :class="`md-alignment-center-center`">
        <md-progress-spinner md-mode="indeterminate"></md-progress-spinner>
      </div>
    </div>
  </div>
</template>

<script>
import AppHeader from './components/AppHeader';

export default {
  components: { AppHeader },
  data() {
    return {
      loading: true,
    };
  },
  mounted() {
    Kakao.init('0d394b0cf1d7956aba38ee4bceedbdb4');
    if (Kakao.isInitialized()) {
      this.loading = false;
      console.warn('Kakao API initialized');
      Kakao.Auth.authorize({
        redirectUri: 'http://localhost:9000',
      });
    }
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
