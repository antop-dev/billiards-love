<template>
  <div>
    <v-simple-table fixed-header>
      <template v-slot:default>
        <thead>
          <tr>
            <th scope="col" class="text-left">순위</th>
            <th scope="col" class="text-left">참가자</th>
            <th scope="col" class="text-center">핸디</th>
            <th scope="col" class="text-right">점수</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in players" :key="item.id">
            <td>{{ rank(item) }}</td>
            <td>{{ item.nickname }}</td>
            <td class="text-center">{{ item.handicap }}</td>
            <td class="text-right">{{ item.score || '' }}</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
  </div>
</template>

<script>
import RankApi from '../api/rank.api';

export default {
  name: 'ContestRank',
  data() {
    return {
      players: [],
    };
  },
  methods: {
    rank: function(item) {
      if (!item['rank']) return '';
      let v = '';
      if (item['variation'] > 0) v = '▲';
      if (item['variation'] < 0) v = '▼';
      return item['rank'] + ' ' + v;
    },
  },
  async created() {
    this.players = await RankApi.inquire(this.$route.params.id);
  },
};
</script>

<style scoped></style>
