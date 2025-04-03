<template>
    <div id="message" v-scroll-bottom="session">
        <ul v-if="currentSessionId==item.id" v-for="item in sessions">
            <li v-for="entry in item.messages">
                <p class="time">
                    <span>{{entry.date | time}}</span>
                </p>
                <div class="main" :class="{self:entry.self}">
                    <img class="avatar" :src="entry.self ? img : item.user.img" alt="">
                    <p class="text">{{entry.content}}</p>
                </div>
            </li>
        </ul>
    </div>
</template>

<script>
    import { mapState } from 'vuex'

    export default {
        name: 'message',
        data() {
            return {
                userInfo: {
                    username:'',
                    nickname:''
                },
                userId:window.sessionStorage.getItem('userId')
            }
        },
        computed: mapState([
            'sessions',
            'currentSessionId'
        ]),
        filters: {
            time(date) {
                if (date) {
                    date = new Date(date);
                }
                return `${date.getHours()}:${date.getMinutes()}`;
            }
        },
        directives: {/*vue的自定义指令*/
            // 发送消息后滚动到底部,用setTimeout的方法模拟
            'scroll-bottom'(el) {
                //console.log(el.scrollTop);
                setTimeout(function () {
                    el.scrollTop += 9999;
                }, 1)
            }
        },
        async getUserInfor(){
            await this.$axios.get(`${this.HOST}/user/seller/${this.userId}`,{
                params:{
                    id:this.userId
                }
            }).then(res =>{
                console.log('getUserInfor',res)
                if (res.data.code == 200) {
                    // this.$message.success("获取成功");
                    this.userInfo = res.data.data}
            }).catch(res=>{
            console.log('getUserInforError',res);
            // this.$message.error("获取失败");
        })
    },
        created(){
                this.getUserInfor()
            }
    }
</script>

<style lang="scss" scoped>
    #message {
        padding: 15px;
        max-height: 68%;
        overflow-y: scroll;

        ul {
            list-style-type: none;
            padding-left: 0px;
            li {
                margin-bottom: 15px;
            }
        }

        .time {
            text-align: center;
            margin: 7px 0;

            >span {
                display: inline-block;
                padding: 0 18px;
                font-size: 12px;
                color: #FFF;
                background-color: #dcdcdc;
                border-radius: 2px;
            }
        }

        .main {
            text-align: left;
            .avatar {
                float: left;
                margin: 0 10px 0 0;
                border-radius: 3px;
                width: 30px;
                height: 30px;

            }

            .text {
                display: inline-block;
                padding: 0 10px;
                max-width: 80%;
                background-color: #fafafa;
                border-radius: 4px;
                line-height: 30px;
            }
        }

        .self {
            text-align: right;

            .avatar {
                float: right;
                margin: 0 0 0 10px;
                border-radius: 3px;
                width: 30px;
                height: 30px;
            }

            .text {
                display: inline-block;
                padding: 0 10px;
                max-width: 80%;
                background-color: #b2e281;
                border-radius: 4px;
                line-height: 30px;
            }
        }
    }
</style>