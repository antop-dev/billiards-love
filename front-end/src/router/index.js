import Vue from 'vue';
import VueRouter from 'vue-router';
import routes from './routes';
import store from '../store';

Vue.use(VueRouter);

const router = new VueRouter({
  mode: 'history',
  routes,
});

router.beforeEach((to, from, next) => {
  if (!store.state.accessToken && to.path !== '/login') {
    next({ path: '/login', query: { redirect: to.fullPath } });
  } else {
    if (to.path === '/') {
      next('/login');
    }
    // 최초에 무조건 타는 루트
    next();
  }
});

export default router;
