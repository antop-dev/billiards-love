import HttpClient from './client';

const loginApi = class {
  #client;
  constructor(client) {
    this.#client = client;
  }

  requestInitKey(deviceID, requestId) {
    const header = {
      'X-DEVICE-ID': deviceID,
      'X-REQUEST-ID': requestId, // TODO 약속된 키
    };
    return this.#client.get('/v1/init', {}, header).then(({ data }) => data);
  }
};

export default new loginApi(HttpClient);
