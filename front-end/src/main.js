import Vue from 'vue';
import VueMaterial from 'vue-material';
import App from './App';
import router from './router';
import store from './store';
import FingerPrinter from './util/fingerPrint';
import 'vue-material/dist/vue-material.min.css';
import 'vue-material/dist/theme/default.css';
import '../public/index.css';

Vue.use(VueMaterial);

// 카카오 초기화 정보를 가져옵니다
const initInfo = async () => {
  store.state.accessToken = localStorage.getItem('access_token');
  // 최초의 로그인 정보를 로컬 스토리지에 가지고 있습니다.
  if (store.state.deviceId === null) {
    store.state.deviceId = await FingerPrinter();
  }
};

// eslint-disable-next-line no-new
const initVue = () => {
  window.$app = new Vue({
    el: '#app',
    router,
    store,
    render: h => h(App),
  });
};

initInfo().then(initVue);
