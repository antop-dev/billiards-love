<template>
  <div>
    <app-header title="결과 입력" close-btn="true"></app-header>
    <div class="md-layout md-gutter md-alignment-center-center">
      <div class="md-layout-item md-size-90">
        <md-card>
          <md-card-area md-inset>
            <md-card-header class="md-layout md-alignment-center-center">
              <h2 class="md-display-2">Vs 상대</h2>
            </md-card-header>
          </md-card-area>

          <md-card-area md-inset>
            <md-card-content>
              <div class="md-layout md-gutter md-alignment-center">
                <div class="md-layout-item md-size-40 md-display-1">1경기</div>
                <div class="md-layout-item md-size-40 md-display-1">승</div>
              </div>
            </md-card-content>
            <md-card-content>
              <div class="md-layout md-gutter md-alignment-center">
                <div class="md-layout-item md-size-40 md-display-1">2경기</div>
                <div class="md-layout-item md-size-40 md-display-1">승</div>
              </div>
            </md-card-content>
            <md-card-content>
              <div class="md-layout md-gutter md-alignment-center">
                <div class="md-layout-item md-size-40  md-display-1">3경기</div>
                <div class="md-layout-item md-size-40  md-display-1">선택</div>
              </div>
            </md-card-content>
          </md-card-area>
          <div class="md-layout md-gutter md-alignment-center">
            <md-button class="md-raised">확인</md-button>
          </div>
        </md-card>
      </div>
    </div>
  </div>
</template>

<script>
import MatchApi from '../api/match.api';
import AppHeader from '@/common/AppHeader';
export default {
  components: { AppHeader },
  name: 'GameResult',
  async created() {
    const id = this.$store.state.match_detail.id;
    const matchResult = await MatchApi.inquire(id);
    console.log(matchResult);
  },
};
</script>

<style scoped></style>
