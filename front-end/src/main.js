import Vue from 'vue';
import App from './App';
import router from './router';
import store from './store';
import '../public/index.css';
import vuetify from './plugins/vuetify';

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
