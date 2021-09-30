<template>
  <div>
    <v-dialog v-model="dialog" width="500">
      <template v-slot:activator="{ on, attrs }">
        <a v-bind="attrs" v-on="on">{{ value }}</a>
      </template>

      <v-card>
        <v-card-title class="text-h5 blue lighten-2">
          결과 입력
        </v-card-title>
        <v-card-title class="text-center justify-center py-6">
          <h1 class="font-weight-bold text-h3 basil--text">
            VS 상대
          </h1>
        </v-card-title>
        <v-divider></v-divider>

        <v-list subheader three-line>
          <v-divider></v-divider>
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title><h2>1경기</h2></v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item>
            <v-list-item-content>
              <v-list-item-title><h2>2경기</h2></v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="dialog = false">
            확인
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
// import MatchApi from '../api/match.api';
export default {
  name: 'GameResult',
  props: {
    value: String,
    showDialog: Boolean,
    toggleWindow: Function,
  },
  async created() {
    // const id = this.$store.state.match_detail.id;
    // const matchResult = await MatchApi.inquire(id);
    // console.log(matchResult);
  },
  methods: {
    closeWindow() {
      this.$emit('toggleWindow', false);
    },
  },
};
</script>

<style scoped></style>
