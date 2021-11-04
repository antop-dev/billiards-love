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
            VS {{ match.opponent.nickname }}
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
                  v-model="results[i]"
                  :items="['WIN', 'LOSE', 'ABSTENTION', 'HOLD']"
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
    opponentId: Number,
    value: String,
    toggleWindow: Function,
  },
  data() {
    return {
      selected: '',
      match: {
        opponent: {},
      },
      results: [],
      dialog: false,
    };
  },
  methods: {
    closeWindow() {
      this.$emit('toggleWindow', false);
    },
    updateResult(v, i) {
      this.$set(this.results, v, i);
    },
    confirm() {
      MatchApi.update(this.id, this.results);
      this.dialog = false;
    },
  },
  async created() {
    try {
      this.match = await MatchApi.inquire(this.opponentId);
    } catch {
      console.error('error');
    }
    this.results = this.match.result;
  },
};
</script>

<style scoped></style>
