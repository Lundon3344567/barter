/**
 * @Description: 权限列表
 * @author Lion-Air14
 * @date 2021/12/8
 * @time 15:27
*/
<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="Calpath('AdminHome')">首页</el-breadcrumb-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      <el-breadcrumb-item>权限列表</el-breadcrumb-item>
      <!-- 添加权限按钮区域 -->
      <el-row style="float: right">
        <el-col>
          <kt-button label='添加权限' perms='admin:permission:save' type="primary" @click="addDialogVisible = true" style="margin-bottom: 10px"/>
        </el-col>
      </el-row>
    </el-breadcrumb>

    <!-- 卡片视图 -->
    <el-card>
      <tree-table
          class="treeTable"
          :data="treeRightList"
          :columns="columns"
          :selection-type="false"
          :expand-type="false"
          show-index
          index-text="#"
          border
          align="center"
          style="text-align: center"
      >
        <!--        操作-->
        <template slot="opt" slot-scope="scope" align="center">
          <kt-button label="编辑" perms="admin:permission:update" @click="showEditDialog(scope.row)" type="primary" icon="el-icon-edit"></kt-button>
          <kt-button label="删除" perms="admin:permission:delete" @click="removeRightById(scope.row.id,scope.row.name)" type="danger" icon="el-icon-delete"></kt-button>
        </template>
      </tree-table>
      <!--  分页区域-->
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pagenum"
          :page-sizes="[10,20, 30, 50, 100]"
          :page-size="pagesize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
      >
      </el-pagination>
    </el-card>
    <!-- 添加权限的对话框 -->
    <el-dialog
        title="添加权限"
        :visible.sync="addDialogVisible"
        width="50%"
        @close="addDialogClosed"
    >
      <!-- 内容主体区域 -->
      <el-form
          :model="addForm"
          :rules="addFormRules"
          ref="addFormRef"
          label-width="80px"
      >
        <el-form-item label="权限名称" prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-radio v-model="addForm.type" :label="0" disabled>目录</el-radio>
          <el-radio v-model="addForm.type" :label="1" disabled>菜单</el-radio>
          <el-radio v-model="addForm.type" :label="2" disabled>按钮</el-radio>
        </el-form-item>
        <el-form-item label="父级ID" prop="parentID">
          <el-cascader
              v-model="addForm.parentId"
              expand-trigger="click"
              :options="treeRightList"
              :props="{ value: 'id', label: 'title',children:'children'}"
              change-on-select
              clearable
              @change="handleChange"></el-cascader>
        </el-form-item>
        <el-form-item label="授权信息" prop="perms">
          <el-input v-model="addForm.perms" placeholder="users:test:test"></el-input>
        </el-form-item>
        <el-form-item label="组件名" prop="component">
          <el-input v-model="addForm.component"></el-input>
        </el-form-item>
        <el-form-item label="路径" prop="path">
          <el-input v-model="addForm.path" placeholder="/path"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="statu">
          <el-radio v-model="addForm.statu" :label="1">启用</el-radio>
          <el-radio v-model="addForm.statu" :label="0">禁用</el-radio>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addRight">确 定</el-button>
      </span>
    </el-dialog>
    <!--    修改权限对话框-->
    <el-dialog title="修改权限" :visible.sync="editRightDialogVisible" width="50%" @close="editRightDialogClosed">
      <el-form
          :model="editForm"
          :rules="editFormRules"
          ref="editFormRef"
          label-width="100px"
      >
        <el-form-item label="权限名称" prop="name">
          <el-input v-model="editForm.name" ></el-input>
        </el-form-item>
        <el-form-item label="权限名" prop="title">
          <el-input v-model="editForm.title" ></el-input>
        </el-form-item>
        <el-form-item label="权限路径" prop="path">
          <el-input v-model="editForm.path" ></el-input>
        </el-form-item>
        <el-form-item label="授权信息" prop="perms" >
          <el-input placeholder="users:test:test" v-model="editForm.perms" ></el-input>
        </el-form-item>
        <el-form-item label="权限图标" prop="icon">
          <el-input v-model="editForm.icon" ></el-input>
          <i :class="editForm.icon"></i>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="editRightDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editRight">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
import ktButton from "@/components/ktButton";
import {Decrypt,Encrypt,Calpath} from "@/utools/utils";
export default {
  data() {
    return {
      // 权限列表
      rightsList: [],
      treeRightList: [],
      pagesize: 10,
      pagenum: 1,
      total: 50,
      addDialogVisible: false,
      addForm: {
        name: '',
        type: 0,
        parentId: 0,
        path: '',
        perms: '',
        component: '',
        statu: 0,
      },
      currentPage: 1,
      addFormRules: {
        name: [
          {required: true, trigger: 'blur', message: '请输入权限名称'},
          {
            min: 3,
            max: 30,
            message: "权限名称在3-30",
            trigger: "blur",
          },
        ],
        type: [
          {required: true, trigger: 'blur', message: '请输入权限类型'}
        ],
        parentId: [
          {required: true, trigger: 'blur', message: '请输入父类ID'}
        ],
        path: [
          {required: true, trigger: 'blur', message: '请输入权限路径'},
          {
            min: 3,
            max: 30,
            message: "权限路径在3-30",
            trigger: "blur",
          },
        ],
        perms: [
          {required: true, trigger: 'blur', message: '请输入权限授权信息'},
          {
            min: 3,
            max: 30,
            message: "权限授权信息在3-30",
            trigger: "blur",
          },
        ],
        component: [
          {required: true, trigger: 'blur', message: '请输入组件名称'},
          {
            min: 3,
            max: 30,
            message: "组件名称长度在3-30",
            trigger: "blur",
          },
        ],
        statu: [
          {required: true, trigger: 'blur', message: '请输入权限状态'}
        ]
      },
      editForm: {},
      editFormRules: {
        name: [
          {required: true, trigger: 'blur', message: '请输入权限名称'},
          {
            min: 3,
            max: 30,
            message: "权限名称在3-30",
            trigger: "blur",
          },
        ],
        path: [
          {required: true, trigger: 'blur', message: '请输入权限路劲'},
          {
            min: 3,
            max: 30,
            message: "权限路劲在3-30",
            trigger: "blur",
          },
        ],
        perms: [
          {required: true, trigger: 'blur', message: '请输入权限授权信息'},
          {
            min: 3,
            max: 30,
            message: "权限授权信息在3-30",
            trigger: "blur",
          },
        ],
        title:[
          {required:true,trigger:'blur',message:'请输入权限名'}
        ]
      },
      editRightDialogVisible: false,
      prop: [{
        value: 'id',
        label: 'title',
        children: 'children'
      }],
      columns: [
        {
          label: "权限名称",
          prop: "title",
        },
        {
          label: "路径",
          //表示当前模板列的使用名称
          prop: "path",
        },
        {
          label: "操作",
          //  表示将当前列第一为模板列
          type: "template",
          //表示当前模板列的使用名称
          template: "opt",
        }
      ],
    }
  },
  components:{
    ktButton
  },
  created() {
    //   获取所有的权限
    this.getRightsList();
  },
  methods: {
    /**
     * @desc 获取权限列表
     * @returns {Promise<void>}
     */
    async getRightsList() {
     await this.$axios.get(`${this.HOST}/admin/permission/page`,{
       params:{
         pageNum:this.pagenum,
         pageSize:this.pagesize,
       }
     }).then((res) =>{
       console.log('rightsGet',res.data.data);
       this.rightsList=res.data.data.list
       this.total = res.data.data.total
     }).catch((res) =>{
       console.log('rightsGetError',res);
       this.$message.error('请求失败！')
     })
      /**
       * @desc 获取所有权限的数据
       */
      await this.$axios
          .get(`${this.HOST}/admin/permission/`)
          .then((res) => {
            if (res.data.code == 200) {
              this.treeRightList = res.data.data;
              console.log("treeRightList", this.treeRightList);
            }
          })
          .catch((res) => {
            console.log("SetRightError", res);
          });
    },
    /**
     * @desc 级联选择器改变parentId，动态实现权限的类型
     * @param value
     */
    handleChange(value){
      console.log('selectedParentId',value)
      if(this.addForm.parentId.length==2)
        this.addForm.type=1
      else if(this.addForm.parentId.length==3)
        this.addForm.type=2
    },
    /**
     * @desc 监听pagesize该报事件
     * @param newSize
     */
    handleSizeChange(newSize) {
      this.pagesize = newSize;
      this.getRightsList();
    },
    /**
     * @desc 监听页面值改变的事件
     * @param newPage
     */
    handleCurrentChange(newPage) {
      this.pagenum = newPage;
      this.getRightsList();
    },
    /**
     * @desc 计算路径
     * @param path
     * @returns {string}
     * @constructor
     */
    Calpath(path){
      return '/'+Encrypt(path)
    },
    /**
     * @desc 监听添加用户对话框的关闭事件
     */
    addDialogClosed() {
      this.$refs.addFormRef.resetFields();
    },
    /**
     * @desc 添加权限
     */
     addRight(){
      this.$refs.addFormRef.validate(async (valid) =>{
        let len = this.addForm.parentId.length
        let parentId = this.addForm.parentId[len-1]
        if(!valid)return;
        console.log('addForm',this.addForm)
        console.log('parentId',parentId)
        await this.$axios.post(`${this.HOST}/admin/permission`,{
          "name":this.addForm.name,
          "type":this.addForm.type,
          "parentId":parentId,
          "path":this.addForm.path,
          "perms":this.addForm.perms ,
          "component":this.addForm.component,
          "statu":this.addForm.status
        }).then((res)=>{
          console.log('addRight',res)
          if(res.data.code==200){
            this.$message.success('权限添加成功')
            this.addDialogVisible=false
            this.getRightsList()
          }else{
            this.$message.info('权限添加失败')
          }

        }).catch((res)=>{
          this.$message.error('权限添加请求失败！')
          console.log('addRightError',res)
        })
      })
    },
    /**
     * @desc 根据id删除对应的权限
     * @param id
     * @param name
     * @returns {Promise<ElMessageComponent>}
     */
    async removeRightById(id,name) {
      // 弹框询问用户是否删除数据
      const confirmResult = await this.$confirm('此操作将永久删除'+name+', 是否继续?', '警告', {
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
      await this.$axios.delete(`${this.HOST}/admin/permission/${id}`,{
        id:id
      }).then((res)=>{
        console.log('removeRightById',res)
        if(res.data.code==200){
          this.$message.success('删除权限成功')
          this.getRightsList()
        }else if(res.data.code==400){
          this.$message.info('请清理拥有该权限的用户后删除')
        }else{
          this.$message.info('删除失败')
        }
      }).catch((res)=>{
        console.log('removeRightByIdError',res)
        this.$message.error('删除权限请求失败！')
      })
     this.getRightsList()

    },
    /**
     * @desc 在展示对话框之前获取权限信息
     * @param info
     */
    showEditDialog(info) {
      console.log('editInfo',info)
      this.editForm =info
      this.editRightDialogVisible=true
    },
    //修改权限有问题
    /**
     * @desc 修改权限信息
     * @returns {Promise<void>}
     */
    async editRight(){
      await this.$axios.put(`${this.HOST}/admin/permission`,{
        component:this.editForm.component,
        created:this.editForm.created,
        ordernum:this.editForm.ordernum,
        parentId:this.editForm.parentId,
        id:this.editForm.id,
        path:this.editForm.path,
        perms:this.editForm.perms,
        type:this.editForm.type,
        icon:this.editForm.icon,
        name:this.editForm.name,
        status:this.editForm.status,
        updated:new Date().getDate()
      }).then((res)=>{
        console.log('editRight',res)
        if(res.data.code==200){
          this.$message.success('权限修改成功')
          this.editRightDialogVisible=false
        }else {
          this.$message.info('权限修改失败')
        }
      }).catch((res)=>{
        console.log('editRightError',res)
        this.$message.error('权限修改请求失败')
      })
    },
    /**
     * @desc 监听修改用户对话框的关闭事件
     */
    editRightDialogClosed() {
      this.$refs.editFormRef.resetFields();
    },
  },
};
</script>


<style lang="less" scoped>

</style>

