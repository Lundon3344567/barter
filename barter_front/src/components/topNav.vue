/**
 * @Description: 页面的公用头部
 * @author Lion-Air14
 * @date 2021/12/9
 * @time 19:06
*/
<template>
  <div class="topNav">
    <!--  logo和导航栏-->
    <div class="Nav">
      <el-tooltip class="item" effect="dark" content="返回首页" placement="top">
        <el-button class="logo" @click="toHome" style="z-index: 3" type="text" preventReClick>
          <div style="color: #ff4400;font-size: larger">易物</div>
          <div style="margin-top: 5px;color: #2b9376">校园二手交易平台</div>
        </el-button>

      </el-tooltip>
      <el-menu
          class="el-menu-demo"
          mode="horizontal"
          @select="handleSelect"
          background-color="white"
          active-text-color="#42b883"
          style="border: #f4f4f4 solid 1px"
      >
        <!-- 一级菜单 -->
        <el-submenu
            :index="item.classifyId+''"
            v-for="item in menuList"
            :key="item.classifyId"

        >
          <!-- 一级菜单的模板区域 -->
          <template slot="title">
            <!-- 文本 -->
            <span>{{ item.name }}</span>
          </template>
          <!-- 二级菜单 -->
          <el-menu-item
              :index="subItem.classifyId+''"
              v-for="subItem in item.children"
              :key="subItem.name"
              @click="refresh"
          >
            <template slot="title">
              <!-- 文本 -->
              <span>{{ subItem.name }}</span>
            </template>
          </el-menu-item>
        </el-submenu>
      </el-menu>
      <div class="search">
        <el-input
            style="width: 200px;margin:7px"
            prefix-icon="el-icon-search"
            v-model="searchInfo"
            placeholder="输入关键字进行搜索"
            clearable
            @keyup.enter.native="toSearch"
        ></el-input>
<!--        <el-button type="primary" icon="el-icon-search" @click="toSearch">搜索</el-button>-->
          <div class="person">
            <!--    已登录-->
            <el-menu
                default-active="1"
                mode="horizontal"
                @select="handleSelect"
                v-if="userName != null"
                router
                active-text-color="#2b9376"
            >
              <el-submenu style="font-size: x-large;" >
                <template slot="title">{{userName}}</template>
                <el-menu-item-group>
                  <el-menu-item :index="Calpath('center')">个人中心</el-menu-item>
                  <el-menu-item :index="Calpath('help')">帮助中心</el-menu-item>
                  <el-menu-item @click="exit">退出登录</el-menu-item>
                </el-menu-item-group>
              </el-submenu>

            </el-menu>
            <!--    未登录-->
            <div slot="title" v-else style="z-index: 3">
              <router-link :to="Calpath('login')">
                <el-divider direction="vertical"></el-divider>
                <el-button type="text" style="font-size: large">
                  未登录
                </el-button>
                <el-divider direction="vertical"></el-divider>
              </router-link>
              <router-link :to="Calpath('help')"><el-button type="text" style="font-size:large">帮助中心</el-button></router-link>
            </div>

          </div>
        </div>
    </div>
<!--  个人、客服、帮助入口-->
  </div>
</template>
<script>
import Utils from '../utools/utils'
import axios from "axios";
import {Decrypt,Encrypt,Calpath} from "@/utools/utils";
export default {
    name:'toNav',
    inject:['reload'],
    data(){
      return{
        userName:'',
        userId:13,
        menuList: [],
        searchInfo:'',
      }
    },
    methods:{
      //获取用户信息
      getUserInfo(){
          this.userName = window.sessionStorage.getItem('userName')
          this.userId=window.sessionStorage.getItem('userId');
          console.log('userName',this.userName)
      },
      //获取头部菜单
      async getMenuList(){
        await this.$axios.get(`${this.HOST}/released/classify/goods`).then((res) =>{
          console.log('/released/goods',res)
          this.menuList = res.data.data
          console.log('menulist',this.menuList)
        }).catch((res) =>{
          this.$message.error('商品分类请求失败！')
        })
        store.dispatch('connect');
      },
      //处理选项点击
      handleSelect(key, keyAndPath) {
        console.log('handleSelect',keyAndPath);
        Utils.$emit('test',keyAndPath)
        this.$router.push({path:Calpath('classification'),query:{info:keyAndPath}})
      },
      refresh(){
        // this.reload()
        // location.reload()
      },
    //  退出
      async exit(){
        // 弹框询问用户是否删除数据
        const confirmResult = await this.$confirm('确定退出吗？', '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).catch(error => error)
        // 如果用户点击确认则会返回字符串confirm
        // 如果用户点击取消则会返回字符串cancle
        // console.log(confirmResult)
        if (confirmResult == 'confirm'){
          this.$message.success('成功退出')
          window.sessionStorage.clear()
          // this.userName = null
          await this.$router.replace(Calpath('allgoods'))
        }
        this.getUserInfo()
      },
      //计算路径
      Calpath(path){
        return '/'+Encrypt(path)
      },
    //  去搜索页面
      toSearch(){
        if(this.searchInfo==''){
          return;
        }
        this.$router.push({
          path:Calpath('search'),
          query:{searchInfo:this.searchInfo}
        })

      },
      toHome(){
        this.$router.push(Calpath('allgoods'))
      }
    },
    created() {
      this.getMenuList()
      this.getUserInfo()
    },
    watch: {
      //监控用户的搜索
      searchInfo(val) {
        if(val == ''){
          this.$router.replace({path:Calpath('allgoods')})
        // }else{
        //   this.$router.push({
        //     path:Calpath('search'),
        //     query:{searchInfo:this.searchInfo}
        //   })
        }
      },

    }
}
</script>
<style scoped lang="less">
/*#nav{*/
/*  padding: 10px;*/
/*}*/
.topNav{
  padding: 0;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  //margin-bottom: 10px;
  padding-top: 10px;
  background-color: #ffffff;
}
// .person{

// }
.el-menu-demo{
  background-color: @green;
}
.Nav{
  margin-top: 10px;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}
.search{
  display: flex;
  flex-direction: row;
}
.el-divider--vertical {
  height: 2em;
  margin-left: 20px;
}
</style>
