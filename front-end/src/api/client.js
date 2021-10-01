import axios from 'axios';
import store from '../store';

const HttpClient = axios.create({
  timeout: 30000,
  headers: {
    'Access-Control-Allow-Origin': '*',
    'Content-Type': 'application/json; charset = utf-8',
  },
});

HttpClient.interceptors.request.use(
  config => {
    config.headers['Authorization'] = store.state.login_info.token;
    return config;
  },
  function(error) {
    return Promise.reject(error);
  },
);

HttpClient.interceptors.response.use(
  response => {
    return response;
  },
  error => {
    alert(error);
    return Promise.reject(error);
  },
);

HttpClient.interceptors.response.use(
  response => {
    return response;
  },
  error => {
    alert(error);
    return Promise.reject(error);
  },
);

HttpClient.interceptors.response.use(
  response => {
    return response;
  },
  error => {
    alert(error);
    return Promise.reject(error);
  },
);

export default HttpClient;
