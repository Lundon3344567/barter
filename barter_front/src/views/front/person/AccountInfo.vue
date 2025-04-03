/**
 * @Description: 修改账户信息
 * @author Lion-Air14
 * @date 2021/12/22
 * @time 19:07
*/
<template>
  <div class="person">
    <el-form :model="userInfo" ref="userInfo" :rules="userInfoRules">
      <el-descriptions title="个人信息" direction="vertical" border >
        <el-descriptions-item label="用户头像" align="center" style="display: flex;flex-direction: row">
          <el-image :src="userInfo.nickname" style="width: 100px;height: 100px;text-align: center" align="center"></el-image>
          <el-upload
              class="avatar-uploader"
              list-type="picture"
              :action="uploadURL"
              :auto-upload="isUpload"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              :on-exceed="handleExceed"
              :on-success="handleSuccess"
              :multiple="false"
              :limit="1"
              :before-upload="beforeAvatarUpload"
              ref="upload"
          >
            <el-button size="small" type="primary">点击上传头像</el-button>
<!--            <i class="el-icon-upload"></i>-->
<!--            <div class="el-upload__text">将图片拖到此处，或<em>点击上传</em></div>-->
<!--            <div slot="tip" class="el-upload__tip"></div>-->
          </el-upload>
        </el-descriptions-item>
        <el-descriptions-item label="用户名字" style="text-align: center">
          <el-form-item prop="username">
            <el-input
                :placeholder="userInfo.username"
                v-model="userInfo.username"
                clearable>
            </el-input>
          </el-form-item>
        </el-descriptions-item>
        <el-descriptions-item label="用户学号" >
          <el-form-item prop="userSid">
            <el-input :placeholder="userInfo.userSid"
                      v-model="userInfo.userSid"
                      clearable>

            </el-input>
          </el-form-item>
        </el-descriptions-item>
        <el-descriptions-item label="手机号">
          <el-form-item prop="userPhone">
            <el-input
                :placeholder="userInfo.userPhone"
                v-model="userInfo.userPhone">

            </el-input>
          </el-form-item>
        </el-descriptions-item>
        <el-descriptions-item label="性别">
          <el-radio-group v-model="userInfo.gender">
            <el-radio-button label="1">男</el-radio-button>
            <el-radio-button label="0">女</el-radio-button>
          </el-radio-group>
        </el-descriptions-item>
        <el-descriptions-item label="学生邮箱">
          <el-form-item prop="stuEmail">
            <el-input
                :placeholder="userInfo.stuEmail"
                v-model="userInfo.stuEmail"
            ></el-input>
          </el-form-item>
        </el-descriptions-item>
        <el-descriptions-item label="个人邮箱">
          {{userInfo.email}}
        </el-descriptions-item>
        <el-descriptions-item label="积分">
          {{userInfo.credit}}
        </el-descriptions-item>
        <el-descriptions-item label="校区">
          <el-alert
              title="修改校区便于获取商品哦"
              type="error"
          style="margin-bottom: 5px">
          </el-alert>
            <el-radio-group v-model="userInfo.campus">
              <el-radio-button :label="1">成龙校区</el-radio-button>
              <el-radio-button :label="0">狮子山校区</el-radio-button>
            </el-radio-group>
        </el-descriptions-item>
        <el-descriptions-item label="生日">
          <el-alert
              title="修改生日便于推荐商品哦"
              type="error"
              style="margin-bottom: 5px"></el-alert>
          <el-tag>{{userInfo.birth.substring(0,10)}}</el-tag>
          <el-date-picker
              v-model="userInfo.birth"
              :default-value="userInfo.birth"
              type="date"
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-descriptions-item>
        <el-descriptions-item label="用户状态">
          <el-tag v-if="userInfo.userState==1" type="success">可用</el-tag>
          <el-tag v-else type="warning">锁定</el-tag>
        </el-descriptions-item>
      </el-descriptions>
      <el-button type="primary" style="margin-top: 20px" :disabled="userInfo.userState!=1" @click="updateUserInfo">保存</el-button>
    </el-form>
  </div>
</template>
<script>
import {Encrypt,Decrypt} from "@/utools/utils";
import testRules from "@/utools/testRules.json";
export default {
  data(){
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
    let checkUserName = (rule, value, callback) => {
      // this.userNameUserFul()
      let regUserName = eval(testRules.userName.testRule)
      if(value==''){
        callback(new Error(testRules.userName.messageEmpty))
      }
      if (!regUserName.test(value)) {
        return callback(new Error(testRules.userName.messageError)); // 有一个if 就要 一个 callback
      }
      return callback(); // 返回一个回调函数给验证，是表单validate生效
    };
    return{
      userId:'',
      userInfo:{
        stuEmail:'',
        //图片地址
        nickname:''
      },
      isUpload:false,
      userInfoRules:{
        username:[
          {validator:checkUserName,trigger:'blur',required:true}
        ],
        userSid:[
          {required: true, message: "请输入学号", trigger: "blur"},
          {max:10,min:10,message: '学号长度不对'}
        ],
        stuEmail:[
          {validator:checkEmail,trigger:'blur',required:true}
        ],
        userPhone:[
          {validator:checkPhone,trigger:'blur',required:true}
        ]
      },
      uploadURL: "http://127.0.0.1:8081/qiniu/image",
    }
  },
  methods:{
    /**
     * @desc 获取用户个人信息
     * @returns {Promise<void>}
     */
    async getUserInfo(){
      await this.$axios.get(`${this.HOST}/user/self/${this.userId}`,{
        params:{
          id:this.userId
        }
      }).then((res)=>{
        console.log('getUserInfo',res)
        if(res.data.code==200){
          this.userInfo = res.data.data
          this.$message.success('获取用户个人信息成功')
        }else {
          this.$message.info('获取用户个人信息失败')
        }
      }).catch((res)=>{
        console.log('getUserInfoError',res)
        this.$message.error('获取个人信息请求失败')
      })
    },
    /**
     * @desc 移出图片成功提示
     * @param file
     * @param fileList
     * @returns {ElMessageComponent}
     */
    handleRemove(file, fileList) {
       return this.$message.success('图片移除成功')
    },
    /**
     * @desc 处理预览
     * @param file
     */
    handlePreview(file) {
      console.log(file);
    },
    /**
     * @desc 限制头像图片的上传
     * @param files
     * @param fileList
     */
    handleExceed(files, fileList) {
      this.$message.warning(`只能上传一张图片`);
    },
    /**
     * @desc 在移出图片前的询问
     * @param file
     * @param fileList
     * @returns {Promise<MessageBoxData>}
     */
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除图片吗？`);
    },
    /**
     * @desc 图片上传成功
     * @param response
     * @param file
     * @param fileList
     */
    handleSuccess(response,file,fileList){
      console.log('handleSuccessResponse',response)
      this.userInfo.nickname = response.data.path
      this.update()
    },
    /**
     * @desc 在上传图片之前确定文件类型和大小
     * @param file
     * @returns {boolean}
     */
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    async userNameUserFul(){
      if(!this.userInfo.username) {
        return;
      }
      await this.$axios.get(`${this.HOST}/user`,{
        params:{
          username:this.userInfo.username
        }
      }).then( (res) =>{
        console.log('usernameUseful',res)
        if(res.data.code===400){
          this.$message.error('用户名已经存在')
          this.userInfo.username=''
        }
      }).catch((res) =>{
        this.$message.error('请求失败！')
      })
    },
    //更新用户的信息
     async updateUserInfo() {
       //  this.$refs['upload'].$children[0].$refs.input.click()
       this.$refs.userInfo.validate(async valid => {
         if (!valid) {
           this.$message.error('请填写必要信息')
           return;
         } else {
           console.log('userInfo', this.userInfo)
           if(this.$refs.upload.uploadFiles.length==0){
             await this.update()
           }else{
             await this.$refs.upload.submit();
           }
         }
       })
     },
    async update(){
      await this.$axios.put(`${this.HOST}/user/self`, {
        "userId":window.sessionStorage.getItem('userId'),
        "userSid": this.userInfo.userSid,
        "nickname": this.userInfo.nickname,
        "gender": this.userInfo.gender,
        "birth": this.userInfo.birth,
        "campus": this.userInfo.campus,
        "stuEmail": this.userInfo.stuEmail,
        "userPhone": this.userInfo.userPhone,
      }).then((res) => {
        console.log('updateUserInfoRes', res)
        if(res.data.code==200){
          this.$message.success('修改个人信息成功')
          this.getUserInfo()
        }else{
          this.$message.info('修改个人信息失败')
        }
      }).catch((res) => {
        console.log('updateUserInfoError', res)
        this.$message.error('修改个人请求失败')
      })
    }
  },
  created() {
    this.userId = (window.sessionStorage.getItem('userId'))
    this.getUserInfo()
  }
}
</script>
<style lang="less" scoped>
/* el-descriptions-item 文字居中 */
.el-descriptions__body .el-descriptions__table .el-descriptions-item__cell {
  text-align: center;
}

</style>
