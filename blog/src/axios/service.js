import axios from "axios";
import  router  from "@/router/router";
import { Promise } from "core-js";
import { Message } from 'element-ui';

const service = axios.create({
    // baseURL: 'http://localhost:12306',
     baseURL: '/api',
    timeout: 7000, // 请求超时时间

});

service.interceptors.request.use(config => {
    //TODO 自动带验证请求头
    return config;
}, error => {
    
    Message.error('网络错误')
    Promise.reject(error)
})
service.interceptors.response.use(response => {
    if (response.data.code === 0) {
        return response.data;

    }else if(response.data.code===200101){
        Message.error(response.data.msg)
        return Promise.reject(response.data)

    }else {
        Message.error(response.data.msg)
        return Promise.reject(response.data)
    }

}, error => {
    
    if(error.response.status === 401 ){
        Message.error('登录过期，请重新登录')
        router.push('/login') 
    }else if(error.response.status === 403){
        Message.error('没有权限')
    }else if(error.response.status === 400){
        Message.error('请求错误！')
    }else{
        Message.error('服务器繁忙，请稍后再试！')
    }
    return Promise.reject(error)

})


export default service