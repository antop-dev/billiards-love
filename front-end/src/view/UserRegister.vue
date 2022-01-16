<template>
  <div>
    <app-header title="추가정보 입력"></app-header>
    <v-form>
      <v-row>
        <v-col cols="12">
          <v-text-field
            v-model="nickname"
            counter="25"
            label="별명"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12">
          <v-text-field
            v-model="handicap"
            counter="25"
            label="핸디"
          ></v-text-field>
        </v-col>
      </v-row>
    </v-form>
    <div class="text-align-center">
      <v-btn @click="confirm" elevation="2">확인</v-btn>
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
