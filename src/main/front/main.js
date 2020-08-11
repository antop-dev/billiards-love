import Vue from 'vue';
import VueMaterial from 'vue-material';
import App from './App';
import router from './router';
import 'vue-material/dist/vue-material.min.css';
import 'vue-material/dist/theme/default.css';

Vue.use(VueMaterial);



// eslint-disable-next-line no-new
window.$app = new Vue({
  el: '#app',
  router,
  render: (h) => h(App),
});
