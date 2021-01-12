<template>
  <div>
    <a @click="kakaoLogin" href="#">
      <img
        src="//k.kakaocdn.net/14/dn/btqCn0WEmI3/nijroPfbpCa4at5EIsjyf0/o.jpg"
        width="222"
      />
    </a>
  </div>
</template>

<script>
export default {
  name: 'Login',
  methods: {
    kakaoLogin() {
      window.Kakao.Auth.login({
        success: dat => {
          localStorage.setItem('access_token', dat.access_token);
          this.$store.state.accessToken = dat.access_token;
          // 토큰 호출
          // const token = await LoginApi.loginExecute(dat);
          // 토큰 저장
          // this.$store.state.token = token;
          // 라우팅
          if (this.$route.query.redirect) {
            this.$router.replace(this.$route.query.redirect);
          } else {
            this.$router.push('/register');
          }
        },
        fail: e => {
          console.error(e);
        },
      });
    },
  },
  created() {
    // 최초로 카카오 초기화 합니다.
    this.kakaoLogin();
    // 초기화 정보 저장
  },
};
</script>

<style scoped></style>
