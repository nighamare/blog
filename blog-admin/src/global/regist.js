// regist.js  这里注册我们编写的公共组件
import Vue from 'vue'
// 全局引入Header组件和Footer组件
import Header from '../components/common/Header.vue'
import Footer from '../components/common/Footer.vue'
import CommonTable from '../components/common/CommonTable.vue'



Vue.component(Header.name, Header)
Vue.component(Footer.name, Footer)
Vue.component(CommonTable.name, CommonTable)

