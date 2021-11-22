import Vue from 'vue';
import Vuetify from 'vuetify/lib';
import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';

Vue.use(Vuetify, {});
Vue.use(VueToast, {
  position: 'top',
});

export default new Vuetify({});
