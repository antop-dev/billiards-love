<template>
  <div class="md-layout md-gutter md-alignment-center-center">
    <!-- 초기화가 되어있나 안되어있나-->
    <div v-if="isInit">
      <!-- TODO 로그인이 되어있나 안되어 있나에 따라 나뉨 -->
      <div v-if="!isLogin">
        <app-header></app-header>
        <router-view></router-view>
      </div>
      <div v-else>
        <a @click="kakaoLogin" style="cursor: pointer;">
          <img
            src="//k.kakaocdn.net/14/dn/btqCn0WEmI3/nijroPfbpCa4at5EIsjyf0/o.jpg"
            width="222"
          />
        </a>
      </div>
    </div>
    <div v-else>
      <div>
        <md-progress-spinner md-mode="indeterminate"> </md-progress-spinner>
      </div>
    </div>
  </div>
</template>

<script>
import AppHeader from '@/common/AppHeader';
import LoginApi from './api/login.api';
export default {
  components: { AppHeader },
  data() {
    return {
      isInit: false,
      isLogin: true,
    };
  },
  methods: {
    /**
     * 카카오 초기화 정보를 요청합니다
     * @returns data = { appId:'', encodedId: '', kakaoKey: ''}
     */
    async kakaoInit() {
      // 리퀘스트 아이디 요청
      const requestId = this.$store.state.requestId;
      const deviceId = this.$store.state.deviceId;

      console.log(requestId);
      console.log(deviceId);

      if (!window.Kakao.isInitialized()) {
        // 서버로부터 초기화 키를 요청합니다
        try {
          // "appId" : "","encodedId" :"", "kakaoKey": ""
          // const initKey = await LoginApi.requestInitKey(deviceId, requestId);
          // this.$store.dispatch('saveLoginRequestInfo', initKey);

          // testKEy 60b53819660d5a05c66c3d1c5d4a4503
          console.log('kakao is not initialized');
          window.Kakao.init('60b53819660d5a05c66c3d1c5d4a4503');
        } catch (e) {
          console.error('error :: ', e);
        } finally {
          this.isInit = true;
        }
      }
      // console.log(initKey);
    },
    kakaoLogin() {
      return window.Kakao.Auth.login({
        success: async dat => {
          // 토큰 호출
          const token = await LoginApi.loginExecute(dat);
          // 토큰 저장
          this.$store.state.token = token;
          // 라우팅
        },
        fail: e => {
          console.error(e);
        },
      });
    },
  },
  created() {
    // 최초로 카카오 초기화 합니다.
    this.kakaoInit();
    // 초기화 정보 저장
    this.kakaoLogin();
  },
};
</script>

<style></style>
