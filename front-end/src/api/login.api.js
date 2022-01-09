import HttpClient from './client';
import store from '../store';
import aes256 from '../util/aes256';

/**
 * 로그인 API
 * @type {loginApi}
 */
const loginApi = class {
  #client;
  constructor(client) {
    this.#client = client;
  }
  /**
   * 카카오 초기화 정보를 요청합니다.
   * @returns data: { "appId" : "","encodedId" :"", "kakaoKey": ""}
   */
  requestInitKey() {
    return this.#client.post('/api/v1/init', {}).then(({ data }) => data || {});
  }

  executeLogin(userInfo) {
    const info = {
      id: userInfo.id,
      connectedAt: userInfo.connected_at,
      profile: {
        nickname: aes256.encrypt(
          userInfo.kakao_account.profile.nickname,
          store.state.secret_key,
        ),
        imageUrl: userInfo.kakao_account.profile.profile_image_url,
        thumbnailUrl: userInfo.kakao_account.profile.thumbnail_image_url,
        needsAgreement: userInfo.kakao_account.profile_needs_agreement,
      },
    };
    return this.#client
      .post('/api/v1/logged-in', info)
      .then(({ data }) => data || {});
  }
  registerMember(input) {
    return this.#client
      .put('/api/v1/member', input)
      .then(({ data }) => data || {});
  }
};

export default new loginApi(HttpClient);
