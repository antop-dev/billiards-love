import Vue from 'vue';
import Vuex from 'vuex';
import mutations from './mutations';
import * as actions from './actions';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    match_detail: {
      id: 0,
    },
    login_info: {
      token: '',
      registered: false,
      member: {
        handicap: '',
        id: '',
        nickname: '',
        thumbnail: '',
      },
    },
    secret_key: '',
  },
  mutations,
  actions,
});
