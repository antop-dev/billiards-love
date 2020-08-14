import * as types from './mutation-types';

export default {
  [types.SET_USER_INFO](state, payload) {
    state.profile = payload;
  },
};
