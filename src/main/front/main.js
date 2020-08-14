import Vue from 'vue';
import VueMaterial from 'vue-material';
import App from './App';
import router from './router';
import store from './store';
import 'vue-material/dist/vue-material.min.css';
import 'vue-material/dist/theme/default.css';

Vue.use(VueMaterial);

window.$app = new Vue({
  el: '#app',
  router,
  store,
  render: (h) => h(App),
});
