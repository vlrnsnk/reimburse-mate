import axios, { AxiosInstance } from 'axios';

const api: AxiosInstance = axios.create({
  // baseURL: 'https://ill-cicely-vlrnsnk-c006a76e.koyeb.app/api/v1',
  baseURL: 'https://reimburse-mate.onrender.com/api/v1',
  // baseURL: 'http://localhost:8080/api/v1',
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

export { api };
