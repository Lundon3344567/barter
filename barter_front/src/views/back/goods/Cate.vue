/**
 * @Description: 后台商品分类列表
 * @author Lion-Air14
 * @date 2021/12/8
 * @time 15:23
*/
<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="Calpath('AdminHome')">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品分类</el-breadcrumb-item>
      <el-row style="float: right">
        <el-col>
          <el-button type="primary" @click="showAddCateDialog" size="mini"
          >添加分类
          </el-button
          >
        </el-col>
      </el-row>
    </el-breadcrumb>
    <!--    卡片视图区域-->
    <el-card>

      <!--      表格-->
      <tree-table
          class="treeTable"
          :data="cateList"
          :columns="columns"
          :selection-type="false"
          :expand-type="false"
          show-index
          index-text="#"
          border
          align="center"
          style="text-align: center"
      >
        <!--排序-->
        <template slot="order" slot-scope="scope" style="text-align: center">
          <el-tag v-if="scope.row.parentId === 0" align="center">一级</el-tag>
          <el-tag type="success" v-else align="center">二级</el-tag>
        </template>
        <!--        操作-->
        <template slot="opt" slot-scope="scope" align="center">
          <el-button type="primary" align="center" size="mini" icon="el-icon-edit" @click="showEditDialog(scope.row)">编辑</el-button>
          <el-button type="danger" align="center" size="mini" icon="el-icon-delete" @click="removeCateById(scope)">删除</el-button>
        </template>
      </tree-table>
    </el-card>
    <!-- 添加分类的对话框 -->
    <el-dialog
        title="添加分类"
        :visible.sync="addCateDialogVisible"
        width="50%"
        @close="addCateDialogClosed"
    >
      <!-- 添加分类的表单 -->
      <el-form
          :model="addCateForm"
          :rules="addCateFormRules"
          ref="addCateFormRef"
          label-width="100px"
      >
        <el-form-item label="分类名称：" prop="name">
          <el-input v-model="addCateForm.name"></el-input>
        </el-form-item>

        <el-form-item label="同级分类：">
          <!-- options 用来指定数据源 -->
          <!-- props 用来指定配置对象 -->
          <el-cascader
              expand-trigger="hover"
              :options="cateList"
              :props="cascaderProps"
              v-model="selectedKeys"
              @change="parentCateChanged"
              clearable
              change-on-select
          >
          </el-cascader>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addCateDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addCate">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改分类的对话框 -->
    <el-dialog
        title="修改分类"
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
        <el-form-item label="分类ID" prop="classifyId">
        <el-input v-model="editForm.classifyId" disabled></el-input>
      </el-form-item>
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>

        <el-form-item label="同级分类：" prop="parentId">
          <!-- options 用来指定数据源 -->
          <!-- props 用来指定配置对象 -->
          <el-cascader
              expand-trigger="hover"
              :options="cateList"
              :props="cascaderProps"
              v-model="editForm.parentId"
              @change="parentCateChanged"
              clearable
              change-on-select
          >
          </el-cascader>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editCateInfo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import {Encrypt} from "@/utools/utils";

export default {
  data() {
    //查询条件
    return {
      //商品分类数据列表，默认为空
      cateList: [],
      //总数据条数
      total: 0,
      //table指定列的定义
      columns: [
        {
          label: "分类名称",
          prop: "name",
        },
        {
          label: "排序",
          //  表示将当前列第一为模板列
          type: "template",
          //表示当前模板列的使用名称
          template: "order",
        },
        {
          label: "操作",
          //  表示将当前列第一为模板列
          type: "template",
          //表示当前模板列的使用名称
          template: "opt",
        },
      ],
      //控制分类对话框的显示与隐藏
      addCateDialogVisible: false,
      //添加分类的表单数据对象
      addCateForm: {
        //将要添加的分类名称
        name: "",
        //  父级分类的id
        parentId: 0,
      },
      //添加分类规则
      addCateFormRules: {},
      // 父级分类的列表
      parentCateList: [],
      // 指定级联选择器的配置对象
      cascaderProps: {
        value: "classifyId",
        label: "name",
        children: "children",
      },
      // 选中的父级分类的Id数组
      selectedKeys: [],
    //  编辑分类
      editForm:{

      },
      editFormRules:{
        classifyId:[
          {require:true,trigger:'blur',message:'分类ID必选'}
        ],
        name:[
          {require:true,trigger:'blur',message:'请输入分类名称'}
        ],

      },
      editDialogVisible:false,
      pageNum:1,
      pageSize:6,
    };
  },
  created() {
    this.getCateList();
  },
  methods: {
    /**
     * @desc 获取商品分类列表
     * @returns {Promise<void>}
     */
    async getCateList() {
      await this.$axios
          .get(`${this.HOST}/released/classify/goods`)
          .then((res) => {
            console.log("res111", res);
            this.cateList = res.data.data;
          })
          .catch((res) => {
            this.$message.error("商品分类请求有误!");
            // console.log('resError',res)
          });
    },
    /**
     * @desc 监听pagesize改变
     * @param newSize
     */
    handleSizeChange(newSize) {
      this.pageSize = newSize;
      this.getCateList();
    },
    /**
     * @desc 监听pagenum改变
     * @param newPage
     */
    handleCurrentChange(newPage) {
      this.pageNum = newPage;
      this.getCateList();
    },
    /**
     * @desc 点击按钮，展示添加分类的对话框
     */
    showAddCateDialog() {
      //先获取父级分类的数据列表
      // this.getParentCateList();
      //再展示出对话框
      this.addCateDialogVisible = true;
    },
    /**
     * @desc 获取父级分类的数据列表
     * @returns {Promise<void>}
     */
    async getParentCateList() {
      await this.$axios
          .get(`${this.HOST}/released/classify/goods`)
          .then((res) => {
            this.parentCateList = res.data.data;
            console.log("getParentCateList", res.data);
          })
          .catch((res) => {
            this.$message.error("获取分类请求失败！");
          });
    },
    //选择项发生变化则触发
    parentCateChanged() {
      console.log(this.selectedKeys);
      //  如果selectedKeys数组中的length大于0，证明选中了父级分类
      //  反之说明没有选中任何父级分类
      if (this.selectedKeys.length > 1) {
        // 父级分类的ID
        this.addCateForm.parentId =
            this.selectedKeys[0];
        return;
      } else {
        // 父级分类的ID
        this.addCateForm.parentId = 0;
        return;
      }
    },
    //点击按钮添加新的分类
    addCate() {
      this.$refs.addCateFormRef.validate(async (valid) => {
        await this.$axios
            .post(`${this.HOST}/released/classify`, {
              name: this.addCateForm.name,
              parentId: this.addCateForm.parentId,
            })
            .then((res) => {
              console.log("addCate", res);
              if (res.data.code == 200) {
                this.$message.success('添加分类成功')
                this.addCateDialogVisible=false
                this.getCateList()
              } else {
                this.$message.info('添加分类失败')
              }
            })
            .catch((res) => {
              this.$message.error('添加分类请求失败')
              console.log("addCateError", res);
            });
      });
    },
    // 监听对话框的关闭事件，重置表单数据
    addCateDialogClosed() {
      this.$refs.addCateFormRef.resetFields();
      this.selectedKeys = [];
      this.addCateForm.cat_pid = 0;
      this.addCateForm.cat_level = 0;
    },
    //计算路径
    Calpath(path) {
      return "/" + Encrypt(path);
    },
    //删除分类
    async removeCateById(scope) {
      console.log('scop', scope)
      // 弹框询问用户是否删除数据
      const confirmResult = await this.$confirm('此操作将永久删除'+scope.row.name+'分类, 是否继续?', '警告', {
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
      // console.log('确认了删除')
      await this.$axios.delete(`${this.HOST}/released/classify/${scope.row.classifyId}`, {
        id: scope.row.classifyId
      }).then((res) => {
        console.log('removeCateById', res)
        if (res.data.code == 200) {
          this.$message.success('删除分类成功')
          this.getCateList()
        } else {
          this.$message.info('删除分类失败')
        }
      }).catch((res) => {
        this.$message.error('删除分类请求失败')
        console.log('removeCateByIdError', res)
      })
    },
    //分类信息
    showEditDialog(cateInfo){
      this.editForm = cateInfo
      console.log('cateInfo',this.editForm)
      this.editDialogVisible = true
    },
  //  修改分类信息
    async editCateInfo(){
      console.log('editCateInfo',this.editForm)
      let parentId = this.editForm.parentId[0]
      await this.$axios.put(`${this.HOST}/released/classify`,{
          "classifyId": this.editForm.classifyId,
          "name": this.editForm.name,
          "parentId": parentId,
          "sonId": this.editForm.sonId,
          "path": this.editForm.path
      }).then((res) =>{
        console.log('editCateInfoRes',res)
        if(res.data.code==200){
          this.$message.success('修改分类成功')
          this.editDialogVisible=false
          this.getCateList()
        }else{
          this.$message.info('修改分类失败')
        }
      }).catch((res)=>{
        console.log('editCateInfoError',res)
        this.$message.error('修改分类请求失败')
      })
    },
    // 监听修改用户对话框的关闭事件
    editDialogClosed() {
      this.$refs.editFormRef.resetFields();
    },
  },
};
</script>
<style lang="less" scoped>
.treeTable {
  margin-top: 15px;
}

.el-cascader {
  width: 100%;
}
</style>
