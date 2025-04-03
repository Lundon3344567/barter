<template>
    <div class="lostPwd">
       <h1>忘记密码</h1>

      <el-card
          shadow="always"
          v-loading="loading"
          element-loading-text="拼命加载中"
          element-loading-spinner="el-icon-loading"
          element-loading-background="rgba(0,0,0,0.8)">


        <el-form
            label-width="120px"
            ref="pwdForm"
            :model="pwdForm"
            :rules="rules"
            status-icon
            v-if="active==0"

        >
          <el-form-item prop="email" label-width="100px" style="display: flex;align-items: self-start;">
            <el-input
                v-model="pwdForm.email"
                placeholder="请输入邮箱"
                prefix-icon="el-icon-message"
                @blur="emailUseful"
            >
            </el-input>
          </el-form-item>
          <el-form-item label-width="100px"  prop="verification">
            <el-input placeholder="请输入验证码"
                      v-model="pwdForm.verification"
            ></el-input>

            <el-button
                type="primary"
                @click="certify"
                :disabled="cannotClick"
                size="mini"
                style="font-size: small;"
            >{{ content }}
            </el-button>
          </el-form-item>

          <el-form-item  label="密码" prop="password">
            <el-input  type="password" v-model="pwdForm.password" autocomplete="off" show-password></el-input>
          </el-form-item>
          <el-form-item  label="确认密码" prop="password_confirm">
            <el-input type="password" v-model="pwdForm.password_confirm" autocomplete="off" show-password></el-input>
          </el-form-item>

          <el-button
              type="primary"
              @click="submitForm()"
          >确认
          </el-button>
          <el-button @click="pwdReset()">重置</el-button>
          <router-link :to="Calpath('login')" style="float: right">
            <el-button type="danger" >去登录</el-button>
          </router-link>
        </el-form>
<!--        更新成功前往登录-->
        <el-form v-if="active==1">
          <el-form-item>
            <h3>恭喜你，新登录密码设置成功
              <router-link :to="Calpath('login')" style="float: right">
              <el-button type="success" >去登录</el-button>
            </router-link></h3>

          </el-form-item>
        </el-form>
      </el-card>
    </div>
</template>
<script>
import axios from "axios";
import {Encrypt} from "@/utools/utils";
import testRules from '../../utools/testRules.json'
export default {
  data() {
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
    let validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.pwdForm.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    let checkEmail = (rule, value, callback) => {
      let regEmail = eval(testRules.email.testRule);
      if(this.pwdForm.email== ""){
        return callback(new Error(testRules.email.messageEmpty))
      }
      if (!regEmail.test(this.pwdForm.email)) {
        return callback(new Error(testRules.email.messageError)); // 有一个if 就要 一个 callback
      }
      return callback(); // 返回一个回调函数给验证，是表单validate生效
    };
    return {
      loading:false,
      active: 0,
      emailUse:true,
      pwdForm: {
        email:'',
        password: '1234',
        password_confirm: '1234',
        verification:'',
      },
      imageUrl:'',
      rules: {
        email:[
          {validator:checkEmail,trigger:'blur',required:true}
        ],
        password: [
          {validator: validatePass, trigger: 'blur',required:true}
        ],
        password_confirm: [
          {validator: validatePass2,trigger: 'blur',required:true}
        ],
        verification: [
          {required: true, message: "请输入邮箱验证码", trigger: "blur"},
        ],
        code: [
          {required: true, message: "请输入图形验证码", trigger: "blur"},
        ]

      },
      content: '发送验证邮件',
      cannotClick: true,
      totalTime: 30,
      message: '将发送一封验证邮件到账号绑定邮箱，此操作会更改账号密码'
    }
  },
  methods: {
    //失去焦点时判断邮箱是否可用，可用则可以获取验证码
     async emailUseful() {
       if(this.pwdForm.email!= ""){
         await this.$axios.get(`${this.HOST}/email`, {
           params: {
             email: this.pwdForm.email
           }
         }).then((res) => {
           console.log('emailUseful', res);
           if (res.data.code == 400) {
             this.cannotClick = false
           } else {
             this.$message.error('该邮箱未被注册')
             this.emailUse = false
             this.cannotClick = true
           }
         }).catch((res) => {
           this.$message.error('请求失败')
         })
       }

     },
    //按钮点击计时器
    countDown() {
      if (this.cannotClick) return //改动的是这两行代码
      this.cannotClick = true
      this.content = this.totalTime + 's后重新发送'
      let clock = window.setInterval(() => {
        this.totalTime--
        this.content = this.totalTime + 's后重新发送'
        this.cannotClick = true
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
    //发送重置邮箱验证码
    async certify() {
      this.countDown()
      await this.$axios.get(`${this.HOST}/email/forget/massage`,{
        params:{
          email:this.pwdForm.email
        }
      }).then((res)=>{
        console.log('certifyRes',res)
        if(res.data.code==200){
          this.$message.success('重置密码邮箱验证码发送成功')
        }
      }).catch((res)=>{
        console.log('certifyError',res)
      })
    },
    submitForm() {
       this.$refs.pwdForm.validate(async valid => {
        if (valid) {
          await this.$axios.post(`${this.HOST}/email/password`,{
            email:this.pwdForm.email,
            password:Encrypt(this.pwdForm.password),
            code:this.pwdForm.verification
          }).then((res)=>{
            console.log('submitFormRes',res)
            if(res.data.code==200){
              this.$message.success('密码修改成功，请点击上方按钮登录')
              this.active = 1
            }else{
              this.$message.info('验证失败，请确认信息填写无误')
            }
          }).catch((res)=>{
            console.log('submitFormError',res)
            this.$message.error('修改密码请求失败')
          })
        }
      })
      // }
    },
    pwdReset(){
      this.$refs.pwdForm.resetFields()
    },
    //计算路径
    Calpath(path){
      return '/'+Encrypt(path)
    }
  },
  components: {

  },
  created() {

  }
}
</script>
<style scoped>
.lostPwd{
  height: 800px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.el-card{
  height: 600px;
  display: flex;
  width: 700px;
  justify-content: center;
  align-items: center;

}
.el-input {
  width: 180px;
  padding-right: 40px;
}

</style>
