<template>
  <div class="page-container md-layout-column">
    <md-toolbar class="md-primary">
      <md-button class="md-icon-button" @click="showNavigation = true">
        <md-icon>menu</md-icon>
      </md-button>
      <span class="md-title">당구사랑</span>
    </md-toolbar>

    <md-drawer :md-active.sync="showNavigation" md-swipeable>
      <md-toolbar class="md-transparent" md-elevation="0">
        <profile></profile>
      </md-toolbar>
      <md-list>
        <md-list-item>
          <md-button class="md-list-item-text" v-on:click="goHello">
            <span>대시보드</span>
          </md-button>
        </md-list-item>

        <md-list-item>
          <md-button class="md-list-item-text" v-on:click="goWorld">
            <span>고월드</span>
          </md-button>
        </md-list-item>
      </md-list>
    </md-drawer>
    <md-content>helloWorld</md-content>
  </div>
</template>
<script>
import Profile from './Profile';
export default {
  name: 'AppHeader',
  components: { Profile },
  data() {
    return {
      showNavigation: false,
    };
  },
  methods: {
    goHello: function () {
      this.$router.push('/hello');
    },
    goWorld: function () {
      this.$router.push('/world');
    },
  },
};
</script>

<style scoped>
.md-toolbar {
  padding: 0;
}
.md-list-item .md-button {
  text-align: left;
}
</style>
