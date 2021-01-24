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
        <md-input v-model="handy"></md-input>
      </md-field>
      <div class="text-align-center">
        <md-button @click="confirm" class="md-raised">확인</md-button>
      </div>
    </div>
  </div>
</template>
<script>
import AppHeader from '@/common/AppHeader';
export default {
  name: 'SignUp',
  components: { AppHeader },
  data() {
    return {
      nickname: '',
      handy: '',
    };
  },
  methods: {
    confirm() {
      this.$router.push('/dashboard');
    },
  },
};
</script>

<style scoped></style>
