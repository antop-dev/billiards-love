import axios from 'axios';

const HttpClient = axios.create({
  timeout: 3000,
});

export default HttpClient;
