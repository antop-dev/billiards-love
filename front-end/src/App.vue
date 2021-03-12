<template>
  <div class="page-container">
    <div>
      <!-- 초기화가 되어있나 안되어있나-->
      <div v-if="showLoading">
        <md-progress-spinner md-mode="indeterminate"> </md-progress-spinner>
      </div>
      <div v-else>
        <div v-if="this.$store.state.isLogin">
          <md-content>
            <a @click="kakaoLogin" href="#">
              <img
                src="//k.kakaocdn.net/14/dn/btqCn0WEmI3/nijroPfbpCa4at5EIsjyf0/o.jpg"
                width="222"
              />
            </a>
          </md-content>
        </div>
        <div v-else>
          <router-view></router-view>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import LoginApi from './api/login.api';
import aes256 from './util/aes256';

export default {
  data() {
    return {
      showLoading: true,
    };
  },
  methods: {
    /**
     * 카카오 초기화 정보를 요청합니다
     * @returns data = { appId:'', encodedId: '', kakaoKey: ''}
     */
    async kakaoInit() {
      // 리퀘스트 아이디 요청
      try {
        // 초기화
        const initKey = await LoginApi.requestInitKey();
        const kakaoInitKey = aes256.decrypt(
          initKey.kakaoKey,
          initKey.secretKey,
        );
        window.Kakao.init(kakaoInitKey);
        this.kakaoLogin();
      } catch (e) {
        console.error('error :: ', e);
      } finally {
        this.showLoading = false;
      }
    },
    async kakaoLogin() {
      const statusInfo = await this.getUserInfo();
      if (statusInfo.status === 'not_connected') {
        this.$store.state.isLogin = true;
        window.Kakao.Auth.login({
          success: async () => {
            // 사용자 정보 입력
            await this.executeLogin(await this.getUserInfo());
          },
          fail: e => {
            console.error(e);
          },
        });
      } else {
        await this.executeLogin(statusInfo);
      }
    },
    async executeLogin(dat) {
      // 토큰이 없으면 로그인
      if (this.$store.state.login_info.token === null) {
        this.$store.state.login_info = await LoginApi.executeLogin(dat);
      }
      this.$store.state.isLogin = false;

      if (!this.$store.state.login_info.registered) {
        this.$router.push('/register');
      }
    },
    async getUserInfo() {
      return await new Promise(resolve => {
        window.Kakao.Auth.getStatusInfo(statusObj => {
          resolve(statusObj.user);
        });
      });
    },
  },
  created() {
    // 최초로 카카오 초기화 합니다.
    this.kakaoInit();
  },
};
</script>

<style>
.md-alignment-center-center {
}
</style>
