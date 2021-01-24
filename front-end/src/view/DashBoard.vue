<template>
  <div>
    <app-header title="대시보드" menu-btn="true"></app-header>
    <div class="board" v-for="content in contentsList" v-bind:key="content.id">
      <board-contents
        :title="content.title"
        :isOpen="content.isOpen"
        :rank="content.rank"
        @click.native="getDetail(content.id)"
      ></board-contents>
    </div>
  </div>
</template>
<script>
import BoardContents from '@/dashboard/BoardContents';
import AppHeader from '@/common/AppHeader';
export default {
  name: 'DashBoard',
  data: function() {
    return {
      contentsList: [
        {
          id: 1,
          title: '8월 챔프전',
          isOpen: true,
          rank: 1,
        },
        {
          id: 2,
          title: '7월 챔프전',
          isOpen: false,
          rank: 2,
        },
      ],
    };
  },
  components: { BoardContents, AppHeader },
  methods: {
    getDetail(id) {
      this.$router.push({ path: '/game', query: { id } });
    },
  },
};
</script>

<style scoped>
.board {
  margin-top: 20px;
}
</style>
