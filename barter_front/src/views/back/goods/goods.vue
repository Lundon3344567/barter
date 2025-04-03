/**
 * @Description: 后台展示商品列表
 * @author Lion-Air14
 * @date 2021/12/8
 * @time 15:25
*/
<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="Calpath('AdminHome')">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
<!--      搜索框和添加商品区域-->
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="请输入内容" v-model="query" clearable @change="searchGood" @keyup.enter="searchGood">
            <el-button slot="append" icon="el-icon-search" @click="searchGood"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4" style="float: right">
          <kt-button perms="normal:good:save" label="添加商品" size="mini" @click="addDialogVisible = true"></kt-button>
        </el-col>
      </el-row>
      <!--    商品展示-->
      <el-table :data="goodsList" border stripe width="100%">
        <el-table-column label="商品名称" prop="goodName" width="150px" align="center" :show-overflow-tooltip="true"> </el-table-column>
        <el-table-column label="商品描述" prop="goodDescribe" width="200px" align="center" :show-overflow-tooltip="true"
        > </el-table-column>
        <el-table-column label="商品价格" prop="goodPrice" align="center"></el-table-column>
        <el-table-column label="商品数量" prop="goodNum" align="center"> </el-table-column>
        <el-table-column label="商品状态" align="center">
          <template slot-scope="scope">
              <el-tag v-if="scope.row.goodFlag==1">审核中</el-tag>
              <el-tag v-else-if="scope.row.goodFlag==2" type="danger">已冻结</el-tag>
              <el-tag v-else-if="scope.row.goodFlag==100" type="warning">审核不通过</el-tag>
              <el-tag v-else-if="scope.row.goodFlag==0||scope.row.goodFlag==101" type="success">销售中</el-tag>
              <el-tag v-else-if="scope.row.goodFlag==-1" type="info">已售出</el-tag>
              <el-tag v-else-if="scope.row.goodFlag==-2" type="info">已删除</el-tag>
          </template>
        </el-table-column>
<!--        <el-table-column label="商品收藏数" prop="collectionNum"></el-table-column>-->
<!--        <el-table-column label="商品图片" prop="goodPrice"> </el-table-column>-->
        <el-table-column label="操作" width="180px" align="center">
          <template #default = 'scoped'>
            <!-- 修改按钮 -->
            <kt-button perms="normal:good:update"
                      type="primary"
                      icon="el-icon-edit"
                      size="mini"
                      @click="showEditDialog(scoped.row)">
            </kt-button>
            <!-- 删除按钮 -->
            <kt-button perms="normal:good:delete"
                      type="danger"
                      icon="el-icon-delete"
                      size="mini"
                      @click="removeGoodsById(scoped.row.goodId,scoped.row.goodName)">
            </kt-button>
          </template>
        </el-table-column>
      </el-table>
      <!--  分页区域-->
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[10,20, 30, 50, 100]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
      >
      </el-pagination>
    </el-card>
    <!-- 添加商品的对话框 -->
    <el-dialog
        title="添加商品"
        :visible.sync="addDialogVisible"
        width="50%"
        @close="addDialogClosed"
    >
      <el-form
          :model="addForm"
          :rules="addFormRules"
          ref="addFormRef"
          label-width="100px"
      >
        <el-form-item label="图片链接" prop="imgUrl">
          <el-input v-model="addForm.imgUrl"></el-input>
        </el-form-item>
        <el-form-item label="拥有者ID" prop="ownerId">
          <el-input v-model="addForm.ownerId"></el-input>
        </el-form-item>
        <el-form-item label="商品描述" prop="goodDescribe">
        <el-input v-model="addForm.goodDescribe"></el-input>
        </el-form-item>
        <el-form-item label="商品名称" prop="goodName">
          <el-input v-model="addForm.goodName"></el-input>
        </el-form-item>
        <el-form-item label="商品价格" prop="goodPrice">
          <el-input v-model="addForm.goodPrice"></el-input>
        </el-form-item>
        <el-form-item label="商品数量" prop="goodNum">
          <el-input v-model="addForm.goodNum"></el-input>
        </el-form-item>
        <el-form-item label="商品分类" prop="classifiedNum">
          <el-input v-model="addForm.classifiedNum" disabled></el-input>
        </el-form-item>
        <el-form-item>
          <!-- options 用来指定数据源 -->
          <!-- props 用来指定配置对象 -->
          <el-cascader
              expand-trigger="hover"
              :options="cateList"
              :props="cascaderProps"
              v-model="selectedKeys"
              @change="parentCateChanged(addForm)"
              clearable
              :change-on-select="true"
              placeholder="请选择商品父类"
          >
          </el-cascader>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addGoods">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 修改商品的对话框 -->
    <el-dialog
        title="修改商品"
        :visible.sync="editDialogVisible"
        width="50%"
        @close="editDialogClosed"
    >
      <el-form
          :model="editForm"
          :rules="editFormRules"
          ref="editFormRef"
          label-width="100px"
      >
        <el-form-item label="图片链接" prop="imgUrl">
          <el-input v-model="editForm.imgUrl"></el-input>
        </el-form-item>
        <el-form-item label="拥有者ID" prop="ownerId">
          <el-input v-model="editForm.ownerId"></el-input>
        </el-form-item>
        <el-form-item label="商品描述" prop="goodDescribe">
          <el-input v-model="editForm.goodDescribe"></el-input>
        </el-form-item>
        <el-form-item label="商品名称" prop="goodName">
          <el-input v-model="editForm.goodName"></el-input>
        </el-form-item>
        <el-form-item label="商品价格" prop="goodPrice">
          <el-input v-model="editForm.goodPrice"></el-input>
        </el-form-item>
        <el-form-item label="商品分类" prop="classifiedNum">
          <el-input v-model="editForm.classifiedNum" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="商品数量" prop="goodNum">
        <el-input v-model="editForm.goodNum"></el-input>
        </el-form-item>
        <el-form-item>
          <!-- options 用来指定数据源 -->
          <!-- props 用来指定配置对象 -->
          <el-cascader
              expand-trigger="hover"
              :options="cateList"
              :props="cascaderProps"
              v-model="selectedKeys"
              @change="parentCateChanged(editForm)"
              clearable
              :change-on-select="true"
              placeholder="请选择商品父类"
          >
          </el-cascader>
        </el-form-item>
        <el-form-item label="商品状态">
          <template>
            <el-radio v-model="editForm.goodFlag" :label="1">审核中</el-radio>
            <el-radio v-model="editForm.goodFlag" :label="2">冻结</el-radio>
            <el-radio v-model="editForm.goodFlag" :label="100">审核不通过</el-radio>
            <el-radio v-model="editForm.goodFlag" :label="101">审核通过</el-radio>
            <el-radio v-model="editForm.goodFlag" :label="-2">删除</el-radio>
          </template>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editGoodInfo">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import {Encrypt} from "@/utools/utils";

export default {
  name: "goods",
  data(){
    return{
      //商品模糊搜索
      query:'',
      //控制添加商品的对话框
      addDialogVisible:false,
      //控制修改商品信息的对对话框
      editDialogVisible:false,
      goodsList:{

      },
      pageNum: 1,
      pageSize:10,
      total:4,
      addForm:{
        imgUrl: "#",
        ownerId: 55,
        goodDescribe: "这里是添加商品测试",
        viewCount: 0,
        goodFlag: 1,
        goodName: "测试添加商品",
        collectionNum: 0,
        classifiedNum: 5,
        goodPrice: 45,
        goodNum:1,
      },
      addFormRules:{
        imgUrl: [
          {required:true,trigger:'blur',message:'请输入图片地址'}
        ],
        goodDescribe: [
          {required:true,trigger:'blur',message:'请输入商品描述'}
        ],
        goodName: [
          {required:true,trigger:'blur',message:'请输入商品名称'}
        ],
        classifiedNum: [
          {required:true,trigger:'blur',message:'请输入商品分类'}
        ],
        goodPrice: [
          {required:true,trigger:'blur',message:'请输入商品价格'},
          { type:"number" ,min: 0, message: '价格要大于0', trigger: 'blur' }
        ],
        ownerId: [
          {required:true,trigger:'blur',message:'请输入拥有者ID'}
        ],
        goodFlag:[
          {required:true,trigger:'blur',message:'请选择商品状态'}
        ],
        goodNum:[
          {required:true,trigger:'blur',message:'请输入商品数量'},
          { type:"number" ,min: 0, message: '数量要大于0', trigger: 'blur' }
        ]
      },
      editForm:{},
      editFormRules:{
        imgUrl: [
          {required:true,trigger:'blur',message:'请输入图片地址'}
        ],
        goodDescribe: [
          {required:true,trigger:'blur',message:'请输入商品描述'}
        ],
        onUseingTime: [
          {required:true,trigger:'blur',message:'请输入使用时间'}
        ],
        goodName: [
          {required:true,trigger:'blur',message:'请输入商品名称'}
        ],
        classifiedNum: [
          {required:true,trigger:'blur',message:'请输入商品分类'}
        ],
        goodPrice: [
          {required:true,trigger:'blur',message:'请输入商品价格'}
        ],
        ownerId: [
          {required:true,trigger:'blur',message:'请输入拥有者ID'}
        ],
        goodFlag:[
          {required:true,trigger:'blur',message:'请输入goodFlag'}
        ]
      },
      cateList:[],
      // 选中的父级分类的Id数组
      selectedKeys: [],
    //指定级联选择器的配置对象
      cascaderProps: {
        value: "classifyId",
        label: "name",
        children: "children",
      },
      // 父级分类的列表
      parentCateList: [],
    }
  },
  methods:{
    //获取所有商品
    async getGoods(){
      await this.$axios.get(`${this.HOST}/admin/goods/page`,{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize
        }
      }).then((res) =>{
        console.log('goods',res)
        if(res.data.code==200){
          this.goodsList = res.data.data.list
          this.total = res.data.data.total
          // this.$message.success('商品获取成功')
        }else{
          this.$message.info('商品列表获取失败')
        }

      }).catch((res) =>{
        this.$message.error('商品列表请求失败！')
      })
    },
    //搜索商品
    async searchGood(){
      console.log('query',this.query)
      if(this.query==""){
       await this.getGoods()
      }else{
        await this.$axios.get(`${this.HOST}/admin/goods/name`,{
          params:{
            name:this.query,
            pageNum:this.pageNum,
            pageSize:this.pageSize,
          }
        }).then((res)=>{
          console.log('searchGood',res);
          if(res.data.code==200){
            this.goodsList =res.data.data.list
            this.total  =res.data.data.total
          }else{
            this.$message.info('搜索商品请求失败！')
          }
        }).catch((res)=>{
          console.log('searchGoodError',res)
          this.$message.error('搜索商品请求有误！')
        })
      }
    },
    // 监听pagesize该报事件
    handleSizeChange(newSize) {
      this.pageSize = newSize;
      this.getGoods();
    },
    // 监听页面值改变的事件
    handleCurrentChange(newPage) {
      this.pageNum = newPage;
      this.getGoods();
    },
    // 监听添加用户对话框的关闭事件
    addDialogClosed() {
      this.$refs.addFormRef.resetFields();
    },
    // 展示编辑用户的对话框
    async showEditDialog(info) {
      console.log('EditDialog',info);
          this.editDialogVisible = true;
          this.editForm=info
    },
    // 监听修改用户对话框的关闭事件
    editDialogClosed() {
      this.$refs.editFormRef.resetFields();
      this.selectedKeys = [];
    },
    // 修改商品信息并提交
    editGoodInfo() {
      this.$refs.editFormRef.validate(async valid => {
        if (!valid) return;
        // 发起修改商品信息的数据请求
        console.log('editInfo',this.editForm)
        await this.$axios.put(`${this.HOST}/admin/goods`,{
          "goodId":this.editForm.goodId,
          "imgUrl": this.editForm.imgUrl,
          "goodDescribe": this.editForm.goodDescribe,
          "classifiedNum": this.editForm.classifiedNum,
          "goodPrice": this.editForm.goodPrice,
          "ownerId":this.editForm.ownerId,
          "goodFlag":this.editForm.goodFlag,
          "collectionNum":this.editForm.collectionNum,
          "viewCount":this.editForm.viewCount,
          "goodName":this.editForm.goodName,
          "createTime":this.editForm.createTime,
          "goodNum":this.editForm.goodNum
        }).then((res)=>{
          console.log('editGood',res);
          if(res.data.code==200){
            this.$message.success('商品信息修改成功')
            this.editDialogVisible=false
            this.getGoods()
          }else{
            this.$message.info('商品信息修改失败！')
          }
        }).catch((res)=>{
          this.$message.error('修改商品信息请求失败！')
        })
      });
    },
    // 根据id删除对应的商品信息
    async removeGoodsById(id,name) {
      // 弹框询问用户是否删除数据
      const confirmResult = await this.$confirm('此操作将删除'+name+', 是否继续?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(error => error)
      // 如果用户点击确认则会返回字符串confirm
      // 如果用户点击取消则会返回字符串cancle
      // console.log(confirmResult)
      if (confirmResult !== 'confirm') {
        return this.$message.info('已经取消删除')
      }
      await this.$axios.delete(`${this.HOST}/admin/goods/${id}`).then((res) =>{
        console.log('deletGood',res);
        if(res.data.code==200){
          this.$message.success('删除商品成功')
          this.getGoods()
        }else{
          this.$message.info('商品删除失败！')
        }
      }).catch((res)=>{
        this.$message.error('删除商品请求失败！')
      })
    },
    //添加商品
    async addGoods(){
      await this.$refs.addFormRef.validate(async (valid) => {
        if (!valid) return;
        else {
          this.$axios.post(`${this.HOST}/admin/goods`,{
            imgUrl: this.addForm.imgUrl,
            goodDescribe: this.addForm.goodDescribe,
            onUseingTime: this.addForm.onUseingTime,
            goodName: this.addForm.goodName,
            classifiedNum: this.addForm.classifiedNum,
            goodPrice: this.addForm.goodPrice,
            ownerId:this.addForm.ownerId,
            goodFlag:this.addForm.goodFlag,
            collectionNum:this.addForm.collectionNum,
            viewCount:this.addForm.viewCount,
            goodNum:this.addForm.goodNum
          }).then((res) =>{
            console.log('adGoods',res)
            this.getGoods()
            this.addDialogVisible=false
          }).catch((res) =>{
            this.$message.error('添加商品请求失败！')
          })
        }
      })
    },
    //计算路径
    Calpath(path){
      return '/'+Encrypt(path)
    },
    //  获取商品分类列表
    async getCateList() {
      await this.$axios
          .get(`${this.HOST}/released/classify/goods`)
          .then((res) => {
            console.log("res111", res);
            this.cateList = res.data.data;
          })
          .catch((res) => {
            this.$message.error("商品分类请求有误!");
            console.log('resError',res)
          });
    },
    //选择项发生变化则触发
    parentCateChanged(info) {
      console.log(this.selectedKeys);
      //  如果selectedKeys数组中的length大于0，证明选中了父级分类
      //  反之说明没有选中任何父级分类
      if (this.selectedKeys.length > 1) {
        // 父级分类的ID
        info.classifiedNum = this.selectedKeys[1]
      } else {
        this.$message.info('请选择二级商品分类')
      }
      // console.log('classifiedNum',this.editForm.classifiedNum)
    },

  },
  created() {
    this.getGoods()
    this.getCateList()
  }
}
</script>

<style scoped>

</style>
