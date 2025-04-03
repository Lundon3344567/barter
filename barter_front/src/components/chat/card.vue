<template>
    <div id="card">
        <header>
            <img class="avatar" v-bind:src="userInfo.nickname" v-bind:alt="userInfo.username">
            <p class="name">{{userInfo.username}}</p>
            <!-- <el-image :src="userInfo.nickname" class="avatar"></el-image>
            <el-descriptions-item class="name">{{userInfo.username}}</el-descriptions-item> -->
        </header>
        <footer>
            <input class="search" type="text" v-model="$store.state.filterKey" placeholder="search user...">
        </footer>
    </div>
</template>

<script>
    export default {
        name: 'card',
        data() {
            return {
                userInfo: {
                    username:'',
                    nickname:''
                },
                userId:window.sessionStorage.getItem('userId')
            }
        },
        methods:{
                // 获取用户信息
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
        },
        created(){
                this.getUserInfor()
            }
    }
</script>

<style lang="scss" scoped>
    #card {
        padding: 12px;

        .avatar {
            width: 40px;
            height: 40px;
            vertical-align: middle;
            /*图片和文字居中对齐*/
        }

        .name {
            display: inline-block;
            padding: 10px;
            margin-bottom: 15px;
            font-size: 16px;
        }

        .search {
            background: #26292E;
            height: 30px;
            line-height: 30px;
            padding: 0 10px;
            border: 1px solid #3a3a3a;
            border-radius: 4px;
            outline: none;
            /*鼠标点击后不会出现蓝色边框*/
            color: #FFF;
        }
    }
</style>