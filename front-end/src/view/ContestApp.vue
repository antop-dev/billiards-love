<template>
  <div>
    <contest-tabs :id="id" :title="title"></contest-tabs>
    <v-container>
      <v-card outlined>
        <router-view></router-view>
      </v-card>
    </v-container>
  </div>
</template>

<script>
import ContestTabs from '@/status/ContestTabs';
export default {
  name: 'ContestApp',
  components: { ContestTabs },
  props: {
    title: String,
  },
  data() {
    return {
      id: '',
    };
  },
  methods: {},
  created() {
    const params = this.$route.params;
    if (params) {
      this.id = params.id;
    }
  },
};
</script>

<style scoped></style>
