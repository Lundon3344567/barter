<template>
  <div class="addGoods">
    <el-card
    shadow="always"
    v-loading="loading"
    element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0,0,0,0.8)">
          <h2>发布商品</h2>
        <el-divider></el-divider>
  <br>
  <!-- 完善商品信息 -->
    <div class="step">
      <el-steps :active="active" finish-status="success" align-center>
      <el-step title="选择商品种类"></el-step>
      <el-step title="完善商品信息"></el-step>
      <el-step title="完成"></el-step>
      </el-steps>
    </div>
    <el-form  ref="addFormRef" :model="goodsForm">
    <!-- 选择商品种类 -->
    <div v-if="active==0" label-width="120px">
      <el-form-item>
        <el-cascader
        v-model="goodsForm.classifiedNum"
        :options="cateList"
        :props="cateProps"
        placeholder="请选择商品品牌"
        clearable
        @change="handleChange">
        </el-cascader>
      </el-form-item>
      <el-button style="margin-top: 12px;" @click="next">下一步</el-button>
    </div>

    <!-- 完善商品信息 -->
    <el-form v-if="active==1" :rules="addFormRules"  ref="addFormRef" :model="goodsForm">
      <el-form-item el-form-item label="标题"  prop="goodName">
        <el-input v-model="goodsForm.goodName" placeholder="请填写标题"></el-input>
    </el-form-item>
    <el-form-item label="商品描述" prop="goodDescribe">
      <el-input v-model="goodsForm.goodDescribe" type="textarea"></el-input>
    </el-form-item>
    <el-form-item label="价格" prop="goodPrice">
      <el-input v-model="goodsForm.goodPrice" type="number"></el-input>
    </el-form-item>
    <el-form-item label="商品数量" prop="goodNum">
      <el-input v-model="goodsForm.goodNum" type="number"></el-input>
    </el-form-item>
    <el-form-item>
      <el-upload v-model="goodsForm.imgUrl" :action="uploadURL" :on-success="uploadSuccess"
        :on-preview="handlePreview" accept=".png, .jpg" :file-list="fileList"
        :on-remove="handleRemove" list-type="picture">
        <el-button size="small" type="primary">点击上传商品图片</el-button>
        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
      </el-upload>
    </el-form-item>
    <el-button type="primary" @click="submitForm()">下一步</el-button>
    </el-form>
    </el-form>
    <!-- 完成 -->
    <el-form v-if="active==2">
    <el-form-item>
    <h3>商品发布成功</h3>
    <router-link :to="Calpath('home')"><el-button type="primary">回到主页</el-button></router-link>
    </el-form-item>
    </el-form>

    </el-card>
  </div>
</template>

<script>
import { Decrypt, Encrypt } from "@/utools/utils";
import axios from "axios";
export default {
  data() {
    const checkprice=(rule,value,callback)=>{
        if(!value){
          return callback(new Error('价格不能为空'));
        }else{
          if(value<0){
            callback(new Error('价格不能为负数'));
          }else{
            callback();
          }
        }
      };
    var checknum=(rule,value,callback)=>{
      if(!value){
        return callback(new Error('商品数量不能为空'));
      }else{
        if(value<0){
          callback(new Error('商品数量不能为负数'));
        }else{
          callback();
        }
      }
    }
    return {
      goodsForm: {
        ownerId:'',
        goodName: '',
        imgUrl:'',
        goodDescribe:'',
        goodPrice:'',
        classifiedNum:'',
        goodNum:''
      },
      active: 0,
       // 商品分类列表
      cateList: [],
      // img_url:[],
      //选择框配置对象
      cateProps:{
        value:'parentId',
        label:'name',
        children:'children'
      },
      uploadURL: "http://127.0.0.1:8081/qiniu/image",
      imageurl:"",
      addFormRules:{
        goodName: [
          { required: true, message: '请添加标题', trigger: 'blur' },
          { min: 3, max: 20, message: "标题在 3 到 20 个字内哦", trigger: "blur" }
        ],
        goodDescribe: [
          { required: true, message: '请添加商品描述', trigger: 'blur' },
          { min: 0, max: 100, message: "商品描述在100 个字内哦", trigger: "blur" }
        ],
        goodPrice: [
          { required: true, validator:checkprice, trigger: 'blur' },
          { min: 1, max: 5, message: "商品价格在99999内哦", trigger: "blur" }
        ],
        goodNum: [
          { required: true, validator:checknum, trigger: 'blur' },
          { min: 1, max: 2, message: "商品数量在99内哦", trigger: "blur" }
        ]
      }
    };
  },

  methods: {
    next(){
      // if(this.classifiedNum==null){
      //   this.$message.error('请选择分类');
      // }

        this.active = 1

    },
    handleChange() {
      console.log(this.goodsForm.classifiedNum);
    },
    uploadSuccess(res) {
      console.log('img',res)
        this.$message.success('图片上传成功');
        this.goodsForm.imgUrl=res.data.path;
        console.log('111',this.goodsForm.imgUrl)
    },
        // 获取商品分类
    async getCateList(){
      await this.$axios.get(`${this.HOST}/released/classify/goods`).then(res =>{
        console.log('getCateListRes',res)
        if (res.data.code == 200) {
            // this.$message.success("获取列表成功");
            this.cateList = res.data.data}
      }).catch(res=>{
        console.log('getCateListError',res)
      })
    },
    //提交表单
    async submitForm(){
      if(this.goodsForm.goodName!=''&&this.goodsForm.goodPrice!=''&&this.goodsForm.goodNum!=''){
        this.active = 2;
      }else{
        this.active = 1;
        this.$message.error('请完善信息')
      }
      await this.$axios.post(`${this.HOST}/released/classify/released`,{
        ownerId:window.sessionStorage.getItem('userId'),
        goodName:this.goodsForm.goodName,
        classifiedNum:this.goodsForm.classifiedNum[1],
        goodPrice:this.goodsForm.goodPrice,
        imgUrl:this.goodsForm.imgUrl,
        goodDescribe:this.goodsForm.goodDescribe,
        goodNum:this.goodsForm.goodNum
      }).then((res)=>{
        console.log('submitFormRes',res)
      }).catch((res)=>{
        console.log('submitFormError',res)
        this.$message.error('提交失败')
      })

    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    //计算路径
    Calpath(path){
      return '/'+Encrypt(path)
    }
  },

  created() {
    this.getCateList()
  }
}
</script>

<style scoped>

.addGoods {
  margin: 40px auto;
  height: 100%;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  flex-direction: column;
}

.step {
        width: 1000px;
        height: 100px;
    }

.nextoption {
  height: 80px;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  font-size: medium;
}
</style>
