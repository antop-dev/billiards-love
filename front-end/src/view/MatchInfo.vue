<template>
  <div>
    <v-row class="pa-4" align="center" justify="center">
      <v-col class="text-center">
        <h3 class="text-h5">
          상태
        </h3>
      </v-col>
    </v-row>
    <v-form>
      <v-container>
        <v-text-field
          v-model="date"
          filled
          color="blue-grey lighten-2"
          label="날짜"
        ></v-text-field>
        <v-text-field
          v-model="rate"
          filled
          color="blue-grey lighten-2"
          label="진행률"
        ></v-text-field>
        <v-text-field
          v-model="join"
          filled
          color="blue-grey lighten-2"
          label="참가자"
        ></v-text-field>
      </v-container>
    </v-form>
  </div>
</template>

<script>
export default {
  name: 'GameInfo',
  data() {
    return {
      date: '2020-10-01 ~ 2021-10-02',
      rate: '20% (228/1,140)',
      join: '20',
      showDialog: false,
    };
  },
};
</script>

<style scoped>
.md-card {
  margin: 4px;
  display: inline-block;
  vertical-align: top;
}
</style>
