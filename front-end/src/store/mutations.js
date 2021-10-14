import * as types from './mutations-type';

export default {
  /**
   * 로그인 후 Response 정보를 저장합니다
   */
  [types.SAVE_LOGIN_REQUEST_INFO](state, payload) {
    /**
     * login_info: {
      token: '',
      registered: false,
      member: {
        handicap: '',
        id: '',
        nickname: '',
        thumbnail: '',
      },
    },
     */
    state.login_info = payload;
  },
  [types.SAVE_MATCH_ID](state, payload) {
    state.match_detail.id = payload;
  },
  [types.SAVE_MATCH_INFO](state, payload) {
    state.match_detail = payload;
  },
};
