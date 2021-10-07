<template>
  <div>
    <v-dialog v-model="dialog" width="500">
      <template v-slot:activator="{ on, attrs }">
        <a v-bind="attrs" v-on="on">{{ renderResult() }}</a>
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
  data() {
    return {
      dialog: false,
    };
  },
  props: {
    resultList: Array,
    toggleWindow: Function,
  },
  methods: {
    closeWindow() {
      this.$emit('toggleWindow', false);
    },
    renderResult() {
      let win = 0;
      let lost = 0;
      let typeNone = true;
      this.resultList.forEach(value => {
        if (value === 'WIN') {
          typeNone = false;
          win++;
        } else if (value === 'LOSE') {
          typeNone = false;
          lost++;
        }
      });
      return typeNone ? '선택' : (win + '승' || '') + (lost + '패' || '');
    },
  },
  async created() {
    this.renderResult();
    // const id = this.$store.state.match_detail.id;
    // const matchResult = await MatchApi.inquire(id);
    // console.log(matchResult);
  },
};
</script>

<style scoped></style>
