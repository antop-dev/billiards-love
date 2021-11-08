<template>
  <div>
    <v-app>
      <div v-if="showLoading" class="text-center">
        <v-progress-circular
          :size="70"
          :width="7"
          color="purple"
          indeterminate
        ></v-progress-circular>
      </div>
      <div v-else>
        <div v-if="showLoginButton">
          <a @click="kakaoLogin" href="#">
            <img
              src="//k.kakaocdn.net/14/dn/btqCn0WEmI3/nijroPfbpCa4at5EIsjyf0/o.jpg"
              width="222"
            />
          </a>
        </div>
        <v-main v-else>
          <router-view></router-view>
        </v-main>
      </div>
      <v-snackbar v-model="snackbar" top>
        {{ text }}

        <template v-slot:action="{ attrs }">
          <v-btn color="pink" text v-bind="attrs" @click="snackbar = false">
            Close
          </v-btn>
        </template>
      </v-snackbar>
      <v-btn @click="snackbar = true">테스트</v-btn>
    </v-app>
    <!-- 초기화가 되어있나 안되어있나-->
  </div>
</template>

<script>
import LoginApi from './api/login.api';
import aes256 from './util/aes256';

export default {
  data() {
    return {
      text: 'TEST!!',
      snackbar: false,
      showLoading: true,
      showLoginButton: false,
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
        this.$store.state.secret_key = initKey.secretKey;
        const kakaoInitKey = aes256.decrypt(
          initKey.kakaoKey,
          this.$store.state.secret_key,
        );
        window.Kakao.init(kakaoInitKey);
      } catch (e) {
        console.error('error :: ', e);
      }
    },
    async kakaoLogin() {
      const statusInfo = await this.getUserInfo();
      if (statusInfo.status === 'not_connected') {
        this.showLoginButton = true;
        window.Kakao.Auth.login({
          success: async () => {
            // 사용자 정보 입력
            await this.executeLogin(statusInfo);
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
      if (this.$store.state.login_info.token === '') {
        const loginInfo = await LoginApi.executeLogin(dat.user);
        this.$store.commit('SAVE_LOGIN_REQUEST_INFO', loginInfo);
      }

      this.showLoginButton = false;
      this.showLoading = false;
      if (!this.$store.state.login_info.registered) {
        await this.$router.push('/register');
      } else if (this.$route.path === '/') {
        await this.$router.push('/dashboard');
      }
    },
    async getUserInfo() {
      return await new Promise(resolve => {
        window.Kakao.Auth.getStatusInfo(statusObj => {
          resolve(statusObj);
        });
      });
    },
  },
  async created() {
    // 최초로 카카오 초기화 합니다.
    await this.kakaoInit();
    await this.kakaoLogin();
  },
};
</script>

<style></style>
