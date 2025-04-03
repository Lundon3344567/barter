<template>
  <div class="center">
    <TopNav
    ></TopNav>
    <!--      个人主体区域-->
    <el-container class="container"
                  >
      <!--        左侧侧边栏-->
      <el-aside style="width: 200px">
        <div
            style="
            text-align: center;
            font-weight: bold;
            margin-bottom: 20px;
          "
        >账号管理</div>
        <el-menu
            text-color="black"
            active-text-color="orange"
            :unique-opened="true"
            :router="true"
            :default-active="Calpath(activePath)"
        >
          <el-menu-item
              v-for="item in menuList"
              @click="saveNavState(item.path)"
              :index=Calpath(item.path)
              align="center"
              :key="item.id"
          >
            <template slot="title">
              <!-- 图标 -->
              <i :class="'iconfont '+icons[item.id]"></i>
              <!-- 文本 -->
              <span style="width: 100px">{{ item.name }}</span>
            </template>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <!-- 面包屑导航区域 -->
        <el-breadcrumb
            separator-class="el-icon-arrow-right"
        >
          <el-breadcrumb-item :to="Calpath('allgoods')">首页</el-breadcrumb-item>
          <el-breadcrumb-item>会员中心</el-breadcrumb-item>
          <el-breadcrumb-item>个人中心</el-breadcrumb-item>
        </el-breadcrumb>
        <el-card>
          <router-view></router-view>
        </el-card>
      </el-main>
    </el-container>

    <el-footer>
      <Tail></Tail>
    </el-footer>
  </div>
</template>
<script>
import {Encrypt} from "@/utools/utils";
import TopNav from "@/components/topNav";
import Tail from "@/components/tail"
import Driver from "driver.js" // import driver.js
import 'driver.js/dist/driver.min.css'
export default {
  data(){
    return{
      icons:{
        "3": "barter-gouwucheman1",
        "4": "barter-gerenzhongxin",
        "5": "barter-xiaoxitongzhi",
        "6": "barter-zhanghaoxinxi",
        "7": "barter-zhanghao-anquan",
        "8":"barter-shouye",
      },
      menuList:[
        {name:'购物车',path:'shopcart',id:3,intro:'查看加入购物车的商品信息'},
        {name:'我的订单',path: 'myorder',id:4,intro:'按照不同类型查看订单'},
        {name:'个人商品',path: 'person',id:5,intro:'查看发布、售出等不同状态的商品'},
        {name:'消息通知',path: 'alerts',id:6,intro:'查看系统通知消息'},
        {name:'账号信息',path: 'accountinfo',id:7,intro:'修改个人信息'},
        {name: '账号安全',path: 'accountsecurity',id:8,intro:'修复手机号码、密码、邮箱'}
      ],
      activePath:'',
      autostart:false,
      steps : [
        {
          element: ".el-breadcrumb",
          popover: {
            title: "导航栏",
            description: "可以快速回到首页",
            position: "bottom"
          }
        },
        {
          element: ".el-aside",
          popover: {
            title: "侧边栏",
            description: "可进行功能选择",
            position: "right"
          }
        },
      ],
      driver: null,
      isNewer:true
    }
  },
  methods:{
    //计算路径
    Calpath(path){
      return '/'+Encrypt(path)
    },
    //保存链接的激活状态
    saveNavState(activePath){
      window.sessionStorage.setItem('activePath', activePath);
    },
    guide () {
      this.driver.defineSteps(this.steps)
      this.driver.start()
      this.isNewer=0
      window.sessionStorage.setItem('isNewer',0)
    },
  },
  components:{
    TopNav,
    Tail
  },
  created() {
    this.activePath = window.sessionStorage.getItem('activePath')
    this.isNewer = window.sessionStorage.getItem('isNewer')

  },
  mounted() {
    this.driver = new Driver({
      className: "scoped-class", // className to wrap driver.js popover
      animate: true, // Animate while changing highlighted element
      opacity: 0.75, // Background opacity (0 means only popovers and without overlay)
      padding: 10, // Distance of element from around the edges
      allowClose: true, // Whether clicking on overlay should close or not
      overlayClickNext: false, // Should it move to next step on overlay click
      doneBtnText: "完成", // Text on the final button
      closeBtnText: "关闭", // Text on the close button for this step
      nextBtnText: "下一步", // Next button text for this step
      prevBtnText: "上一步" // Previous button text for this step
      // Called when moving to next step on any step
    })
    if(this.isNewer==1){
      this.guide()
    }
    // this.driver = new Driver()
  }
};
</script>
<style lang="less" scoped>
.container {
  padding-top: 10px;
}
.el-card {

}
.el-main{
  padding: 0;
}
.iconfont{
  font-size: 20px;
  margin-right: 5px;
}
</style>
