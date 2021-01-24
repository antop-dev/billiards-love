<template>
  <div class="page-container">
    <div class="md-alignment-center-center">
      <!-- 초기화가 되어있나 안되어있나-->
      <div v-if="isInit">
        <div v-if="!isLogin">
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
      <div v-else>
        <div>
          <md-progress-spinner md-mode="indeterminate"> </md-progress-spinner>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isInit: false,
      isLogin: false,
    };
  },
  methods: {
    /**
     * 카카오 초기화 정보를 요청합니다
     * @returns data = { appId:'', encodedId: '', kakaoKey: ''}
     */
    kakaoInit() {
      // 리퀘스트 아이디 요청
      if (!window.Kakao.isInitialized()) {
        // 서버로부터 초기화 키를 요청합니다
        try {
          // "appId" : "","encodedId" :"", "kakaoKey": ""
          // const initKey = await LoginApi.requestInitKey(deviceId, requestId);
          // this.$store.dispatch('saveLoginRequestInfo', initKey);

          // testKEy 60b53819660d5a05c66c3d1c5d4a4503
          console.log('kakao is not initialized');
          window.Kakao.init('60b53819660d5a05c66c3d1c5d4a4503');
          this.isInit = true;
        } catch (e) {
          console.error('error :: ', e);
        }
      }
      // console.log(initKey);
    },
    async kakaoLogin() {
      const statusInfo = await new Promise(resolve => {
        window.Kakao.Auth.getStatusInfo(statusObj => {
          resolve(statusObj);
        });
      });
      if (statusInfo.status === 'not_connected') {
        window.Kakao.Auth.login({
          success: dat => {
            // 토큰 호출
            // const token = await LoginApi.loginExecute(dat);
            // 토큰 저장
            // this.$store.state.token = token;
            // 라우팅
            this.$store.state.accessToken = dat.access_token;
            this.isLogin = true;
          },
          fail: e => {
            console.error(e);
          },
        });
      } else {
        this.$store.state.accessToken = statusInfo.user.access_token;
        this.isLogin = true;
      }
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
