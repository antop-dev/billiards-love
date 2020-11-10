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
   * @returns {*}
   */
  requestInitKey(deviceID, requestId) {
    const header = {
      'X-DEVICE-ID': deviceID,
      'X-REQUEST-ID': requestId, // TODO 약속된 키
    };
    return this.#client.get('/v1/init', {}, header).then(({ data }) => data);
  }
};

export default new loginApi(HttpClient);
