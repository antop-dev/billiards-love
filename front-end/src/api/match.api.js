import HttpClient from './client';

const MatchApi = class {
  #client;
  constructor(client) {
    this.#client = client;
  }
  valid() {
    return true;
  }

  update(id, data) {
    return this.#client.put('/api/v1/match/' + id, { result: data });
  }
  inquire_all(id) {
    return this.#client
      .get('/api/v1/contest/' + id + '/matches')
      .then(({ data }) => data || {});
  }
  inquire(id) {
    return this.#client
      .get('/api/v1/match/' + id)
      .then(({ data }) => data || {});
  }
};

export default new MatchApi(HttpClient);
