<template>
  <el-container class="home_container">
    <!-- 头部区域 -->
    <el-header>
      <div>
        <img src="../../assets/imgs/barter.png" alt="" />
        <span>{{userName}}</span>
      </div>
      <el-button type="warning" class="exitButton"  @click="exit">退出</el-button>
    </el-header>
    <!-- 页面主体区域 -->
    <el-container>
      <!-- 侧边栏 -->
      <el-aside :width="isCollapse ? '64px' : '200px'">
        <div class="toggle_button" @click="toggleCollapse">|||</div>
        <!-- 侧边栏菜单区域 -->
        <el-menu
          active-text-color="#ffa200"
          :unique-opened="true"
          :collapse="isCollapse"
          :collapse-transition="false"
          :router="true"
          :default-active="activePath"
        >
          <!-- 一级菜单 -->
          <el-submenu
            :index="item.id + ' '"
            v-for="item in menuList"
            :key="item.id"
          >
            <!-- 一级菜单的模板区域 -->
            <template slot="title">
              <!-- 图标 -->
              <i :class="item.icon"></i>
              <!-- 文本 -->
              <span>{{ item.title }}</span>
            </template>
            <!-- 二级菜单 -->
            <el-menu-item
              v-for="subItem in item.children"
              :key="subItem.id"
              @click="saveNavState(subItem.path)"
              :index=Calpath(subItem.path)
            >
              <template slot="title">
                <!-- 图标 -->
                <i :class="subItem.icon"></i>
                <!-- 文本 -->
                <span style="width: 100px">{{ subItem.title }}</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <!-- 右侧内容主体 -->
      <el-main>
          <!-- 路由占位符 -->
          <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
import {Encrypt,Calpath} from "@/utools/utils";
import {hasPermission} from "@/views/back/index";
export default {
  data() {
    return {
      //用户信息
      userName:'',
      //   左侧菜单数据
      menuList: [
        {
          authName:'用户管理',
          id:1,
          path:'users',
          children:[
            {authName:'用户列表',id:11,path:'users'}
          ]
        },
        {
          authName: '权限管理',
          id:2,
          path:'rights',
          children: [
            {authName: '角色列表',id:21,path:'roles'},
            {authName: '权限列表',id:22,path:'rights'}
          ]
        },
        {
          authName: '商品管理',
          id:3,
          path:'goods',
          children: [
            {authName: '商品列表',id:31,path:'goods'},
            // {authName: '分类参数',id:32,path: 'params'},
            {authName: '商品分类',id:33,path: 'cate'}
          ]
        },
        {
          id:4,
          authName: '广告管理',
          path: 'ads',
          children: [
            {id:41,authName: '广告列表',path: 'ads'}
          ]
        },
        {
          id:6,
          authName: '订单管理',
          path:'order',
          children: [
            {id:61,authName: '订单列表',path: 'order'}
          ]
        },
        {
          id:5,
          authName: '日志管理',
          path: 'logs',
          children: [
            {id:51,authName: '日志列表',path: 'logs'},
            {id:52,authName: '异常日志',path: 'abnormalog'}
          ]
        },

      ],
      //是否折叠
      isCollapse: false,
      //被激活链接地址
      activePath:'',
      perms:[],

    };
  },
  created() {
    this.getMeanuList();
    this.getUserInfo();
    this.activePath = window.sessionStorage.getItem('active')
  },
  methods: {
     getUserInfo(){
       this.userName = window.sessionStorage.getItem('userName')
     },
     async exit() {
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
        this.$router.replace('/'+Encrypt('allgoods'))
      }
      this.getUserInfo()
    },
    // 获取所有的菜单
    async getMeanuList() {
      let userName = window.sessionStorage.getItem('userName')
      await this.$axios.get(`${this.HOST}/admin/menu/`,{
        params:{
          "name":userName
        }
      }).then((res)=>{
        console.log('getMeanuListRes',res)
        this.menuList = res.data.data.nav
        this.perms = res.data.data.authoritys
        this.$store.dispatch('SET_PERMISSION',this.perms)
      }).catch((res)=>{
        console.log('getMeanuListError',res)
      })
    },
    //点击按钮，切换菜单的折叠与展开
    toggleCollapse() {
        this.isCollapse = !this.isCollapse
    },
    //保存链接的激活状态
    saveNavState(activePath){
       console.log('acyivePatg',activePath)
        window.sessionStorage.setItem('active', '/'+Encrypt(activePath));

    },
    //计算路径
    Calpath(path){
      return '/'+Encrypt(path)
    },
    hasPerms: function (perms) {
      // 根据权限标识和外部指示状态进行权限判断
      return hasPermission(perms)
    },
  },

};
</script>
<style lang="less" scoped>

.home_container{
  width: 100%;
}
.el-container{
  width: 100%;
  height: 100%;
}
.el-header {
  background-color: @blue;
  color: @fontColorWhite;
  text-align: center;
  line-height: 60px;
  display: flex;
  justify-content: space-between;
  padding-left: 0;
  align-items: self-start;
  font-size: @font-size;
  img {
    width: 50px;
    height: 50px;
  }
  > div {
    display: flex;
    align-items: center;
    span {
      margin-left: 15px;
    }
  }

}
.exitButton{
  margin-top: 5px;
}
.el-aside {
  //background-color: ;
  color: @fontColorWhite;
  min-height: 700px;
  height: 100%;
  //height:expression(this.parentNode.offsetHeight + "px");

  .el-menu {
    border: none;
    //background-color:@backgroundColorBlack;
    text-color:@fontColorWhite
  }

}

.el-main {
  background-color: @backgroundColorDeep;
  color: @fontColorBlack;
  // line-height: 160px;
}
.home_container {
  height: 100%;

}
.el-icon {
  margin-right: 10px;
}

.toggle_button {
  background-color: @blue;
  font-size: @font-size;
  line-height: 24px;
  // width: 100%;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer;
  color: @fontColorWhite;
  border: 2px @green solid;
}


</style>
