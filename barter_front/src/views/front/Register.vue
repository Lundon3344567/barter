<template>
  <div class="register">
    <!--    头部区域-->
    <div class="registerNav">
      <p style="font-size: large">
        <span style="color: red">易物</span>
        <br>
        <span>校园二手交易平台</span>
      </p>
      <p style="font-size: large">
        <span>已有账号，</span>
        <router-link :to="Calpath('login')">
          <el-button type="text" style="color: red;font-size: large">立即登录</el-button>
        </router-link>
      </p>
    </div>
    <!-- 注册面板 -->
    <el-card>
      <div style="margin-bottom: 50px;font-size: x-large;">{{ isVertify == 1 ? '邮箱验证' : '设置密码' }}</div>
      <!--      邮箱认证区域-->
      <el-form
          v-if="isVertify==1"
          label-width="120px"
          ref="emailRef"
          :model="newUser"
          :rules="registerFormRules"
      >
        <el-form-item prop="email" label-width="100px" style="margin-left: 10px">
          <el-input
              v-model="newUser.email"
              placeholder="请输入邮箱"
              prefix-icon="el-icon-message"
              @blur="emailUseful"
          >
          </el-input>
        </el-form-item>

        <el-form-item  style="padding-top: 20px;" prop="verification">
          <el-input placeholder="请输入验证码"
                    v-model="newUser.verification"
                    @change="change"
                    @input="change"
          ></el-input>
        </el-form-item>
        <el-button
            type="primary"
            @click="certify"
            :disabled="cannotClick"
            size="mini"
            style="font-size: small;margin-top: 10px;margin-left: 50px"
        >{{ content }}
        </el-button>
        <el-form-item style="text-align: center;margin-top: 60px">
            <el-button @click="toNext" type="primary" >下一步</el-button>
            <el-button @click="certifyReset" style="margin-left: 100px">重置</el-button>
        </el-form-item>
      </el-form>
      <!--        设置用户名、密码-->
      <el-form
          v-if="isVertify==2"
          :model="newUser"
          label-width="100px"
          ref="newUserRef"
          :rules="registerFormRules"
          status-icon
      >
<!--        用户名-->
        <el-form-item label="用户名" prop="username">
          <el-input
              v-model="newUser.username"
              placeholder="请输入用户名"
              type="text"
              @blur="userNameUserFul"
          >
          </el-input>
        </el-form-item>
<!--        密码-->
        <el-form-item label="密码" prop="password">
          <el-input
              v-model="newUser.password"
              placeholder="请输入密码"
              type="password"
              show-password
          >
          </el-input>
        </el-form-item>
<!--        确认密码-->
        <el-form-item label="确认密码" prop="confirmPwd">
          <el-input
              v-model="newUser.confirmPwd"
              placeholder="再次输入密码"
              type="password"
              show-password
          ></el-input>
        </el-form-item>
<!--        验证码功能-->
        <!-- 验证码输入框-->
        <el-form-item label="验证码" prop="code">
          <el-input
              v-model="newUser.code"
              placeholder="请输入验证码"
          ></el-input>

        </el-form-item>
<!--        验证码图片-->
        <el-form-item style="display: flex;justify-content: space-around">
          <el-image
              :src="imageUrl"
              style="width: 110px;height: 60px;padding-right: 40px"
              fit="contain"
              @click="getVetityPhoto"
          ></el-image>
          <el-button type="text" style="color: blue;text-underline: blue">看不清？请点击图片</el-button>
        </el-form-item>
        <div>
          <el-button type="primary" style="width: 120px" @click="register"
          >注册
          </el-button>
          <el-button type="info" style="width: 120px" @click="resetRegister"
          >重置
          </el-button>
        </div>
      </el-form>
      <!--        跳转至登录页面-->
      <el-form v-if="isVertify==3">
        <el-form-item>
          <h3>恭喜你，注册成功</h3>
          <h2>点击下方按钮登录</h2>
          <router-link :to="Calpath('login')">
            <el-button type="primary">立即登录</el-button>
          </router-link>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
<script>
import axios from "axios";
import {Encrypt,Calpath} from "@/utools/utils";
import testRules from "@/utools/testRules.json"
export default {
  data() {
    let checkEmail = (rule, value, callback) => {
      let regEmail = eval(testRules.email.testRule)
      if(value==''){
        callback(new Error(testRules.email.messageEmpty))
      }
      if (!regEmail.test(value)) {
        return callback(new Error(testRules.email.messageError)); // 有一个if 就要 一个 callback
      }
      return callback(); // 返回一个回调函数给验证，是表单validate生效
    };
    let validatePass = (rule, value, callback) => {
      let regPas  = eval(testRules.password.testRule)
      if (value === '') {
        callback(new Error(testRules.password.messageEmpty))
      }
      if(!regPas.test(value)){
        callback(new Error(testRules.password.messageError))
      }
      callback()
    }
    var validatePass2 = (rule, value, callback) => {
      if(value == ''){
        callback(new Error('请再次输入密码'))
      }
      if (value != this.newUser.password) {
        callback(new Error('两次输入密码不一致!'));
      }
        callback();
    };
    let checkUserName = (rule, value, callback) => {
      this.userNameUserFul()
      let regUserName = eval(testRules.userName.testRule)
      if(value==''){
        callback(new Error(testRules.userName.messageEmpty))
      }
      if (!regUserName.test(value)) {
        return callback(new Error(testRules.userName.messageError)); // 有一个if 就要 一个 callback
      }
      return callback(); // 返回一个回调函数给验证，是表单validate生效
    };
    return {
      isVertify: 1,
      isRegiste:false,
      certification: '',
      content: '点击发送验证邮件',
      newUser: {
        username: '',
        email: "",
        password: "",
        confirmPwd: "",
        verification:'',
        code:''
      },
      imageUrl:'',
      registerFormRules: {
        username: [
          {validator:checkUserName,trigger:'blur',required:true}
        ],
        email: [
          {validator: checkEmail, trigger: 'blur'},
        ],
        password: [
          {validator:validatePass, trigger: 'blur',required: true},
        ],
        confirmPwd: [
          {validator: validatePass2, trigger: 'blur',required:true},
        ],
        verification: [
          {required: true, message: "请输入邮箱验证码", trigger: "blur"},
        ]
      },
      cannotClick: true,
      totalTime: 30,
      message: '将发送一封验证邮件到账号绑定邮箱，此操作会更改账号密码'
    }
  },
  methods: {
    //注册功能
    register() {
      console.log('newUserRef', this.$refs.newUserRef)
      // this.newUser.username = Encrypt(this.newUser.username)
      this.newUser.password=Encrypt(this.newUser.password)
      this.newUser.confirmPwd = Encrypt(this.newUser.confirmPwd)
      this.$refs.newUserRef.validate((valid) => {
        if (valid) {
          console.log(this.newUser)
          this.$axios.post(`${this.HOST}/user/register`, {
            email:this.newUser.email,
            username:this.newUser.username,
            password:this.newUser.password,
            code:this.newUser.code
          }).then((res) => {
            if (res.data.code == 200) {
              //后端返回状态码200意为成功
              this.$message({
                message: "注册成功",
                type: "success",
              });
              this.isVertify = 3
              console.log('registerRes',res)
            } else {
              // 注册失败
              console.log('registerError',res)
              this.$message.error("注册有误")
            }
          }).catch((res)=>{
            this.$message.error('请求失败！')
          })
        }
      })
    },
    //重置
    resetRegister() {
      this.$refs.newUserRef.resetFields()
    },

    //  邮箱验证
    async toNext() {
      console.log('verification',this.newUser.verification)
      await this.$refs.emailRef.validate(async (valid) => {
        if (!valid) return;
        else {
          await this.$axios.post(`${this.HOST}/email`, {
            "email": this.newUser.email,
            "code": this.newUser.verification
          }, {
            headers: {
              'Content-Type': 'application/json'
            }
          }).then((res) => {
            console.log('email', res)
            if (res.data.code === 200) {
              this.$message.success('邮箱验证码成功！')
              this.isVertify = 2
            } else {
              this.$message.error('邮箱验证失败！')
            }
          }).catch((res) => {
            this.$message.error('请求失败！')
          })
        }
      })

    },
    //失去焦点时判断邮箱是否可用，可用则可以获取验证码
    async emailUseful() {
          if(!this.newUser.email){
            this.cannotClick = true
            return ;
          }
          this.$axios.get(`${this.HOST}/email`,{
            params:{
              email:this.newUser.email
            }
          }).then( (res) =>{
            console.log('emailUseful',res);
            if(res.data.code==400){
              this.$message.error('该账户已经被注册')
              this.cannotClick = true
              return ;
            }else{
              this.cannotClick = false
              return ;
            }
            this.cannotClick = true
          }).catch( (res) =>{
            this.$message.error('请求失败')
          })
    },
    async userNameUserFul(){
      if(!this.newUser.username) {
        this.cannotClick = true
        return;
      }
      await this.$axios.get(`${this.HOST}/user`,{
        params:{
          username:this.newUser.username
        }
      }).then( (res) =>{
        console.log('usernameUseful',res)
        if(res.data.code===400){
          this.$message.error('用户名已经存在')
          this.newUser.username=''
        }
      }).catch((res) =>{
        this.$message.error('请求失败！')
      })
    },
    //按钮点击计时器
    countDown() {
      if (this.cannotClick) return //改动的是这两行代码
      this.cannotClick = true
      this.content = this.totalTime + 's后重新发送'
      let clock = window.setInterval(() => {
        this.totalTime--
        this.content = this.totalTime + 's后重新发送'
        if (this.totalTime < 0) {
          window.clearInterval(clock)
          this.content = '重新发送验证邮件'
          this.message =
              '将发送一封验证邮件到账号绑定邮箱，此操作会更改账号密码'
          this.totalTime = 30
          this.cannotClick = false //这里重新开启
        }
      }, 1000)
    },
    async certify() {
      if(this.newUser.email==''){
        this.$message.error('请输入邮箱')
        this.cannotClick = true
        return;
      }
      this.countDown()
      await this.$axios.get(`${this.HOST}/email/register/massage`,{
        params:{
          email:this.newUser.email
        }
      }).then( (res) =>{
        console.log(res)
      }).catch((res) =>{
        this.$message.error('请求失败！')
      })
    },
    //el-input强制刷新
    change() {
      this.$forceUpdate();
    },
    certifyReset() {
      this.$refs.emailRef.resetFields()
    },
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
    this.getVetityPhoto()
  }
}
</script>
<style scoped lang="less">
#nav {
  padding: 0px;
}

.el-header {
  padding: 0;
}

.register {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  font-size: medium;

}

.registerNav {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  width: 100%;
  background-color: @backgroundColorGrey;
}

.el-card {
  width: 570px;
  height:550px;
  /*margin-top: 100px;*/
}

.el-input {
  width: 180px;
  padding-right: 40px;
}

</style>
