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
            <td class="text-center">{{ user.opponent.no }}</td>
            <td>{{ user.opponent.nickname }}</td>
            <td>
              <match-result :id="id" :opponent="user"></match-result>
            </td>
            <td>{{ user.closed }}</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
  </div>
</template>

<script>
// import MatchApi from '../api/match.api';
import MatchResult from './MatchResult';
export default {
  name: 'GameRank',
  components: { MatchResult },
  data() {
    return {
      id: '',
      users: [
        {
          id: 3823,
          opponent: {
            no: 2,
            id: 312,
            nickname: '홍길동',
          },
          result: ['WIN', 'LOSE', 'LOSE'],
          closed: true,
        },
      ],
    };
  },
  methods: {},
  async created() {
    const params = this.$route.params;
    this.id = params.id;
    // this.users = await MatchApi.inquire_all(params.id);
  },
};
</script>

<style scoped></style>
