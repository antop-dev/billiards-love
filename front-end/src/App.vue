<template>
  <div>
    <!-- 초기화가 되어있나 안되어있나-->
    <div v-if="!isInit">
      <!-- TODO 로그인이 되어있나 안되어 있나에 따라 나뉨 -->
      <div v-if="isLogin">
        <app-header></app-header>
        <router-view></router-view>
      </div>
      <div v-else>
        <span>카카오 버튼이 생길겁니다..</span>
      </div>
    </div>
    <md-progress-spinner v-else md-mode="indeterminate"> </md-progress-spinner>
  </div>
</template>

<script>
import AppHeader from '@/common/AppHeader';
import FingerPrinter from './util/fingerPrint';
import LoginApi from './api/login.api';
export default {
  components: { AppHeader },
  data() {
    return {
      isInit: true,
      isLogin: true,
    };
  },
  methods: {
    /**
     * 카카오 초기화 정보를 요청합니다
     * @returns {Promise<*>}
     */
    async kakaoInit() {
      // 핑거프린터 Key
      const deviceID = await FingerPrinter();
      // 리퀘스트 아이디 요청
      const requestId = '';
      // 카카오 키 요청
      return await LoginApi.requestInitKey(deviceID, requestId);
    },
  },
  created() {
    const kakaoInfo = this.kakaoInit();
    // 초기화 정보 저장
    this.$store.dispatch('saveInitRequestInfo', kakaoInfo);
  },
};
</script>

<style></style>
