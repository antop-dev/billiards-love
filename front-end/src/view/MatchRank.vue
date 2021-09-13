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
            <td>{{ item.handicap }}</td>
            <td>{{ item.progress }}</td>
            <td>{{ item.number }}</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
  </div>
</template>

<script>
import ContestApi from '../api/contest.api';

export default {
  name: 'GameRank',
  data() {
    return {
      users: [],
    };
  },
  async created() {
    try {
      const inquireRank = await ContestApi.inquire_rank(this.$route.params.id);
      this.users = inquireRank;
    } catch (e) {
      console.log(e);
    }
  },
};
</script>

<style scoped></style>
