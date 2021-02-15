<template>
  <div>
    <md-dialog :md-active.sync="showDialog">
      <md-dialog-title>Preferences</md-dialog-title>
    </md-dialog>

    <md-card>
      <md-card-media></md-card-media>
      <md-card-header>
        <div class="md-title">Title goes here</div>
        <div class="md-subhead">Subtitle here</div>
      </md-card-header>

      <md-card-content>
        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Optio itaque
        ea, nostrum odio. Dolores, sed accusantium quasi non, voluptas eius illo
        quas, saepe voluptate pariatur in deleniti minus sint. Excepturi.
      </md-card-content>

      <md-card-actions>
        <md-button>Action</md-button>
        <md-button>Action</md-button>
      </md-card-actions>
    </md-card>
  </div>
</template>

<script>
export default {
  name: 'GameInfo',
  data() {
    return {
      showDialog: false,
    };
  },
};
</script>

<style scoped>
.md-card {
  margin: 4px;
  display: inline-block;
  vertical-align: top;
}
</style>
