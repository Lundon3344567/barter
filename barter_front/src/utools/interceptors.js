import router from "@/router";
import axios from "axios";
import Vue from "vue";
import {Encrypt} from "@/utools/utils";
//导入Nprogress
import Nprogress from 'nprogress'
import 'nprogress/nprogress.css'
axios.interceptors.request.use((config) => {
    Nprogress.start()
    console.log('config',config);
    config.headers.Authorization = window.sessionStorage.getItem('token');
    // 最后必须return config
    return config;
});

/* 响应拦截器 */
axios.interceptors.response.use(response =>{
    Nprogress.done()
    console.log('response',response)
    if(response.data.code==410){
        console.log('111')
        Vue.prototype.$message.error('请先登录')
        console.log('222')
        window.sessionStorage.clear() // 删除已经失效或过期的token（不删除也可以，因为登录后覆盖）
        // Vue.prototype.$router.replace({
        //     path: '/' + Encrypt('login')
        // }).then(r  =>{
        //     console.log('r',r)
        // })
        router.replace('/' + Encrypt('login'))
    }else if(response.data.code==401){
        Vue.prototype.$message.error('请勿频繁请求')
    }else{
        return response
    }

},error => {
    const { status } = error.response
    console.log('status',status)
    if(status === 423){
        Vue.prototype.$message.error('请重新登录')
        window.sessionStorage.clear() // 删除已经失效或过期的token（不删除也可以，因为登录后覆盖）
        // Vue.prototype.$router.replace({
        //     path: '/' + Encrypt('login')
        // }).then(r  =>{
        //     console.log('r',r)
        // })
        router.replace('/' + Encrypt('allgoods'))
    }
   else if (status==404){
        router.replace('/' + Encrypt('notFound'))
    }

})


