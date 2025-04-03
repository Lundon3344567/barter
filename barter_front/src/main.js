import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import router from './router'
import store from './store'
// import md5 from 'js-md5'
import  qs from 'qs'
import'element-ui/lib/theme-chalk/index.css'
import '../src/assets/css/global.css'
import './assets/iconfont/iconfont.css'
import  axios from 'axios'
import {Decrypt,Encrypt} from "@/utools/utils";
import Mock from 'mockjs'
import TreeTable from 'vue-table-with-tree-grid'
import Blob from '../src/utools/Blob'
import Export2Excel from '../src/utools/Export2Excel'
import 'lib-flexible/flexible'
import JSONbig from 'json-bigint'
import ktButton from "@/components/ktButton"
import Storage from '../src/utools/Storage'

Vue.config.productionTip = false
//全局挂载axios
Vue.prototype.$axios = axios
Vue.prototype.$http = axios
Vue.prototype.$ajax = axios
Vue.prototype.HOST = "/api"
Vue.config.productionTip = false
// Vue.prototype.$router=router
Vue.component('tree-table',TreeTable)
Vue.use(ElementUI);
Vue.use(Mock)
//全局使用权限控制按钮
Vue.component('ktButton',ktButton)
import preventReClick from './utools/preventReClick'
import '../src/utools/interceptors'
//公共样式
import '../src/assets/css/common.less'
import 'lib-flexible'

// Vue.prototype.$md5 = md5
// http://barter.free.idcfengye.com
// axios.defaults.baseURL='http://barter.free.idcfengye.com'


//携带cookie
// axios.defaults.withCredentials = true;
// axios.defaults.transformRequest = [
//   function (data) {
//     return qs.stringify(data)
//   }
// ];
//携带token


new Vue({
  axios,
  router,
  store,
  render: h => h(App)
}).$mount('#app')


