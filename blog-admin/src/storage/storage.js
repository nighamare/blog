import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)


const store =   new Vuex.Store({
  state: {
    token: '',
    userInfo: JSON.parse(sessionStorage.getItem("adminInfo"))
    //sessionStorage中得userInfo是序列化得字符串，这里要将他反序列化成对象。
  },
    mutations: {
      login: (state, adminInfo) => {
        state.adminInfo = adminInfo
        sessionStorage.setItem("adminInfo", JSON.stringify(adminInfo))
      },
      token: (state, token) => {
            state.token = token
      localStorage.setItem("token", token)
      sessionStorage.setItem("token", token)
    },
    logout: (state) => {
      localStorage.setItem("token", '')
      sessionStorage.setItem("adminInfo", JSON.stringify(''))
      state.adminInfo = {}
    }
  },
  getters: {
    getAccount: state => {
      return state.adminInfo
        }
    }
})

window.onload = () => {
  sessionStorage.getItem('token')

}

export default store