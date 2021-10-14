import HttpClient from './client';

const ContestApi = class {
  #client;
  constructor(client) {
    this.#client = client;
  }
  valid(s) {
    console.log(s);
    // TODO
    return true;
  }
  inquire_contests() {
    return this.#client.get('/api/v1/contests').then(({ data }) => data || {});
  }
  inquire_contest(id) {
    // 데이터 없을 때 404 말고 따른거..
    return this.#client
      .get('/api/v1/contest/' + id)
      .then(({ data }) => data || {});
  }
  inquire_rank(id) {
    return this.#client
      .get('/api/v1/contest/' + id + '/ranks')
      .then(({ data }) => data || {});
  }
  register(data) {
    this.valid(data);
    return this.#client
      .post('/api/v1/contest', data)
      .then(({ data }) => data || {});
  }
};

export default new ContestApi(HttpClient);
