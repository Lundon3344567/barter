import Vue from "vue";
import VueRouter from "vue-router";
import {Decrypt,Encrypt} from "@/utools/utils";
import { options } from "less";
Vue.use(VueRouter);

const routes = [
    {
        path:'/',
        redirect:'/'+Encrypt('allgoods')
    },
    {
        //登录页

        path: '/'+Encrypt('login'),
        name: "Login",
        component: () => import("../views/front/Login.vue")
    },
    {
        //找回密码
        path: '/'+Encrypt('lostpwd'),
        name: "LostPwd",
        component: () => import("../views/front/LostPwd.vue")
    },
    {
        //注册
        path:'/'+Encrypt('register'),
        name: "Register",
        component: () => import("../views/front/Register.vue")
    },
    {
        //购物主页
        path: '/'+Encrypt('home'),
        name: "Home",
        component: () => import("../views/front/Home.vue"),
        redirect: '/'+Encrypt('allgoods'),
        children: [
            //子路由
            {
                //所有商品
                path: '/'+Encrypt('allgoods'),
                name: "AllGoods",
                component: () => import("../views/front/AllGoods.vue")
            },
            {
                //商品详情
                path: '/'+Encrypt('goodsdetail'),
                name: "GoodsDetail",
                component: () => import("../views/front/GoodsDetail.vue")
            },
            {
                //我的商品
                path: '/'+Encrypt('mygoods'),
                name: "MyGoods",
                component: () => import("../views/front/MyGoods.vue")
            },
            {
                //帮助中心
                path: '/'+Encrypt('help'),
                name: "Help",
                component: () => import("../views/front/Help.vue")
            },
            {
                //搜索商品
                path: '/'+Encrypt('search'),
                component: () => import("../views/front/Search.vue")
            },
            {
                //分类页面
                path: '/'+Encrypt('classification'),
                component: () => import("../views/front/Classification.vue")
            },
            {
                //添加商品
                path: '/'+Encrypt('addgoods'),
                name: "AddGoods",
                component: () => import("../views/front/AddGoods.vue")
            },
            {
                //聊天
                path: '/'+Encrypt('chat'),
                name: "chat",
                component: () => import("../views/front/Chat.vue")
            },
        ]
    },

    {
        //个人信息页
        path: '/'+Encrypt('center'),
        name: "Center",
        component: () => import("../views/front/person/Center"),
        redirect: '/'+Encrypt('shopcart'),
        children: [
            //    子路由
            {
                //  欢迎页面
                path: '/'+Encrypt('welcome'),
                name: 'Welcome',
                component:() => import("../views/front/person/Welcome")
            },
            {
                //购物车页面
                path: '/'+Encrypt('shopcart'),
                name:'ShopCart',
                component:() => import("../views/front/person/ShopCart")
            },
            {
                //  账号信息页面
                path:'/'+Encrypt('accountinfo'),
                name:'AccountInfo',
                component:() => import("../views/front/person/AccountInfo")
            },
            {
                //  账号安全
                path: '/'+Encrypt('accountsecurity'),
                name: 'AccountSecurity',
                component:() => import("../views/front/person/AccountSecurity")
            },
            {
                //  个人中心
                path: '/'+Encrypt('person'),
                name:'Person',
                component:() => import("../views/front/person/Person")
            },
            {
                //  消息通知
                path: '/'+Encrypt('alerts'),
                name: 'Alerts',
                component:() => import("../views/front/person/Alerts")
            },
            {
                //  个人订单
                path: '/'+Encrypt('myorder'),
                name: 'MyOrder',
                component:() => import("../views/front/person/MyOrder")
            }


        ]
    },
    //管理员
    {
        //  管理员页面
        path: '/'+Encrypt('AdminHome'),
        name:'AdminHome',
        component:() => import('../views/back/AdminHome'),
        redirect: '/'+Encrypt('welcomeadmin'),
        children: [
            //  子路由
            {
                path: '/'+Encrypt('welcomeadmin'),
                name:'WelcomeAdmin',
                component:() => import('../views/back/WelcomeAdmin')
            },
            {
                path: '/'+Encrypt('ads'),
                name:'Ads',
                component:() => import('../views/back/ads/ads')
            },
            {
                path: '/'+Encrypt('cate'),
                name:'Cate',
                component:() => import('../views/back/goods/Cate')
            },
            {
                path: '/'+Encrypt('goods'),
                name:'Goods',
                component:() => import('../views/back/goods/goods')
            },
            {
                path: '/'+Encrypt('params'),
                name:'Params',
                component:() => import('../views/back/goods/Params')
            },
            {
                path: '/'+Encrypt('rights'),
                name:'Rights',
                component:() => import('../views/back/power/Rights')
            },
            {
                path: '/'+Encrypt('roles'),
                name:'Roles',
                component:() => import('../views/back/power/Roles')
            },
            {
                path: '/'+Encrypt('users'),
                name:'Users',
                component:() => import('../views/back/user/Users')
            },
            {
                path: '/'+Encrypt('logs'),
                name:'Logs',
                component:() => import('../views/back/logs/LogLists')
            },
            {
                path: '/'+Encrypt('abnormalog'),
                name: 'AbnormaLog',
                component:() => import('../views/back/logs/AbnormaLog')
            },
            {
                path: '/'+Encrypt('order'),
                name: 'Order',
                component:() => import('../views/back/orders/Order')
            }
        ]
    },
//    notfound页面
    {
        path: '*',
        name:'notfound',
        component:() => import("../views/front/notFound")
    }
];

const router = new VueRouter({
    routes
});
const originalPush = VueRouter.prototype.push

VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

//路由守卫，发起token验证，限制页面访问
router.beforeEach((to, from, next) => {
    if(to.path == '/'+Encrypt('login')||to.path=='/'+Encrypt('lostpwd')
        ||to.path=='/'+Encrypt('register'))
        return next()
    // 获取token
    // console.log('to',to.path)
    // console.log('from',from)
    // console.log('next',next)
    if(to.path=='/'+Encrypt('allgoods')||to.path=='/'+Encrypt('help')||
        to.path=='/'+Encrypt('classification')||to.path=='/'+Encrypt('search')||
        to.path=='/'+Encrypt('goodsdetail')){
        next()
    }else {
        let token = window.sessionStorage.getItem('token')
        if(!token){
            Vue.prototype.$message.error('请先登录')
            next('/'+Encrypt('login'))
        }
    }
    return next()
    // allgoods LjZT4+Yo/7y3nxdr8ufsUQ==
    //center CGLyseQZOKY9tLeVfQm1kg==

    // if (!token) {
    //   //如果没有token要跳转到个人中心、发布、聊天页面，则去登录页面
    //   if (to.path == Encrypt('allgoods')||to.path==Encrypt('goodsdetail')) {
    //     console.log('1111')
    //     next()
    //   } else {
    //     console.log('2222')
    //     //否则不是登录页
    //     next(Encrypt('login'));
    //   }
    //   next()
    // } else {
    //   console.log('3333')
    // 如果有token，就校验token合法性
    // axios({
    //   url: "/checkToken",
    //   method: "get",
    //   headers: {
    //     token: token
    //   }
    // }).then(response => {
    //   if (!response.data) {
    //     store.commit("setToken", ""); //校验失败，移除token
    //     next({ path: "/" });
    //   }
    // });
    // next();
    // }
    // let loc = Decrypt(to)
    // next('loc')
});


export default router;
