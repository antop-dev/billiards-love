<template>
  <div>
    안녕하세요
  </div>
</template>
<script>
export default {
  name: 'Main',
  created() {
    console.log('welcome DashBaord');
    console.log(this.$store.getters.getUserInfo);
  },
};
</script>

<style scoped></style>
