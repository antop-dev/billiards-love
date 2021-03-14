<template>
  <div>
    <app-header title="추가정보 입력"></app-header>
    <div>
      <md-field>
        <label>별명</label>
        <md-input v-model="nickname"></md-input>
      </md-field>
      <md-field>
        <label>대대 핸디</label>
        <md-input v-model="handicap"></md-input>
      </md-field>
      <div class="text-align-center">
        <md-button @click="confirm" class="md-raised">확인</md-button>
      </div>
    </div>
  </div>
</template>
<script>
import AppHeader from '@/common/AppHeader';
import loginApi from '../api/login.api';
import aes256 from '../util/aes256';

export default {
  name: 'SignUp',
  components: { AppHeader },
  data() {
    return {
      nickname: '',
      handicap: '',
    };
  },
  methods: {
    async confirm() {
      try {
        await loginApi.registerMember({
          nickname: aes256.encrypt(this.nickname, this.$store.state.secret_key),
          handicap: this.handicap,
        });
        this.$router.push('/dashboard');
      } catch (e) {
        console.error('error::', e);
      }
    },
  },
  created() {
    if (this.$store.state.login_info.registered) {
      this.$router.push('/dashboard');
    }
  },
};
</script>

<style scoped></style>
