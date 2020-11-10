import * as types from './mutations-type';

export default {
  [types.SAVE_REQUEST_INIT_INFO](state, payload) {
    state.loginRequestInfo.deviceId = payload.deveiceId;
    state.loginRequestInfo.requestId = payload.requestId;
  },
  [types.SAVE_REQUEST_LOGIN_INFO](state, payload) {
    state.loginRequestInfo.appId = payload.appId;
    state.loginRequestInfo.kakaoKey = payload.kakaoKey;
    state.loginRequestInfo.encodeKey = payload.encodeKey;
  },
};
