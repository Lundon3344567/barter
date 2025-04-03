import Vue from 'vue'
import Vuex from 'vuex'
import SockJS from 'sockjs-client'
import Stomp from 'stompjs'

Vue.use(Vuex)
const now = new Date();

const store = new Vuex.Store({
    state: {
    sessions:[{
        id:1,
        user:{
        name:'易物客服',
        img:'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic%2Fab%2Faf%2F4a%2Fabaf4a8418b24fa74ee5062e3e32d3f2.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642773961&t=9f3b1305201690b0be904e49ad805d61'//商家头像
        },
        messages:[{
        content:'hi，这里是易物客服',
        date:now
        },{
        content:'有什么需要帮助的吗',
        date:now
        },{
        content:'aaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbaaaaaaa',
        date:now
        }]
    },{
        id:2,
        user:{
        name:'绝不砍价',
        img:'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fnimg.ws.126.net%2F%3Furl%3Dhttp%253A%252F%252Fdingyue.ws.126.net%252F2021%252F0911%252F97d337a9j00qz9dxj000tc000hs00hsc.jpg%26thumbnail%3D650x2147483647%26quality%3D80%26type%3Djpg&refer=http%3A%2F%2Fnimg.ws.126.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642773961&t=d3ca10bf0b92d2683eab738e3332f82a'//商家头像
        },
        messages:[{
        content:'绝不砍价哦',
        date:now
        }]
    }],
        messages: [],
        admins: [],
        currentSessionId: 1,
        filterKey: '',
        // 设置属性，用于保存后台接口返回的权限数据
        Stomp:null,
        permission:[]
    },
    mutations: {
        changeCurrentSessionId(state, id) {
            state.currentSessionId = id;
        },
        addMessage(state, msg) {
            state.sessions[state.currentSessionId - 1].messages.push({
                content: msg,
                date: new Date(),
                self: true
            })
        },
        INIT_DATA(state) {
            //浏览器本地的历史聊天记录
            let data = localStorage.getItem('vue-chat-session');
            // // console.log(data)
            if (data) {
                state.sessions = JSON.parse(data);
            }
        },
        INIT_ADMINS(state,date){
            state.admins = data;
        },
        SET_PERMISSION(state,permission){//permission 为传入的权限标识集合
            // 传入的权限集合 赋值给状态中的 permission
            state.permission = permission;
        }
    },
    actions: {
    initData (context) {
        context.commit('INIT_DATA')
    },
        connect(context){
            context.state.Stomp = Stomp.over(new SockJS('/ws/ep'));
            let token = window.sessionStorage.getItem('tokenStr');
            context.state.Stomp.connect({'Auth-Token':token},success => {
            // context.state.stomp.subscribe(destination:'user/queue/chat',callback:msq => {
            context.state.stomp.subscribe('user/queue/chat',callback=msg => {
                console.log(msq.body);
            })
            },error => {

            })
        },
        initData(context) {
            getRequest(url,'/user/seller/{id}').then(onfulfilled, resp=>{
                if(resp){
                    context.commit('INIT_admins',resp)
                }
            })
        },
        SET_PERMISSION(context,permission){
            // 提交到 mutation 中的 SET_PERMISSION 函数
            context.commit("SET_PERMISSION",permission);
        }
    },
    modules: {}
})

store.watch(function (state) {
    return state.sessions
}, function (val) {
    console.log('CHANGE: ', val);
    localStorage.setItem('vue-chat-session', JSON.stringify(val));
}, {
    deep: true/*开启watch监测的判断*/
})

export default store;