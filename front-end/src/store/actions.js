import * as types from './mutations-type';

export const saveLoginRequestInfo = ({ commit }, info) => {
  commit(types.SAVE_LOGIN_REQUEST_INFO, info);
};
