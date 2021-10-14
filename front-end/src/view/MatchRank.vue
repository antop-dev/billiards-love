<template>
  <div>
    <v-simple-table>
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-left">
              Rank
            </th>
            <th class="text-left">
              참가자
            </th>
            <th class="text-left">
              핸디
            </th>
            <th class="text-left">
              진행률
            </th>
            <th class="text-left">
              점수
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in users" :key="item.id">
            <td>{{ item.rank }}</td>
            <td>{{ item.nickname }}</td>
            <td>{{ item.handicap || 0 }}</td>
            <td>{{ item.progress || 0 }}</td>
            <td>{{ item.score || 0 }}</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
  </div>
</template>

<script>
import RankApi from '../api/rank.api';

export default {
  name: 'GameRank',
  data() {
    return {
      users: [],
    };
  },
  methods: {},
  async created() {
    try {
      this.users = await RankApi.inquire(this.$store.state.match_detail.id);
    } catch (e) {
      console.log(e);
    }
  },
};
</script>

<style scoped></style>
