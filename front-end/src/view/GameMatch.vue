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
        <md-table-cell md-label="번호" md-sort-by="no" md-numeric>{{
          item.no
        }}</md-table-cell>
        <md-table-cell md-label="참가자명" md-sort-by="name">{{
          item.name
        }}</md-table-cell>
        <md-table-cell md-label="결과" md-sort-by="result">{{
          item.result
        }}</md-table-cell>
        <md-table-cell md-label="확정" md-sort-by="confirm">{{
          item.confirm
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
          no: 1,
          name: '김형익',
          result: '4승',
          confirm: false,
        },
        {
          id: 2,
          no: 2,
          name: '김띠용',
          result: '입력',
          confirm: true,
        },
        {
          id: 3,
          no: 3,
          name: '안탑',
          result: '입력',
          confirm: true,
        },
        {
          id: 4,
          no: 4,
          name: '골드스푼',
          result: '입력',
          confirm: true,
        },
      ],
    };
  },
};
</script>

<style scoped></style>
