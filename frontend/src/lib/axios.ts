import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8085',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      // Server responded with error
      return Promise.reject({
        message: error.response.data?.message || 'Erro ao processar requisição',
        status: error.response.status,
      });
    } else if (error.request) {
      // Request made but no response
      return Promise.reject({
        message: 'Não foi possível conectar ao servidor. Verifique se o backend está rodando.',
        status: 0,
      });
    } else {
      // Error setting up request
      return Promise.reject({
        message: error.message || 'Erro desconhecido',
        status: 0,
      });
    }
  }
);

export { api };

