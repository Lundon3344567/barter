<template>
    <div id="app">
        <div class="sidebar">
            <card></card>
            <list></list>
        </div>
        <div class="main">
            <message></message>
            <usertext></usertext>
        </div>
    </div>
</template>
<script>
    import card from '../../components/chat/card'
    import list from '../../components/chat/list'
    import message from '../../components/chat/message'
    import usertext from '../../components/chat/usertext'

    export default {
        name:'Chat',
        data(){
            return{

            }
        },
        mounted:function(){
            this.$store.dispatch('initDate');
            // this.$store.dispatch('connect');
        },
        components:{
            card,
            list,
            message,
            usertext
        },
    }
</script>
<style lang='scss' scoped>
    #app{
        margin: 50px auto;
        width: 800px;
        height: 600px;
        overflow: hidden;
        border-radius: 10px;
        .sidebar, .main{
            height: 100%;
        }
        .sidebar{
            float: left;
            color: #f4f4f4;
            background-color: #2e3238;
            width: 200px;
        }
        .main{
            position: relative;
            overflow: hidden;
            background-color: #eee;
        }
    }
</style>