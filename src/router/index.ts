import { createRouter, createWebHistory } from 'vue-router'
//import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    // {
    //   path: '/',
    //   name: 'home',
    //   component: { template: '<h1>Welcome to Home Page</h1>' }
    // },
    // {
    //   path: '/about',
    //   name: 'about',
    //   component: { template: '<h1>Welcome to Home Page</h1>'}
    // },

  ],
})

export default router
