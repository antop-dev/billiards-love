<template>
  <div>
    <div class="md-layout md-gutter">
      <div class="md-layout-item">
        <md-field>
          <label>별명</label>
          <md-input v-model="nickname"></md-input>
        </md-field>
        <md-field>
          <label>대대 핸디</label>
          <md-input v-model="handy"></md-input>
        </md-field>
        <div class="md-btn">
          <md-button @click="confirm" class="md-raised">확인</md-button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'SignUp',
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

<style scoped>
.d-toolbar-row {
  display: inline-grid;
}
.md-layout {
  padding: 10px;
}
.md-btn {
  text-align: center;
}
</style>
