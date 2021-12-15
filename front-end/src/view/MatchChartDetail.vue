<template>
  <div>
    <v-card>
      <v-card-title class="text-center justify-center py-6">
        <h1 class="font-weight-bold text-h3 basil--text">
          VS {{ opponent.nickname }}
        </h1>
      </v-card-title>
      <v-divider></v-divider>

      <v-list subheader three-line>
        <v-divider></v-divider>
        <v-list-item :key="i" v-for="(result, i) in myResult">
          <v-list-item-content>
            <h2>{{ i + 1 }}경기</h2>
          </v-list-item-content>
          <v-list-item-content>
            <h2>
              <v-select
                :value="result"
                :items="['WIN', 'LOSE', 'ABSTENTION', 'HOLD']"
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
  </div>
</template>

<script>
import MatchApi from '../api/match.api';
export default {
  name: 'MatchChartDetail',
  data() {
    return {
      selected: '',
      match: {
        left: {}, // 내 정보
        right: {},
      },
      myself: {}, // 내 정보
      myResult: [],
      opponent: {},
      results: [],
      dialog: false,
    };
  },
  methods: {
    confirm() {
      MatchApi.update(this.myself.id, this.myResult);
      this.dialog = false;
    },
  },
  async created() {
    const matchId = this.$route.params.matchId;
    try {
      this.match = await MatchApi.inquire(matchId);
      this.myself = this.match.left;
      this.myResult = this.match.left.result;
      this.opponent = this.match.right;
    } catch {
      console.error('error');
    }
    this.results = this.match.result;
  },
};
</script>

<style scoped></style>
