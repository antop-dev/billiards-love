<template>
  <div>
    <app-header title="결과 입력" close-btn="true"></app-header>
    <div class="md-layout md-gutter md-alignment-center-center">
      <div class="md-layout-item md-size-90">
        <md-card>
          <md-card-area md-inset>
            <md-card-header>
              <h2 class="md-display-2">Vs 상대</h2>
            </md-card-header>
          </md-card-area>
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
        </md-card>
      </div>
    </div>
    <div>
      <md-bottom-bar md-type="shift">
        <div class="md-layout md-gutter md-alignment-center-center">
          <div class="md-layout-item">
            <md-button class="md-raised">확인</md-button>
          </div>
        </div>
      </md-bottom-bar>
    </div>
  </div>
</template>

<script>
import AppHeader from '@/common/AppHeader';
export default {
  components: { AppHeader },
  name: 'GameResult',
};
</script>

<style scoped></style>
