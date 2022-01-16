<template>
  <div>
    <v-simple-table>
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-center">
              번호
            </th>
            <th class="text-left">
              참가자명
            </th>
            <th class="text-left">
              결과
            </th>
            <th class="text-left">
              확정
            </th>
          </tr>
        </thead>
        <tbody v-if="matches.length > 0">
          <tr v-for="match in matches" :key="match.id">
            <td class="text-center">{{ match.right.number }}</td>
            <td>{{ match.right.nickname }}</td>
            <td>
              <a @click.prevent="pushMatchResult(match.id)">{{
                renderButton(match.left.result)
              }}</a>
            </td>
            <td>{{ match.closed }}</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
  </div>
</template>

<script>
import MatchApi from '../api/match.api';
export default {
  name: 'ContestMatchList',
  data() {
    return {
      id: '',
      matches: [],
      isPopupOpen: false,
      leftUser: {},
    };
  },
  methods: {
    renderButton(results) {
      let win = 0;
      let lose = 0;
      let none = 0;
      for (let i = 0; i < results.length; i++) {
        const result = results[i];
        if (result === 'WIN') {
          win++;
        } else if (result === 'LOSE') {
          lose++;
        } else {
          none++;
          if (none === result.length - 1) {
            return '선택';
          }
        }
      }
      return (win > 0 ? win + '승' : '') + (lose > 0 ? lose + '패' : '');
    },
    pushMatchResult(matchId) {
      this.$router.push(`match/${matchId}`);
    },
  },
  async created() {
    const params = this.$route.params;
    this.id = params.id;
    this.matches = await MatchApi.inquire_all(params.id);
  },
};
</script>

<style scoped></style>
