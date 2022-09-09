import Vue from 'vue'
import App from './App.vue'
import router from './router/router'
import store from "./storage/storage";
import moment from 'moment';
import './assets/static/css/global.css'
// 1. 导入 element-ui
import ElementUI from 'element-ui'
// 2. 导入 element-ui 的样式表
import 'element-ui/lib/theme-chalk/index.css'
// 导入全局组件注册
import './global/regist'
//iconfont导入icon图标
import '@/assets/static/fonts/icon/iconfont.css'
// 被动（passive）监听器
import 'default-passive-events'

// 导入通用请求api
import { $post, $get, $delete, $put, $upload } from '@/axios/api/common-api'





Vue.prototype.$post = $post
Vue.prototype.$get = $get
Vue.prototype.$delete = $delete
Vue.prototype.$put = $put
Vue.prototype.$upload = $upload



Vue.config.productionTip = false
// 3. 将 element-ui 注册vue实例
Vue.use(ElementUI)

Vue.filter("DateTimeFormat", function (value) {
  return moment(value).format("YYYY-MM-DD HH:mm:ss");
});



const app = new Vue({
  store,
  router,
  render: h => h(App)
})

app.$mount('#app')