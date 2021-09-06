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
        <tbody>
          <tr v-for="item in users" :key="item.id">
            <td class="text-center">{{ item.opponent.no }}</td>
            <td>{{ item.opponent.nickname }}</td>
            <td><a href="#" @click="showResult(true)">test</a></td>
            <td>{{ item.closed }}</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
  </div>
</template>

<script>
import MatchApi from '../api/match.api';
export default {
  name: 'GameRank',
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
  methods: {
    showResult(toggle) {
      if (toggle) {
        this.showDialog = true;
      } else {
        this.showDialog = false;
      }
    },
  },
  async created() {
    // RankApi.inquire();
    const id = this.$store.state.match_detail.id;
    this.users = await MatchApi.inquire_all(id);
    this.users = [
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
    ];
  },
};
</script>

<style scoped></style>
