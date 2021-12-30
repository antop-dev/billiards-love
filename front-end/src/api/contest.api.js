import HttpClient from './client';

const ContestApi = class {
  #client;
  constructor(client) {
    this.#client = client;
  }
  valid() {
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
  join_contest(id, handicap) {
    return this.#client.post(`/api/v1/contest/${id}/join`, { handicap });
  }
  cancel_contest(id) {
    return this.#client.delete(`/api/v1/contest/${id}/join`);
  }
  open_contest(id) {
    return this.#client.post(`/api/v1/contest/${id}/open`);
  }
  start_contest(id) {
    return this.#client.post(`/api/v1/contest/${id}/start`);
  }
  stop_contest(id) {
    return this.#client.post(`/api/v1/contest/${id}/stop`);
  }
  end_contest(id) {
    return this.#client.post(`/api/v1/contest/${id}/end`);
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
