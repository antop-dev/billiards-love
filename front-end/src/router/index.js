import Vue from 'vue';
import VueRouter from 'vue-router';
import routes from './routes';
// import store from '../store';

Vue.use(VueRouter);

const router = new VueRouter({
  mode: 'history',
  routes,
});

router.beforeEach((to, from, next) => {
  if (from.path === '/' && to.path === '/register') {
    next();
  } else if (from.path === '/' && to.path !== '/') {
    next({ path: '/', query: { redirect: to.fullPath } });
  }
  next();
});

export default router;
