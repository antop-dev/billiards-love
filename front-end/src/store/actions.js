import * as types from './mutations-type';

/**
 * 로그인을 위해 필요한 정보를 요청합니다
 * @param commit
 * @param info = { "appId": "",
                   "encodeKey": "",
                   "kakaoKey": "" }
 */
export const saveLoginRequestInfo = ({ commit }, info) => {
  commit(types.SAVE_REQUEST_INIT_INFO, info);
};
