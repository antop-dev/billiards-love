<template>
  <router-view></router-view>
</template>

<script>
import { SET_USER_INFO } from './store/mutation-types';

export default {
  data() {
    return {
      loading: true,
    };
  },
  methods: {
    init: function () {
      Kakao.init('60b53819660d5a05c66c3d1c5d4a4503');
    },
    getInfo: function (data) {
      Kakao.API.request({
        url: '/v2/user/me',
        success: (res) => {
          this.$store.commit(SET_USER_INFO, res.kakao_account.profile);
        },
        fail: (message) => {
          console.log(message);
        },
      });
    },
  },
  mounted() {
    this.init();
    Kakao.Auth.login({
      success: (response) => {
        this.getInfo(response);
        this.$router.push('/dashboard').catch(() => {
          /**Do Noting**/
        });
      },
      fail: (error) => {
        console.log(error);
      },
    });
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
