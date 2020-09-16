import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import 'buefy/dist/buefy.css'
import '@fortawesome/fontawesome-free/css/all.css'
import Buefy from 'buefy'
import 'nprogress/nprogress.css'
import NProgress from 'nprogress/nprogress'

Vue.use(Buefy)
Vue.config.productionTip = false

router.beforeEach((to, from, next) => {
    NProgress.start()
    next()
})

router.afterEach((to, from) => {
    NProgress.done()
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
