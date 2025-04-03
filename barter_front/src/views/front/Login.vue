<template>
  <div class="login">
    <div class="index">
      <div class="picture hidden-sm-and-down">
        <img src="../../assets/imgs/logo.png"/>
      </div>
      <div class="menu">
        <div class="title-small hidden-md-and-up">校园二手交易平台</div>
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span style="margin-left: 20px;font-size: large">登录</span>
<!--            跳转到注册-->
            <router-link :to="Calpath('register')" >
              <el-button
                style="float: right; padding: 3px 0;margin-right: 30px"
                type="text"
            >注册
            </el-button></router-link>
          </div>
          <!-- 登录面板 -->
          <el-form
              :model="user"
              label-width="120px"
              ref="user"
              :rules="loginFormRules"
          >
            <el-form-item label="用户名" prop="username" size="medium">
              <el-input
                  v-model="user.username"
                  placeholder="请输入用户名"
                  size="medium"
                  clearable
              ></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input
                  v-model="user.password"
                  placeholder="请输入密码"
                  type="password"
                  show-password
                  clearable
              ></el-input>
            </el-form-item>
            <!-- 验证码功能-->
            <el-form-item label="验证码" prop="verification">
              <el-input
                  v-model="user.verification"
                  placeholder="请输入验证码"
              ></el-input>

            </el-form-item>
            <el-form-item style="display: flex;justify-content: space-around">
              <el-image
                  :src="imageUrl"
                  style="width: 110px;height: 60px;padding-right: 40px"
                  fit="scale-down"
                  @click="getVetityPhoto"
              ></el-image>
              <el-button type="text" style="color: blue;text-underline: blue">看不清？请点击图片</el-button>
            </el-form-item>
            <div class="option" align="center">
              <el-button type="primary" style="width: 120px;margin-left: 40px" @click="login()"
              >登录
              </el-button
              >
<!--              跳转到忘记密码页面-->
              <router-link :to="Calpath('lostpwd')" style="margin-left: 35px">
                <el-button type="text">忘记密码</el-button>
              </router-link>
            </div>
          </el-form>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
import {Decrypt,Encrypt,Calpath} from "@/utools/utils";
import axios from "axios";
import qs from 'qs'
export default {
  data() {
    return {
      user: {
        username: "",
        password: "",
        verification: "",//验证码功能，待开启
      },
      loginFormRules: {
        // tigger表示鼠标移出时验证，通过prop绑定
        // 用户名是否合法
        username: [
          {required: true, message: "请输入用户名", trigger: "blur"},
          {min: 3, max: 10, message: "长度在 3 到 10 个字符", trigger: "blur"},
        ],
        // 密码是否合法
        password: [
          {required: true, message: "请输入密码", trigger: "blur"},
          // {min: 5, max: 15, message: "长度在 5 到 15 个字符", trigger: "blur"},
        ],
      //  图片验证码
        verification:[
          {required: true, message: "请输入图形验证码", trigger: "blur"},
        ]
      },
      //  验证码图片
      imageUrl: ''
    };
  },
  methods: {
    //登录功能
    login() {
      console.log('verification',this.user.verification)

      this.$refs.user.validate(async (valid) => {
        if (!valid) return;
        else {
          // console.log('原密码：',this.user.password)
          // this.password = this.$md5(toString(this.user.password) + 'test').toUpperCase()
          this.user.password = Encrypt(this.user.password)
          // console.log("加密后的密码是：" ,this.user.password)
          // this.user.password = Decrypt(this.user.password)
          // console.log('解密后的密码：',this.user.password)
          let data = new FormData();
          data.append('username',this.user.username);
          data.append('password',this.user.password);
          data.append('code',this.user.verification);
          await this.$axios.post(`${this.HOST}/login`, qs.stringify({
              username:this.user.username,
              password:this.user.password,
              code:this.user.verification
          }),{
            headers:{
              'content-type': 'application/x-www-form-urlencoded; charset=UTF-8'
            },
            crossDomain:true,
            // transformRequest: [function(data) {
            //   data = qs.stringify(data)
            //   return data
            // }],
          }).then( res =>{
              console.log('Pwd',res)
              if(res.data.code === 200){
                console.log('loginRes',res.data)
                //普通用户
                if(res.data.data.userState==1){
                  window.sessionStorage.setItem('token',res.headers.authorization);
                  window.sessionStorage.setItem('userId',(res.data.data.userId));
                  window.sessionStorage.setItem('userName',res.data.data.username);
                  window.sessionStorage.setItem('isNewer',1)
                  let goodId = window.sessionStorage.getItem('goodId')
                  if(goodId==null){
                    this.$router.replace(this.Calpath('allgoods'));
                    // this.$router.replace(this.Calpath('AdminHome'));
                    this.$message({
                      type: "success",
                      message: "欢迎来到校园二手交易平台",
                    });
                  }else{
                    this.$router.push({
                      path: Calpath("goodsdetail"),
                      query: { goodId: goodId },
                    });
                  }
                }
                //管理员
                if(res.data.data.userState==101){
                  window.sessionStorage.setItem('token',res.headers.authorization);
                  window.sessionStorage.setItem('userId',res.data.data.userId);
                  window.sessionStorage.setItem('userName',res.data.data.username);
                  this.$router.replace(this.Calpath('AdminHome'));
                  // this.$router.replace(this.Calpath('allgoods'));
                  this.$message({
                    type: "success",
                    message: "欢迎来到易物后台管理",
                  });
                }
                if(res.data.data.userState==2||res.data.data.userState==102){
                  this.$message.error('用户被锁，请联系相关人员！')
                }
                if(res.data.data.userState==3||res.data.data.userState==103){
                  this.$message.error('用户已注销！')
                }
              }
              else if(res.data.message=='验证码错误,请刷新验证码'){
                this.$message.info('验证码错误')
                this.$refs.user.resetFields()
                this.getVetityPhoto()
              }else{
                this.$message.info('账号密码错误或者账户被锁定')
                this.$refs.user.resetFields()
                this.getVetityPhoto()
              }
            }).catch(res =>{
              return this.$message.error('请求失败！')
            })

        }
      })
    },
    //获取验证码图片
    async getVetityPhoto(){
      await axios({
        methods: 'get',
        url:`${this.HOST}/captcha`,
        responseType:'blob',
      }).then( res =>{
        console.log('loginRes',res)
        let blob = new Blob([res.data],{type:'image/jpeg'})
        this.imageUrl = window.URL.createObjectURL(blob)
      }).catch( res =>{
        return this.$message.error('验证码图片获取失败!')
      })
    },
    //计算路径
      Calpath(path){
        return '/'+Encrypt(path)
    }
  },
  created() {
    //   console.log("登录页token值：", localStorage.getItem("token"));
    this.getVetityPhoto();
  },
}
</script>

<style scoped>
.login {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.index {
  width: 1000px;
  height: 500px;
  display: flex;
  justify-content: center;
}

.picture {
  width: calc(100% - 400px);
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.title {
  text-align: center;
  font-size: 36px;
  user-select: none;
  letter-spacing: 2px;
}

.title-small {
  text-align: center;
  font-size: 30px;
  user-select: none;
  letter-spacing: 2px;
  margin: 10px 0;
}

.picture img {
  height: 80%;
}

.menu {
  width:600px;
  height: 560px;
  /*background-color: #29ab91;*/
  display: flex;
  flex-direction: column;
  justify-content: center;
}

/* 卡片 */
.el-card {
  height: 600px;
  width: 600px;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}

.box-card {
  width: 550px;
  height: 550px;
}

/* ---结束卡片---- */
.option {
  margin-left: 80px;
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  font-size: small;

}

</style>
