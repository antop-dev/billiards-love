<template>
  <div>
    <v-card class="mx-auto">
      <v-list two-line subheader>
        <v-subheader>대회정보</v-subheader>

        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>대회명</v-list-item-title>
            <v-list-item-subtitle>{{ title }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>시작일</v-list-item-title>
            <v-list-item-subtitle>{{ startDate }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>참가자 수</v-list-item-title>
            <v-list-item-subtitle>{{ currentJoiner }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-list>
      <v-list>
        <v-list-item>
          <v-list-item-content>
            <v-row class="mb-6">
              <v-col cols="6" sm="6">
                <v-text-field
                  label="참가핸디"
                  v-model="handicap"
                ></v-text-field>
              </v-col>
              <v-col cols="6" sm="6">
                <div class="text-right">
                  <v-btn class="ma-2" color="secondary" @click="showJoinDialog"
                    >참가</v-btn
                  >
                </div>
              </v-col>
            </v-row>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-card>
    <template>
      <v-dialog
        color="red lighten-2"
        v-model="dialog"
        persistent
        max-width="290"
      >
        <v-card>
          <v-card-title class="text-h5">
            참가하시겠습니까?
          </v-card-title>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="green darken-1" text @click="dialog = false">
              아니요
            </v-btn>
            <v-btn color="green darken-1" text @click="join">
              예
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </template>
  </div>
</template>

<script>
import ContestApi from '../api/contest.api';

export default {
  name: 'JoinContest',
  data() {
    return {
      id: '',
      title: '',
      startDate: '',
      currentJoiner: 0,
      dialog: false,
      handicap: 0,
    };
  },
  methods: {
    setInfo(contest) {
      this.id = contest.id;
      this.title = contest.title;
      this.startDate = contest.startDate;
      this.currentJoiner = contest.currentJoiner;
    },
    async inquire() {
      const contest = await ContestApi.inquire_contest(this.id);
      this.setInfo(contest);
    },
    showJoinDialog() {
      this.dialog = true;
    },
    async join() {
      try {
        await ContestApi.join_contest(this.id, this.handicap);
        this.$toast.info('참가신청 되었습니다');
        await this.$router.push({ name: 'info', query: this.id });
      } catch (e) {
        alert(e);
      } finally {
        this.dialog = false;
      }
    },
  },
  created() {
    const params = this.$route.params;
    this.id = params.id;
    this.inquire();
  },
};
</script>

<style scoped></style>
