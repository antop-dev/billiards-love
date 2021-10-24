<template>
  <div>
    <v-dialog v-model="dialog" width="500">
      <template v-slot:activator="{ on, attrs }">
        <a v-bind="attrs" v-on="on">{{ renderButton() }}</a>
      </template>

      <v-card>
        <v-card-title class="text-h5 blue lighten-2">
          결과 입력
        </v-card-title>
        <v-card-title class="text-center justify-center py-6">
          <h1 class="font-weight-bold text-h3 basil--text">
            VS {{ opponent.opponent.nickname }}
          </h1>
        </v-card-title>
        <v-divider></v-divider>

        <v-list subheader three-line>
          <v-divider></v-divider>
          <v-list-item :key="i" v-for="(result, i) in results">
            <v-list-item-content>
              <h2>{{ i + 1 }}경기</h2>
            </v-list-item-content>
            <v-list-item-content>
              <h2>
                <v-select
                  :disabled="result === 'NONE'"
                  v-model="results[i]"
                  :items="['WIN', 'LOSE']"
                  @change="updateResult(result, i)"
                  menu-props="auto"
                  hide-details
                ></v-select>
              </h2>
            </v-list-item-content>
          </v-list-item>
        </v-list>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="confirm">
            확인
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import MatchApi from '../api/match.api';
export default {
  name: 'GameResult',
  props: {
    id: String,
    opponent: Object,
    toggleWindow: Function,
  },
  data() {
    return {
      value: 'test',
      selected: '',
      results: [],
      dialog: false,
    };
  },
  methods: {
    closeWindow() {
      this.$emit('toggleWindow', false);
    },
    renderResult(result) {
      if (result === 'WIN') {
        return '승';
      } else if (result === 'LOSE') {
        return '패';
      } else {
        return '선택';
      }
    },
    updateResult(v, i) {
      this.$set(this.results, v, i);
      this.renderButton();
    },
    renderButton() {
      let win = 0;
      let lose = 0;
      let none = 0;
      for (let i = 0; i < this.results.length; i++) {
        const result = this.results[i];
        if (result === 'WIN') {
          win++;
        } else if (result === 'LOSE') {
          lose++;
        } else {
          none++;
          if (none === result.length) {
            return '선택';
          }
        }
      }
      return (win > 0 ? win + '승' : '') + (lose > 0 ? lose + '패' : '');
    },
    confirm() {
      MatchApi.update(this.id, this.results);
      this.dialog = false;
    },
  },
  async created() {
    this.results = this.opponent.result;
  },
};
</script>

<style scoped></style>
