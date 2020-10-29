<template>
  <div>
    <md-table
      v-model="users"
      md-sort="rank"
      md-sort-order="asc"
      md-card
      md-fixed-header
    >
      <md-table-row slot="md-table-row" slot-scope="{ item }">
        <md-table-cell md-label="Rank" md-sort-by="rank" md-numeric>{{
          item.rank
        }}</md-table-cell>
        <md-table-cell md-label="참가자" md-sort-by="name">{{
          item.name
        }}</md-table-cell>
        <md-table-cell md-label="핸디" md-sort-by="handy" md-numeric>{{
          item.handy
        }}</md-table-cell>
        <md-table-cell md-label="진행률" md-sort-by="progress">{{
          item.progress
        }}</md-table-cell>
        <md-table-cell md-label="점수" md-sort-by="point">{{
          item.point
        }}</md-table-cell>
      </md-table-row>
    </md-table>
  </div>
</template>

<script>
export default {
  name: 'GameRank',
  data() {
    return {
      users: [
        {
          id: 1,
          rank: 1,
          name: '김형익',
          handy: 0,
          progress: 20,
          point: 2000,
        },
        {
          id: 2,
          rank: 2,
          name: '김띠용',
          handy: 100,
          progress: 20,
          point: 190,
        },
        {
          id: 3,
          rank: 3,
          name: '안탑',
          handy: 0,
          progress: 0,
          point: 1000,
        },
        {
          id: 4,
          rank: 4,
          name: '골드스푼',
          handy: 0,
          progress: 20,
          point: 300,
        },
      ],
    };
  },
};
</script>

<style scoped></style>
