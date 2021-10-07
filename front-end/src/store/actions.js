import * as types from './mutations-type';

export const saveLoginRequestInfo = ({ commit }, info) => {
  commit(types.SAVE_LOGIN_REQUEST_INFO, info);
};

export const saveMatchId = ({ commit }, info) => {
  commit(types.SAVE_MATCH_ID, info);
};

export const saveMatchInfo = ({ commit }, info) => {
  commit(types.SAVE_MATCH_INFO, info);
};
