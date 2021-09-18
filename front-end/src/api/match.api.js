import HttpClient from './client';

const MatchApi = class {
  #client;
  constructor(client) {
    this.#client = client;
  }
  valid(s) {
    console.log(s);
    // TODO
    return true;
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