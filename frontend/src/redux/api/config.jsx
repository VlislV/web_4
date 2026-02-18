const API_CONFIG = {
  baseUrl: '/api',
  endpoints: {
    login: '/users/login',
    register: '/users/register',
    getPoints: '/points/getPoints',
    check:'/points/check',
  },
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  },
};
export default API_CONFIG;