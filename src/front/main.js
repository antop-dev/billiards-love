import Vue from 'vue';
import VueMaterial from 'vue-material';
import App from './App';
import 'vue-material/dist/vue-material.min.css';
import 'vue-material/dist/theme/default.css'


Vue.use(VueMaterial);

// eslint-disable-next-line no-new
window.$app = new Vue({
  el: '#app',
  render: (h) => h(App),
});
