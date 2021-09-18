import HttpClient from './client';

const RankApi = class {
  #client;
  constructor(client) {
    this.#client = client;
  }
  valid(s) {
    console.log(s);
    // TODO
    return true;
  }
  inquire(id) {
    return this.#client
      .get('/api/v1/contest/' + id + '/rank')
      .then(({ data }) => data || {});
  }
};

export default new RankApi(HttpClient);
