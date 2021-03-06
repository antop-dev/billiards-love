import HttpClient from './client';
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
   * @param deviceID 방문자 식별갓
   * @param requestId 최초 서버 인증을 위한 정보
   * @returns data: { "appId" : "","encodedId" :"", "kakaoKey": ""}
   */
  requestInitKey() {
    return this.#client.post('/api/v1/init', {}).then(({ data }) => data || {});
  }

  executeLogin(body) {
    return this.#client
      .post('/api/v1/logged-in', body)
      .then(({ data }) => data.token || {});
  }
};

export default new loginApi(HttpClient);
