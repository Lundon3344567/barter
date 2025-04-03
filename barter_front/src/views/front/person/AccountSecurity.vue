<template>
  <div class="security">
<!--    展示基本信息，flag=0-->
    <el-row v-if="flag==0">
      <div style="margin-bottom: 20px">您当前的账号是 {{userInfo.username}}</div>
<!--      已经绑定了手机号码-->
      <el-col v-if="userInfo.userPhone">
        <i class="el-icon-success" style="color: #29ab91"></i>
        <span style="margin-left: 10px">绑定手机号</span>
        <span style="margin-left: 80px;color: #999999">绑定的手机：{{userInfo.userPhone|phoneFilter}}，若手机丢失或停用，请及时更换</span>
        <el-button type="text" style="margin-left: 100px" @click="flag=1">更换手机号</el-button>
        <el-divider></el-divider>
      </el-col>
<!--      未绑定手机号码-->
      <el-col v-else>
        <i class="el-icon-error" style="color: #fd574c"></i>
        <span style="margin-left: 10px">绑定手机号</span>
        <span style="margin-left: 80px;color: #999999">为了正常进行交易，请绑定手机号</span>
        <el-button type="text" style="margin-left: 280px" @click="flag=4">绑定手机号</el-button>
        <el-divider></el-divider>
      </el-col>
<!--修改邮箱-->
      <el-col>
        <i class="el-icon-success" style="color: #29ab91"></i>
        <span style="margin-left: 10px">绑定邮箱</span>
        <span style="margin-left: 95px;color: #999999">绑定的邮箱：{{userInfo.email}}，若邮箱无法使用，请及时更换</span>
        <el-button type="text" style="margin-left: 60px" @click="flag=3">修改邮箱</el-button>
        <el-divider></el-divider>
      </el-col>
<!--修改密码-->
      <el-col>
        <i class="el-icon-success" style="color: #29ab91"></i>
        <span style="margin-left: 10px">登录密码</span>
        <span style="margin-left: 95px;color: #999999">建议您定期更换密码，可以使账号更安全</span>
        <el-button type="text" style="margin-left: 220px" @click="flag=2">修改密码</el-button>
      </el-col>
    </el-row>

<!--    修改已经绑定的手机号码，flag=1-->
    <div v-if="flag==1">
      <el-button @click="reset" type="primary">返回</el-button>
      <div style="margin-top: 30px;width: 100%">
        <el-steps  :active="stepPhoe" finish-status="success" align-center>
          <el-step title="身份验证"></el-step>
          <el-step title="更换手机号码"></el-step>
          <el-step title="完成"></el-step>
        </el-steps>
      </div>
<!--      验证手机号码-->
      <div v-if="stepPhoe==0" style="margin-top: 30px;text-align: center">
        <div>
          <div style="margin-bottom: 20px">已经绑定的手机号码：{{userInfo.userPhone|phoneFilter}}</div>
          <div>
            <el-input v-model="certification" placeholder="输入短信验证码" style="width: 200px;margin-right: 10px"></el-input>
            <el-button type="primary" @click="certify" :disabled="cannotClick">{{content}}</el-button>
          </div>
        </div>
          <el-button :disabled="notPhoneVertify" style="margin-top: 10px" @click="toChangePhone">下一步</el-button>
      </div>
<!--     验证手机号码成功后新的手机号码-->
      <div v-else-if="stepPhoe==1" style="margin-top: 30px;text-align: center">
        <div>
          <el-input v-model="newPhone" placeholder="输入新的手机号码" style="width: 200px;margin-right: 10px"></el-input>
        </div>
        <div style="margin-top: 20px;">
          <el-input v-model="newPhoneCertification" placeholder="输入手机验证码" style="width: 200px;margin-right: 10px"></el-input>
        </div>
        <div style="margin-top: 20px">
          <el-button type="primary" @click="newPhoneCertify" :disabled="cannotClick2">{{content2}}</el-button>
        </div>
        <el-button :disabled="notFinish" style="margin-top: 10px" @click="toFinish">完成</el-button>
      </div>

      <!--    更换手机号码成功-->
      <div v-else style="margin-top: 30px;text-align: center">
        <h3>恭喜你，更换手机号码成功</h3>
        <h4>点击上方按钮返回</h4>
    </div>
      </div>
<!--    更改密码-->
    <div v-if="flag==2" style="display: flex;flex-direction: column;justify-content: center;">
      <div>
        <el-button @click="reset" type="primary" >返回</el-button>
      </div>
      <el-form
      ref="pwdForm"
      :model="pwdForm"
      :rules="rules"
      status-icon
      label-width="120px"
      style="margin: 5px auto"
      >
      <el-form-item prop="email">
        <el-input
            v-model="userInfo.email"
            prefix-icon="el-icon-message"
            disabled
        >
        </el-input>
      </el-form-item>

      <el-form-item  prop="verification">
        <el-input placeholder="请输入验证码"
                  v-model="pwdForm.verification"
        ></el-input>

        <el-button
            type="primary"
            @click="certify2"
            :disabled="cannotClick3"
            size="mini"
            style="font-size: small;margin-left: 20px"
        >{{ content }}
        </el-button>
      </el-form-item>

      <el-form-item  label="密码" prop="password">
        <el-input  type="password" v-model="pwdForm.password" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="password_confirm">
        <el-input  type="password" v-model="pwdForm.password_confirm" autocomplete="off" show-password></el-input>
      </el-form-item>

      <div style="text-align: center">
        <el-button
            type="primary"
            @click="submitForm()"
        >确认
        </el-button>
        <el-button @click="pwdReset()">重置</el-button>
      </div>
      </el-form>
    </div>
<!--    修改邮箱-->
    <div v-if="flag==3">
      <el-button @click="reset" type="primary">返回</el-button>
      <div style="margin-top: 30px;width: 100%">
        <el-steps  :active="stepPhoe" finish-status="success" align-center>
          <el-step title="身份验证"></el-step>
          <el-step title="更换邮箱"></el-step>
          <el-step title="完成"></el-step>
        </el-steps>
      </div>
      <!--      验证邮箱-->
      <div v-if="stepPhoe==0" style="margin-top: 30px;text-align: center">
        <div>
          <div style="margin-bottom: 20px">已经绑定的邮箱：{{userInfo.email}}</div>
          <div>
            <el-input v-model="certification" placeholder="输入短信验证码" style="width: 200px;margin-right: 10px"></el-input>
            <el-button type="primary" @click="certify" :disabled="cannotClick">{{content}}</el-button>
          </div>
        </div>
        <el-button :disabled="notPhoneVertify" style="margin-top: 10px" @click="toChangePhone">下一步</el-button>
      </div>
      <!--     验证邮箱成功后新的邮箱-->
      <div v-else-if="stepPhoe==1" style="margin-top: 30px;text-align: center">
        <div>
          <el-input v-model="newPhone" placeholder="输入新的邮箱" style="width: 200px;margin-right: 10px"></el-input>
        </div>
        <div style="margin-top: 20px;">
          <el-input v-model="newPhoneCertification" placeholder="输入邮箱验证码" style="width: 200px;margin-right: 10px"></el-input>
        </div>
        <div style="margin-top: 20px">
          <el-button type="primary" @click="newPhoneCertify" :disabled="cannotClick2">{{content2}}</el-button>
        </div>
        <el-button :disabled="notFinish" style="margin-top: 10px" @click="toFinish">完成</el-button>
      </div>

      <!--    更换手邮箱成功-->
      <div v-else style="margin-top: 30px;text-align: center">
        <h3>恭喜你，更换邮箱成功</h3>
        <h4>点击上方按钮返回</h4>
      </div>
    </div>
<!--    绑定手机号码，flag=4-->
    <div v-if="flag==4">
      <el-button @click="reset" type="primary">返回</el-button>
      <div style="margin-top: 30px;text-align: center" v-if="stepPhone2==0">
        <div >
          <el-input v-model="newPhone" placeholder="输入要绑定的手机号码" style="width: 200px;margin-right: 10px"></el-input>
        </div>
        <div style="margin-top: 20px;">
          <el-input v-model="newPhoneCertification" placeholder="输入手机验证码" style="width: 200px;margin-right: 10px"></el-input>
        </div>
        <div style="margin-top: 20px">
          <el-button type="primary" @click="newPhoneCertify" :disabled="cannotClick2">{{content2}}</el-button>
        </div>
        <el-button :disabled="notFinish" style="margin-top: 10px" @click="stepPhone2=1">完成</el-button>
      </div>
      <div style="margin-top: 30px;text-align: center" v-if="stepPhone2==1">
        <h3>恭喜你，绑定手机号码成功</h3>
        <h4>点击上方按钮返回</h4>
      </div>
    </div>
  </div>
</template>
<script>
  import {Encrypt,Decrypt} from "@/utools/utils";
  import testRules from "@/utools/testRules.json"
  export default {
    data(){
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
          callback(new Error('两次输入的密码不一致!'))
        } else {
          callback()
        }
      }
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
      let checkPhone = (rule, value, callback) => {
        let regPhone = eval(testRules.phone.testRule)
        if(value==''){
          callback(new Error(testRules.phone.messageEmpty))
        }
        if (!regPhone.test(value)) {
          return callback(new Error(testRules.phone.messageError)); // 有一个if 就要 一个 callback
        }
        return callback(); // 返回一个回调函数给验证，是表单validate生效
      };
      return{
        userId:'',
        userInfo:{},
        flag:0,
      //  修改手机号码的进度
        stepPhoe:0,
      //  手机号码是否验证
        cannotClick:false,
        content:'获取验证码',
        totalTime:60,
        notPhoneVertify:true,
        certification:'',
        newPhone:'',
      //  新手机号码的验证码
        newPhoneCertification:'',
      //  新的手机是否验证
        cannotClick2:false,
        content2:'获取验证码',
        notFinish:true,
        stepPhone2:0,
        pwdForm: {
          password: '1234',
          password_confirm: '1234',
          verification:'',
        },
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
        cannotClick3: false,
      }
    },
    methods:{
      //获取用户个人信息
      async getUserInfo(){
        await this.$axios.get(`${this.HOST}/user/self/${this.userId}`,{
          params:{
            id:this.userId
          }
        }).then((res)=>{
          console.log('getUserInfo',res)
          if(res.data.code==200){
            this.userInfo = res.data.data
            // this.$message.success('获取用户个人信息成功')
          }else {
            this.$message.info('获取用户个人信息失败')
          }
        }).catch((res)=>{
          console.log('getUserInfoError',res)
          this.$message.error('获取个人信息请求失败')
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
        this.notPhoneVertify=false
      },
      //按钮点击计时器
      countDown2() {
        if (this.cannotClick2) return //改动的是这两行代码
        this.cannotClick2 = true
        this.content2 = this.totalTime + 's后重新发送'
        let clock = window.setInterval(() => {
          this.totalTime--
          this.content2 = this.totalTime + 's后重新发送'
          if (this.totalTime < 0) {
            window.clearInterval(clock)
            this.content2 = '重新发送验证邮件'
            this.message =
                '将发送一封验证邮件到账号绑定邮箱，此操作会更改账号密码'
            this.totalTime = 30
            this.cannotClick2 = false //这里重新开启
          }
        }, 1000)
        this.notFinish=false
      },
      async certify() {
        this.countDown()
      //  请求验证码
      },
       async toChangePhone(){
      //  验证验证码是否正确
         this.stepPhoe = 1
      },
      toFinish(){
      //  验证新的手机验证码是否正确
        this.stepPhoe =2
        let time = window.setInterval(()=>{
          this.stepPhoe = 3
        },500)
      },
    //  新手机号码发送验证码
      newPhoneCertify(){
        this.countDown2()
      },
      //失去焦点时判断邮箱是否可用，可用则可以获取验证码
      async emailUseful() {
        await this.$axios.get(`${this.HOST}/email`, {
          params: {
            email: this.pwdForm.email
          }
        }).then((res) => {
          console.log('emailUseful', res);
          if (res.data.message == '允许注册') {
            this.$message.error('该账户未被注册，请前往注册')
            this.emailUse = false
            this.cannotClick = true
          } else {
            this.cannotClick = false
          }
        }).catch((res) => {
          this.$message.error('请求失败')
        })
      },
      //按钮点击计时器
      countDown3() {
        if (this.cannotClick3) return //改动的是这两行代码
        this.cannotClick3 = true
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
            this.cannotClick3 = false //这里重新开启
          }
        }, 1000)
      },
      //发送重置邮箱验证码
      async certify2() {
        this.countDown3()
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
              email:this.userInfo.email,
              password:Encrypt(this.pwdForm.password),
              code:this.pwdForm.verification
            }).then((res)=>{
              console.log('submitFormRes',res)
              if(res.data.code==200){
                this.$message.success('密码修改成功')
                this.flag=0
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
      reset(){
        this.$router.go(0)
      }
    },
    created() {
      this.userId = (window.sessionStorage.getItem('userId'))
      this.getUserInfo()
    },
    filters:{
      phoneFilter(val) {
        const reg = /^(.{3}).*(.{4})$/
        return val.replace(reg, '$1****$2')
      },
    }
  }
</script>
<style lang="less" scoped>
.security{
  text-align: left;
}
.el-input {
  width: 200px;
}
.el-form-item{
  padding: 5px;
}

</style>
