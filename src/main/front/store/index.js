import Vue from 'vue';
import Vuex from 'vuex';
import mutations from './mutations';
import { getUserInfo } from './getters';

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    profile: {
      nickname: '',
      profile_image_url: '',
      thumbnail_image_url: '',
    },
  },
  mutations,
  getters: {
    getUserInfo,
  },
});


export default store;