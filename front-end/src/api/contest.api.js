import HttpClient from './client';

const ContestApi = class {
  #client;
  constructor(client) {
    this.#client = client;
  }
  inquire() {
    return this.#client.get('/api/v1/contests').then(({ data }) => data || {});
  }
};

export default new ContestApi(HttpClient);
