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
          <tr v-for="item in users" :key="item.id">
            <td class="text-center">{{ item.opponent.no }}</td>
            <td>{{ item.opponent.nickname }}</td>
            <td><match-result value="test"></match-result></td>
            <td>{{ item.closed }}</td>
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
      showDialog: false,
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
    // RankApi.inquire();
    const params = this.$route.params;
    this.users = await MatchApi.inquire_all(params.id);
  },
};
</script>

<style scoped></style>
