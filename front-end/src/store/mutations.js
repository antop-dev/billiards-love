import * as types from './mutations-type';

export default {
  /**
   * 카카오 초기화 정보를 요청하기 위한 값을 저장합니다
   * @param state
   * @param payload
   */
  [types.SAVE_REQUEST_INIT_INFO](state, payload) {
    state.loginRequestInfo.deviceId = payload.deviceId;
    state.loginRequestInfo.requestId = payload.requestId;
  },
  /**
   * 로그인을 하기위한 정보를 저장합니다.
   * @param state
   * @param payload
   */
  [types.SAVE_REQUEST_LOGIN_INFO](state, payload) {
    state.loginRequestInfo.appId = payload.appId;
    state.loginRequestInfo.kakaoKey = payload.kakaoKey;
    state.loginRequestInfo.encodeKey = payload.encodeKey;
  },
};
