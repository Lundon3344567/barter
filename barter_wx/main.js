
Vue.prototype.HOST = "http://localhost:8081"
// #ifndef VUE3
import Vue from 'vue'
import App from './App'
// import myRequest from 'utils/api.js'
// Vue.prototype.$myRequest = myRequest

import request from './utils/api.js'
Vue.prototype.$http = request

Vue.config.productionTip = false
App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
import App from './App.vue'

import request from './utils/api.js'
Vue.prototype.$http = request

// import myRequest from 'utils/api.js'
// Vue.prototype.$myRequest = myRequest
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif