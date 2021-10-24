<template>
  <div>
    <game-tabs :id="id" :title="title"></game-tabs>
    <div>
      <v-sheet>
        <v-container>
          <v-card min-height="200">
            <router-view></router-view>
          </v-card>
        </v-container>
      </v-sheet>
    </div>
  </div>
</template>

<script>
import GameTabs from '@/status/GameTabs';
export default {
  name: 'GameStatus',
  components: { GameTabs },
  props: {
    title: String,
  },
  data() {
    return {
      id: '',
    };
  },
  methods: {},
  async created() {
    let params = this.$route.params;
    if (params) {
      this.id = params.id;
    }
  },
};
</script>

<style scoped></style>
