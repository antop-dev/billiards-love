import HttpClient from './client';

const RankApi = class {
  #client;
  constructor(client) {
    this.#client = client;
  }
  valid() {
    // TODO
    return true;
  }
  inquire(id) {
    return this.#client
      .get('/api/v1/contest/' + id + '/ranks')
      .then(({ data }) => data || {});
  }
};

export default new RankApi(HttpClient);
