/**
 * @Description: 角色列表
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
      <el-breadcrumb-item>角色列表</el-breadcrumb-item>
      <!-- 添加角色按钮区域 -->
      <el-row style="float: right">
        <el-col>
          <kt-button label="添加角色"
                     type="primary"
                     perms="admin:role:save"
                     @click="addDialogVisible = true"
                     ></kt-button>
        </el-col>
      </el-row>
    </el-breadcrumb>

    <!-- 卡片视图 -->
    <el-card>
      <!-- 角色列表区域 -->
      <el-table
          :data="rolelist"
          border
          stripe
          slot-scope="scoped"
      >
        <el-table-column
            label="角色名称"
            prop="name"
            width="150px"
            align="center"

        ></el-table-column>
        <el-table-column
            label="角色代码"
            prop="code"
            width="150px"
            align="center"
        ></el-table-column>
        <el-table-column label="用户状态" align="center">
          <template #default="scope">
            <el-switch
                active-value="1"
                inactive-value="0"
                v-model="scope.row.available"
                @change="roleStateChange(scope.row)"
            >
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="400px" align="center">
          <template slot-scope="scoped">
            <kt-button label="编辑" perms="admin:role:update"

                       icon="el-icon-edit"
                       @click="showEditDialog(scoped.row.id)">
            </kt-button>
            <kt-button label="删除" perms="admin:role:delete" type="danger"
                       icon="el-icon-delete"
                       @click="removeRoleById(scoped.row.id, scoped.row.name)">
            </kt-button>

            <kt-button perms="admin:role:perm" label="分配权限"
                       type="warning"
                       icon="el-icon-setting"
                       @click="showSetRightDialog(scoped.row)">
            </kt-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 添加角色的对话框 -->
    <el-dialog
        title="添加用户"
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
        <el-form-item label="角色名" prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="是否启用" prop="available">
          <template>
            <el-radio v-model="addForm.available" label="1">可用</el-radio>
            <el-radio v-model="addForm.available" label="0">不可用</el-radio>
          </template>
        </el-form-item>
        <el-form-item label="角色代码" prop="code">
          <el-input v-model="addForm.code"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addRole">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 分配权限的对话框 -->
    <el-dialog
        title="分配权限"
        :visible.sync="setRightDialogVisible"
        width="50%"
        @close="setRightDialogClosed"
    >
      <!-- 树形控件 -->
      <el-tree
          :data="rightslist"
          :props="treeProps"
          show-checkbox
          node-key="id"
          default-expand-all
          :default-checked-keys="defKeys"
          ref="treeRef"
      ></el-tree>
      <span slot="footer" class="dialog-footer">
        <el-button @click="setRightDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="allotRights">完 成</el-button>
      </span>
    </el-dialog>
    <!--    修改角色对话框-->
    <el-dialog
        title="修改角色"
        :visible.sync="editRoleDialogVisible"
        width="50%"
        @close="editRoleDialogClosed"
    >
      <el-form
          :model="editForm"
          :rules="editFormRules"
          ref="editFormRef"
          label-width="100px"
      >
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="角色代码" prop="code">
          <el-input v-model="editForm.code"></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="editRoleDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editRole">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
import {Decrypt, Encrypt, Calpath,array_difference,array_remove_repeat} from "@/utools/utils";

export default {
  data() {
    return {
      //控制添加对话框的显示与隐藏
      addDialogVisible: false,
      //添加用户表单
      addForm: {
        name: "",
        available: "0",
        code: "",
      },
      //添加表单规则
      addFormRules: {
        name: [
            {required: true, message: "请输入角色名", trigger: "blur"},
          {
            min: 3,
            max: 30,
            message: "角色名的长度在3-30",
            trigger: "blur",
          },
        ],
        code: [
          {
            required: true,
            message: "请输入角色代码",
            trigger: "blur",
          },
          {
            min: 3,
            max: 30,
            message: "角色代码的长度在3-30",
            trigger: "blur",
          },
        ],
        available: [
          {
            required: true,
            message: "请选择是否启用",
            trigger: "blur",
          },
        ],
      },
      // 所有角色列表数据
      rolelist: [],
      // 控制分配权限对话框的显示与隐藏
      setRightDialogVisible: false,
      // 所有权限的数据
      rightslist: [],
      // 树形控件的属性绑定对象
      treeProps: {
        label: "title",
        children: "children",
      },
      // 默认选中的节点Id值数组
      defKeys: [],
      // 当前即将分配权限的角色id
      roleId: "",
      //  控制修改角色对话框
      editRoleDialogVisible: false,
      //修改表单
      editForm: {
        available: 1,
        code: "",
        id: 1,
        name: "",
      },
      editFormRules: {
        code: [
          {
            required: true,
            message: "请输入代码",
            trigger: "blur",
          },
          {
            min: 3,
            max: 30,
            message: "角色代码的长度在3-30",
            trigger: "blur",
          },
        ],
        name: [
          {
            required: true,
            message: "请输入角色名称",
            trigger: "blur",
          },
          {
            min: 3,
            max: 30,
            message: "角色名的长度在3-10",
            trigger: "blur",
          },
        ],
      },
      rigthsList: [],
      selectedRole: [],
    };
  },
  created() {
    this.getRolesList();
  },
  methods: {
    /**
     *@desc:获取所有角色的列表
     * @returns {Promise<void>}
     */
    async getRolesList() {
      await this.$axios
          .get(`${this.HOST}/admin/role/`)
          .then((res) => {
            if (res.data.code == 200) {
              this.rolelist = res.data.data;
              // this.$message.success("角色列表获取成功");
              console.log("rolelist", this.rolelist);
            } else {
              this.$message.info("角色列表获取失败");
            }
          })
          .catch((res) => {
            this.$message.error("角色列表请求失败！");
          });

      // 获取所有权限的数据
      await this.$axios
          .get(`${this.HOST}/admin/permission/`)
          .then((res) => {
            if (res.data.code == 200) {
              console.log("SetRight", res);
              // 把获取到的权限数据保存到 data 中
              this.rightslist = res.data.data;
            }
          })
          .catch((res) => {
            console.log("SetRightError", res);
          });
      console.log('rightslist', this.rightslist);
    },
    /**
     * @desc:监听switch开关的状态
     * @param roleInfo
     * @returns {Promise<void>}
     */
    async roleStateChange(roleInfo) {
      console.log("roleStateInfo", roleInfo);
      await this.$axios
          .put(`${this.HOST}/admin/role/`, {
            available: roleInfo.available,
            code: roleInfo.code,
            id: roleInfo.id,
            name: roleInfo.name,
          })
          .then((res) => {
            console.log("roleStateChange", res);
            if (res.data.code == 200) {
              this.$message.success("更新角色状态成功");
            } else {
              this.$message.info("更新角色状态失败");
            }
            this.getRolesList();
          })
          .catch((res) => {
            console.log("roleResError", res);
            this.$message.error("更新用户状态请求失败！");
          });
      // this.$message.success("更新用户状态成功");
    },
    /**
     * @desc:根据Id删除对应的权限
     * @param role
     * @param rightId
     * @returns {Promise<ElMessageComponent>}
     */
    async removeRightById(role, rightId) {
      // 弹框提示用户是否要删除
      const confirmResult = await this.$confirm(
          "此操作将永久删除该文件, 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }
      ).catch((err) => err);

      if (confirmResult !== "confirm") {
        return this.$message.info("取消了删除！");
      }

      const {data: res} = await this.$http.delete(
          `roles/${role.id}/rights/${rightId}`
      );

      if (res.meta.status !== 200) {
        return this.$message.error("删除权限失败！");
      }

      // this.getRolesList()
      role.children = res.data;
    },
    /**
     * @desc:展示分配权限的对话框
     * @param role
     * @returns {Promise<void>}
     */
    async showSetRightDialog(role) {
      console.log("role", role);
      this.roleId = role.id;

      //获取角色拥有的权限
      await this.$axios.get(`${this.HOST}/admin/role/${this.roleId}/permission`, {
        params: {
          roleId: this.roleId
        }
      }).then((res) => {
        if(res.data.code==200){
          this.selectedRole = res.data.data
          console.log('selectedRoleRes', this.selectedRole)
        }else{
          this.$message.info('获取角色拥有权限失败')
        }
      }).catch((res) => {
        console.log('selectedRoleError', res)
        this.$message.error('请求角色拥有的权限失败')
      })
      // 递归获取三级节点的Id
      this.getLeafKeys(this.selectedRole, this.defKeys);
      // window.setTimeout(function (){
      this.setRightDialogVisible = true;
      // },500)
    },
    /**
     *@desc 通过递归的形式，获取角色下所有三级权限的id，并保存到 defKeys 数组中
     * @param node
     * @param arr
     */
     getLeafKeys(node, arr) {
      console.log('node1', node)
      console.log('arr1', arr)
       node.forEach(function (item) {
        if (item.children.length != 0) {
          // console.log(item)
          item.children.forEach(function (subItem) {
            if (subItem.children.length != 0) {
              // console.log(subItem)
              subItem.children.forEach(function (subItems) {
                arr.push(subItems.id)
              })
            } else {
              arr.push(subItem.id)
            }
          })
        } else {
          arr.push(item.id)
        }
      })
    },
    /**
     * @desc 监听分配权限对话框的关闭事件
     */
    setRightDialogClosed() {
      this.defKeys=[]
      this.$refs.treeRef.setCheckedKeys([])
      // console.log('setRightDialogClosed',this.defKeys)
    },
    /**
     * @desc 点击为角色分配权限
     * @returns {Promise<void>}
     */
    async allotRights() {
      // let keys = [
      //   ...this.$refs.treeRef.getCheckedKeys(),
      // ];
    // ...this.$refs.treeRef.getHalfCheckedKeys(),
    //   console.log('keys',keys)
    //   console.log('defKeys',this.defKeys)
    //   let res1 = array_difference(this.defKeys,keys)
    //   let res1=[1,2,4,5,9]
    //   console.log('res1', res1)
    //   keys = [
    //     ...this.$refs.treeRef.getCheckedKeys(),
    //     ...this.$refs.treeRef.getHalfCheckedKeys(),
    //   ];
    //   let res2 = array_difference(keys,this.defKeys)
      // let res2 = [2,4,5,9,10]
      let keys = [...this.$refs.treeRef.getCheckedKeys(), ...this.$refs.treeRef.getHalfCheckedKeys()]
      console.log('keys',keys)
      //删除权限
      // for (const item of res1) {
      //   await this.$axios.put(`${this.HOST}/admin/role/permission`,{
      //     "roleId":this.roleId,
      //     "permissionId":item,
      //     "option":false
      //   }).then((res)=>{
      //     console.log('selectchangeDelete',res)
      //   }).catch((res)=>{
      //     console.log('selectchangeError',res)
      //   })
      // }
      //添加权限
      // for (const item of res2) {
      //   await this.$axios.put(`${this.HOST}/admin/role/permission`,{
      //     "roleId":this.roleId,
      //     "permissionId":item,
      //     "option":true
      //   }).then((res)=>{
      //     console.log('selectchangeAdd',res)
      //   }).catch((res)=>{
      //     console.log('selectchangeError',res)
      //   })
      // }
      // this.setRightDialogVisible = false;
      // this.$router.go(0)
      // this.getRolesList()
      await this.$axios.put(`${this.HOST}/admin/role/permission`,{
        "roleId": this.roleId,
        "keys": keys
    }).then((res)=>{
        console.log('allotRightsRes',res)
        if(res.data.code==200){
          this.$message.success('修改角色权限成功')
          this.setRightDialogVisible = false
          this.$router.go(0)
        }else {
          this.$message.info('修改角色权限失败')
        }
      }).catch((res)=>{
        this.$message.error('修改角色权限请求失败')
        console.log('allotRightsError',res)
      })
    },
    /**
     * @desc 选中方法
     * @param key
     * @param node
     * @param isChild
     * @returns {Promise<void>}
     */
    async selectchange(key, node, isChild) {
      console.log('key', key)
      console.log('node', node)
      console.log('ischild', isChild)
      let keys1 = this.$refs.treeRef.getNode()
      // let keys1 = node.parent.parent.data
      console.log('keys1', keys1)

    },
    // selectCurrentChange(checkedNodes,checkedKeys){
    //   console.log('checkedNodes',checkedNodes)
    //   console.log('checkedKeys',checkedKeys)
    // },
    /**
     * @desc 监听添加用户对话框的关闭事件
     */
    addDialogClosed() {
      this.$refs.addFormRef.resetFields();
    },
    /**
     * @desc 点击按钮添加新角色
     */
    addRole() {
      console.log("availabel", this.addForm.available);
      this.$refs.addFormRef.validate(async (valid) => {
        if (!valid) return;
        await this.$axios
            .post(`${this.HOST}/admin/role/`, {
              name: this.addForm.name,
              available: this.addForm.available,
              code: this.addForm.code,
            })
            .then((res) => {
              console.log("addRole", res);
              if (res.data.code == 200) {
                this.$message.success("添加角色成功");
                this.addDialogVisible = false;
                this.getRolesList();
              } else {
                this.$message.info("添加角色失败！");
              }
            })
            .catch((res) => {
              console.log("addRoleErroe", res);
              this.$message.error("添加角色请求有误");
            });
      });
      // this.$message.success("添加角色成功");
      // 隐藏添加用户的对话框
      // this.addDialogVisible = false;
      // 重新获取用户的列表
      // this.getRolesList();
    },
    /**
     * @desc 计算路径
     * @param path
     * @returns {string}
     * @constructor
     */
    Calpath(path) {
      return "/" + Encrypt(path);
    },
    /**
     * @desc 展示修改对话框已有信息
     * @param id
     * @returns {Promise<void>}
     */
    async showEditDialog(id) {
      await this.$axios
          .get(`${this.HOST}/admin/role/${id}`)
          .then((res) => {
            console.log("editDiaRes", res);
            if (res.data.code == 200) {
              this.editForm = res.data.data;
              this.editRoleDialogVisible = true;
              // this.$message.success("角色信息获取成功");
            } else {
              this.$message.info("角色信息获取失败");
            }
          })
          .catch((res) => {
            this.$message.error("获取角色信息请求有误");
          });
    },
    /**
     * @desc 修改角色信息
     * @returns {Promise<void>}
     */
    async editRole() {
      await this.$axios
          .put(`${this.HOST}/admin/role/`, {
            id: this.editForm.id,
            code: this.editForm.code,
            name: this.editForm.name,
          })
          .then((res) => {
            // console.log('editRole',res)
            if (res.data.code == 200) {
              this.$message.success("角色信息修改成功");
              this.editRoleDialogVisible = false;
              this.getRolesList();
            } else {
              this.$message.info("角色信息修改失败！");
            }
          })
          .catch((res) => {
            this.$message.error("修改角色信息请求失败！");
            console.log("editRoelError", res);
          });
    },

    /**
     * @desc 清空修改对话框信息
     */
    editRoleDialogClosed() {
      this.$refs.editFormRef.resetFields();
    },

    /**
     * @desc 删除角色
     * @param id
     * @param name
     * @returns {Promise<ElMessageComponent>}
     */
    async removeRoleById(id, name) {
      // 弹框询问用户是否删除数据
      const confirmResult = await this.$confirm(
          "此操作将永久删除" + name + "角色, 是否继续?",
          "警告",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }
      ).catch((error) => error);
      // 如果用户点击确认则会返回字符串confirm
      // 如果用户点击取消则会返回字符串cancle
      // console.log(confirmResult)
      if (confirmResult !== "confirm") {
        return this.$message.info("已经取消删除");
      }
      await this.$axios
          .delete(`${this.HOST}/admin/role/${id}`, {
            params: {
              id: id,
            },
          })
          .then((res) => {
            // console.log('removeRoleById',res)
            if (res.data.code == 200) {
              this.$message.success("删除角色成功");
              this.getRolesList();
            } else {
              this.$message.info("删除角色失败");
            }
          })
          .catch((res) => {
            this.$message.error("删除角色请求失败！");
            console.log("removeRoleByIdError", res);
          });
    },

  },

};
</script>

<style lang="less" scoped>
.el-tag {
  margin: 7px;
}

.bdtop {
  border-top: 1px solid #eee;
}

.bdbottom {
  border-bottom: 1px solid #eee;
}

.vcenter {
  display: flex;
  align-items: center;
}
</style>
