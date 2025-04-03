<template>
  <div class="home">
    <el-container>
      <!--        顶部导航组件-->
      <el-header>
        <TopNav></TopNav>
      </el-header>

      <!--      主体商品展示-->
      <el-main>
        <!-- 路由嵌套 -->
        <router-view/>
      </el-main>
      <!--        下滑出现-->
      <el-backtop target=".home" :bottom="100" style="display: flex;flex-direction: column" visibility-height="0">
<!--        去到发布二手页面-->
          <div placement="left" content="发布二手" type="text"
                      style="{
                  height: 100%;
                  width: 100%;
                  background-color: white;
                  box-shadow: 0 0 6px rgba(0,0,0, .12);
                  text-align: center;
                  line-height: 40px;
                  color: #1989fa;
                  margin-bottom: 5px;}"
                      @click="toAdd"
          >
            <el-tooltip content="发布二手" placement="left" effect="dark"><i class="el-icon-edit" ></i></el-tooltip>
          </div>
        <!--        去到聊天页面-->
      <div placement="left" content="聊天" type="text" style="{
                  height: 100%;
                  width: 100%;
                  background-color: white;
                  box-shadow: 0 0 6px rgba(0,0,0, .12);
                  text-align: center;
                  line-height: 40px;
                  color: #1989fa;
                  margin-bottom: 5px;
              }" @click="toChat">
          <el-tooltip content="聊天" placement="left" effect="dark"><i class="el-icon-chat-dot-round" ></i></el-tooltip>
        </div>
<!--        回到顶部-->
          <div
              style="{
                  height: 100%;
                  width: 100%;
                  background-color: white;
                  box-shadow: 0 0 6px rgba(0,0,0, .12);
                  text-align: center;
                  line-height: 40px;
                  color: #1989fa;
              }">
            <el-tooltip content="回到顶部" placement="left" effect="dark"><i class="el-icon-caret-top"></i></el-tooltip>
        </div>

      </el-backtop>
      <!--      尾部-->
      <el-footer>
      <Tail></Tail>
      </el-footer>
    </el-container>
  </div>
</template>
<script>
import TopNav from "@/components/topNav";
import Tail from "@/components/tail"
import {Encrypt} from "@/utools/utils";
export default {
  data() {
    return {
      user: {
        uid: null,
        username: "未命名",
        avatar: "../assets/logo.png",
        money: null,
      },
      input: "",
    };
  },
  components: {
    TopNav,
    Tail
  },
  methods: {
    //处理下拉菜单的方法
    // handleCommand(command) {
    //   if (command == "toCenter") {
    //     //“更多信息”点击事件：跳转到个人中心页
    //     this.$router.push("/center");
    //   }
    //   if (command == "logout") {
    //     this.$store.commit("setToken", ""); //token设为空字符串
    //     this.$router.push("/");
    //     this.$message({
    //       message: "已退出登录",
    //       type: "success",
    //     });
    //   }
    // },
    //搜索
    search() {
      console.log(this.input);
      this.$router.push({
        path: "/search",
        query: {
          input: this.input,
        },
      });
    },

    toAdd() {
      // console.log('toadd')
      // if(!window.sessionStorage.getItem('userId')){
      //   this.$message.info('请先登录')
      //   this.$router.push(this.Calpath('login'))
      // }else{
        this.$router.push(this.Calpath(`addgoods`))
      // }
    },
    toChat(){
      // if(!window.sessionStorage.getItem('userId')){
      //   this.$message.info('请先登录')
      //   this.$router.push(this.Calpath('login'))
      // }else{
        this.$router.push(this.Calpath(`chat`))
      // }
    },
    //计算路径
    Calpath(path){
      return '/'+Encrypt(path)
    },
  },
  created() {
  },
  //   mounted: function() {
  //   // 页面开始加载时修改font-size
  //   var html = document.getElementsByTagName("html")[0];
  //   var oWidth = document.body.clientWidth || document.documentElement.clientWidth;
  //   // 这里的750是指设计图的大小,自己根据实际情况改变
  //   html.style.fontSize = oWidth / 1000 * 100 + "px";
  //   console.log('rem:', html.style.fontSize);
  // }
};
</script>
<style scoped lang="less">
body{
  overflow: hidden;

}
html{
  overflow: auto;
}
.el-header {
  display: flex;
  padding: 0;
  flex-direction: column;
  justify-content: space-around;
}

.el-input > .el-input__inner {
  background-color: rgba(255, 255, 255, 0.4);
  border-radius: 15em;
}

el-input {
  outline: none;
}

.link {
  display: flex;
  justify-content: center;
  font-size: small;
}

.link div {
  cursor: pointer;
}

.link div:hover {
  color: #f1c40f;
}

.home {
  height: 100vh;
  overflow-x: hidden;
  background-color: #f4f4f4;
  //background-color: rgba(43, 145, 116,0.1);
}
</style>
