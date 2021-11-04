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
        <tbody v-if="users.length > 0">
          <tr v-for="user in users" :key="user.id">
            <td class="text-center">{{ user.opponent.number }}</td>
            <td>{{ user.opponent.nickname }}</td>
            <td>
              <match-result
                :id="id"
                :opponent-id="user.opponent.id"
                :value="renderButton(user.result)"
              ></match-result>
            </td>
            <td>{{ user.closed }}</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
  </div>
</template>

<script>
import MatchApi from '../api/match.api';
import MatchResult from './MatchResult';
export default {
  name: 'GameRank',
  components: { MatchResult },
  data() {
    return {
      id: '',
      users: [],
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
  },
  async created() {
    const params = this.$route.params;
    this.id = params.id;
    this.users = await MatchApi.inquire_all(params.id);
  },
};
</script>

<style scoped></style>
