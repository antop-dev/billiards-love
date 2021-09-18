import Vue from 'vue';
import VueMaterial from 'vue-material';
import App from './App';
import router from './router';
import store from './store';
import 'vue-material/dist/vue-material.min.css';
import 'vue-material/dist/theme/default.css';
import '../public/index.css';
import vuetify from './plugins/vuetify';

Vue.use(VueMaterial);

// eslint-disable-next-line no-new
const initVue = () => {
  window.$app = new Vue({
    el: '#app',
    router,
    store,
    vuetify,
    render: h => h(App),
  });
};

initVue();
