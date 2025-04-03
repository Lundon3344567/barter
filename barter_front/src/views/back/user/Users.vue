/**
* @Description: 用户列表
* @Description: 用户列表
* @author Lion-Air14
* @date 2021/12/8
* @time 15:28
*/
<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="Calpath('AdminHome')">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图区域 -->
    <el-card>
      <!-- 搜索与添加区域 -->
      <el-row :gutter="20">
        <!--        用户名查询-->
        <el-col :span="4">
          <el-input
              placeholder="请输入用户名"
              v-model="queryInfo.name"
              clearable
          >
          </el-input>
        </el-col>
        <!--        电话查询-->
        <el-col :span="4">
          <el-input
              v-model="queryInfo.tel"
              placeholder="请输入电话"
              clearable
          >
          </el-input>
        </el-col>
        <!--        用户状态选择-->
        <el-col :span="4">
          <el-select v-model="queryInfo.state" placeholder="请选择用户状态">
            <el-option
                v-for="item in stateList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="2">
          <el-button @click="clearFilter" type="warning">清除筛选</el-button>
        </el-col>
<!--        <el-col :span="4" style="float: right">-->
<!--          <kt-button label="添加用户" perms="admin:user:save" @click="addDialogVisible = true" type="primary"-->
<!--                     size="normal"></kt-button>-->
<!--        </el-col>-->
      </el-row>
      <!-- 用户列表区域 -->
      <el-table :data="userlist" border stripe>
        <el-table-column label="姓名" prop="username" align="center"></el-table-column>
        <el-table-column label="邮箱" prop="email" width="170px" align="center"></el-table-column>
        <el-table-column label="用户头像" width="80px" align="center">
          <template slot-scope="scope">
            <el-image :src="scope.row.nickname" style="width: 50px;height: 50px"></el-image>
          </template>
        </el-table-column>
        <el-table-column label="电话" prop="userPhone" width="120px" align="center"></el-table-column>
        <el-table-column label="用户状态" width="180px" align="center">
          <template slot-scope="scope">
            <div v-if="scope.row.userState<=3">
              <el-switch
                  :active-value="1"
                  :inactive-value="2"
                  v-model=scope.row.userState
                  @change="userStateChange(scope.row)"
              >
              </el-switch>
              <el-tag v-if="scope.row.userState==1" size="mini">普通用户在用</el-tag>
              <el-tag v-else-if="scope.row.userState==2" size="mini" type="info">普通用户被锁</el-tag>
              <el-tag v-else-if="scope.row.userState==3" size="mini">普通用户已注销</el-tag>
            </div>
            <div v-else>
              <el-switch
                  :active-value="101"
                  :inactive-value="102"
                  v-model=scope.row.userState
                  @change="userStateChange(scope.row)"
              >
              </el-switch>

              <el-tag v-if="scope.row.userState==101" size="mini" type="danger">管理员用户在用</el-tag>
              <el-tag v-else-if="scope.row.userState==102" size="mini" type="danger">管理员用户被锁</el-tag>
              <el-tag v-else-if="scope.row.userState==103" size="mini" type="danger">管理员用户已注销</el-tag>

            </div>
          </template>
        </el-table-column>
        <el-table-column label="性别" prop="gender" align="center" width="50px">
          <template slot-scope="scoped">
            <el-tag size="mini" v-if="scoped.row.gender == 1">男</el-tag>
            <el-tag size="mini" type="danger" v-if="scoped.row.gender == 2">女</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="校区" prop="campus" width="100px" align="center">
          <template slot-scope="scoped">
            <el-tag size="mini" v-if="scoped.row.campus === 1">狮子山校区</el-tag>
            <el-tag size="mini" type="danger" v-if="scoped.row.campus === 2">成龙校区</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200px" align="center">
          <template slot-scope="scoped">
            <!-- 修改按钮 -->
            <kt-button perms="admin:user:update" @click="showEditDialog(scoped.row.userId)" icon="el-icon-edit"
                       type="primary" style="width: 45px;"></kt-button>
            <!-- 删除按钮 -->
            <kt-button perms="admin:user:delete" @click="removerUserById(scoped.row.userId)" icon="el-icon-delete"
                       type="danger" style="width: 45px;"></kt-button>
            <!-- 分配角色按钮 -->
            <el-tooltip
                content="分配角色"
                placement="top"
                effect="dark"
                :enterable="false"
            >
              <kt-button perms="admin:user:role" @click="showSetDialog(scoped.row)" icon="el-icon-setting"
                         type="warning" size="mini" style="width: 45px;"></kt-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <!--  分页区域-->
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryInfo.pagenum"
          :page-sizes="[10,20, 30, 50, 100]"
          :page-size="queryInfo.pagesize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
      >
      </el-pagination>
    </el-card>
    <!-- 添加用户的对话框 -->
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
          label-width="70px"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="addForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="addForm.password"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="addForm.email"></el-input>
        </el-form-item>
        <el-form-item label="手机" prop="mobile">
          <el-input v-model="addForm.mobile"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 修改用户的对话框 -->
    <el-dialog
        title="修改用户"
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
        <el-form-item label="用户名">
          <el-input v-model="editForm.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editForm.email"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="mobile">
          <el-input v-model="editForm.userPhone"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editUserInfo">确 定</el-button>
      </span>
    </el-dialog>

    <!--    分配角色的对话框-->
    <el-dialog
        title="分配角色"
        :visible.sync="setRoleDialogVisible"
        width="50%"
        @close="setRoleDialogClosed">
      <div>
        <el-checkbox-group v-model="checkList" v-for="item in rolesList">
          <el-checkbox :label="item.id">{{ item.name }}</el-checkbox>
        </el-checkbox-group>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="setRoleDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="saveRoleInfo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {Encrypt} from '@/utools/utils'
import testRules from "@/utools/testRules.json";

export default {
  data() {
    let checkUserName = (rule, value, callback) => {
      this.userNameUserFul()
      let regUserName = eval(testRules.userName.testRule)
      if (value == '') {
        callback(new Error(testRules.userName.messageEmpty))
      }
      if (!regUserName.test(value)) {
        return callback(new Error(testRules.userName.messageError)); // 有一个if 就要 一个 callback
      }
      return callback(); // 返回一个回调函数给验证，是表单validate生效
    };
    let validatePass = (rule, value, callback) => {
      let regPas = eval(testRules.password.testRule)
      if (value === '') {
        callback(new Error(testRules.password.messageEmpty))
      }
      if (!regPas.test(value)) {
        callback(new Error(testRules.password.messageError))
      }
      callback()
    };
    let checkEmail = (rule, value, callback) => {
      let regEmail = eval(testRules.email.testRule)
      if (value == '') {
        callback(new Error(testRules.email.messageEmpty))
      }
      if (!regEmail.test(value)) {
        return callback(new Error(testRules.email.messageError)); // 有一个if 就要 一个 callback
      }
      return callback(); // 返回一个回调函数给验证，是表单validate生效
    };
    let checkPhone = (rule, value, callback) => {
      let regPhone = eval(testRules.phone.testRule)
      if (value == '') {
        callback(new Error(testRules.phone.messageEmpty))
      }
      if (!regPhone.test(value)) {
        return callback(new Error(testRules.phone.messageError)); // 有一个if 就要 一个 callback
      }
      return callback(); // 返回一个回调函数给验证，是表单validate生效
    };
    return {
      adminHome: '/' + Encrypt('AdminHome'),
      //获取用户列表的参数对象
      queryInfo: {
        name: '',
        tel: '',
        state: '',
        // 当前页数
        pagenum: 1,
        // 当前每页显示多少条数据
        pagesize: 10,
      },
      stateList: [
        {value: 1, label: '普通用户在用'},
        {value: 2, label: '普通用户被锁'},
        {value: 3, label: '普通用户已注销'},
        {value: 101, label: '管理用账户在用'},
        {value: 102, label: '管理用账户被锁'},
        {value: 103, label: '管理用账户已注销'},
      ],
      userlist: [],
      total: 0,
      // 控制添加用户对话框的显示与隐藏
      addDialogVisible: false,
      // 添加用户的表单数据
      addForm: {
        username: "",
        password: "",
        email: "",
        mobile: "",
      },
      // 添加表单的验证归钻对象
      addFormRules: {
        username: [
          {
            validator: checkUserName,
            trigger: "blur",
            required: true,
          },
        ],
        password: [
          {
            validator: validatePass,
            trigger: "blur",
            required: true,
          },
        ],
        email: [
          {
            validator: checkEmail,
            trigger: "blur",
            required: true,
          },
        ],
        mobile: [
          {
            validator: checkPhone,
            trigger: "blur",
            required: true,
          },
        ],
      },
      // 控制修改用户对话框的显示与隐藏
      editDialogVisible: false,
      // 查询到的用户信息对象
      editForm: {},
      // 修改表单的验证归钻对象
      editFormRules: {
        email: [
          {
            validator: checkEmail,
            trigger: "blur",
            required: true,
          },
        ],
        userPhone: [
          {
            validator: checkPhone,
            trigger: "blur",
            required: true,
          },
        ],
      },
      //控制分配角色对话框的显示与隐藏
      setRoleDialogVisible: false,
      //需要被分配角色的用户信息
      userInfo: {},
      //所有角色的数据列表
      rolesList: [],
      editId: '',
      checkList: [],
    };
  }
  ,
  created() {
    this.getUserList();
  }
  ,
  methods: {
    async getUserList() {
      console.log('queryInfo', this.queryInfo);
      await this.$axios.get(`${this.HOST}/admin/user/condition`, {
        params: {
          "name": this.queryInfo.name,
          "requestPage": this.queryInfo.pagenum,
          "pageSize": this.queryInfo.pagesize,
          "state": this.queryInfo.state,
          "tel": this.queryInfo.tel,
        }
      }).then((res) => {
        console.log('getUserList', res)
        this.userlist = res.data.data.list
        this.total = res.data.data.total
      }).catch((res) => {
        console.log('getUserListError', res)
      })
    }
    ,
    async userNameUserFul() {
      if (!this.addForm.username) {
        this.cannotClick = true
        return;
      }
      await this.$axios.get(`${this.HOST}/user`, {
        params: {
          username: this.addForm.username
        }
      }).then((res) => {
        console.log('usernameUseful', res)
        if (res.data.code === 400) {
          this.$message.error('用户名已经存在')
          this.addForm.username = ''
        }
      }).catch((res) => {
        this.$message.error('请求失败！')
      })
    },
    // 监听pagesize该报事件
    handleSizeChange(newSize) {
      this.queryInfo.pagesize = newSize;
      this.getUserList();
    }
    ,
    // 监听页面值改变的事件
    handleCurrentChange(newPage) {
      this.queryInfo.pagenum = newPage;
      this.getUserList();
    }
    ,
    // 监听swithc开关的状态
    async userStateChange(userInfo) {
      console.log('userStateChange', userInfo)
      await this.$axios.put(`${this.HOST}/admin/user/state`, {
        "userId": userInfo.userId,
        "state": (userInfo.userState)
      }).then((res) => {
        console.log('userState', res)
        if (res.data.code == 200) {
          this.$message.success("更新用户状态成功");
        } else {
          this.$message.info("更新用户状态失败");
        }
        this.getUserList()
      }).catch((res) => {
        this.$message.error('更新用户状态请求失败！')
      })

    }
    ,
    // 监听添加用户对话框的关闭事件
    addDialogClosed() {
      this.$refs.addFormRef.resetFields();
    }
    ,
    // 点击按钮添加新用户
    addUser() {
      this.$refs.addFormRef.validate(async (valid) => {
        if (!valid) return;
        // 可以发起用户的网络请求
        await this.$axios.put(`${this.HOST}/admin/user`, {})

        this.$message.success("添加用户成功");
        // 隐藏添加用户的对话框
        this.addDialogVisible = false;
        // 重新获取用户的列表
        this.getUserList();
      });
    }
    ,
    // 展示编辑用户的对话框
    async showEditDialog(id) {
      this.editId = id
      console.log('editID', id);
      await this.$axios.get(`${this.HOST}/admin/user/id/${id}`).then((res) => {
        // console.log('editRes',res)
        this.editForm = res.data.data
        this.editDialogVisible = true;
      }).catch((res) => {
        this.$message.error('获取用户信息请求失败！')
      })

    }
    ,
    // 监听修改用户对话框的关闭事件
    editDialogClosed() {
      this.$refs.editFormRef.resetFields();
    }
    ,
    // 修改用户信息并提交
    async editUserInfo() {
      await this.$refs.editFormRef.validate(async valid => {
        if (!valid) return;
        // 发起修改用户信息的数据请求
        await this.$axios.put(`${this.HOST}/admin/user/`, {
          userId: this.editId,
          email: this.editForm.email,
          userPhone: this.editForm.userPhone
        }).then((res) => {
          console.log('editUserInfo', res)
          if (res.data.code == 200) {
            this.$message.success('修改用户信息成功')
          } else {
            this.$message.info('修改用户信息失败')
          }
        }).catch((res) => {
          console.log('editUserInfoError', res)
          this.$message.error('修改用户信息请求失败')
        })
        //关闭对话框
        this.editDialogVisible = false
        // 刷新数据列表
        await this.getUserList()

      });
    }
    ,
    // 根据id删除对应的用户信息
    async removerUserById(id) {
      // 弹框询问用户是否删除数据
      const confirmResult = await this.$confirm('此操作将永久删除该用户, 是否继续?', '警告', {
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
      await this.$axios.delete(`${this.HOST}/admin/user/id/${id}`, {
        params: {
          id: id
        }
      }).then((res) => {
        console.log('removerUserById', res)
        if (res.data.code == 200) {
          this.$message.success('删除用户成功')
          this.getUserList()
        } else {
          this.$message.info('删除用户失败')
        }
      }).catch((res) => {
        console.log('removerUserByIdError', res)
        this.$message.error('删除用户请求失败')
      })

    }
    ,
    //展示角色前获取所有的角色信息
    async showSetDialog(info) {
      if (info.userState <= 3) {
        this.$message.info('普通用户不用分配角色')
        this.setRoleDialogVisible = false
        return;
      }
      this.userInfo = info
      await this.$axios.get(`${this.HOST}/admin/role/`).then((res) => {
        // console.log('roles',res)
        if (res.data.code == 200) {
          this.$message.success('获取全部角色成功')
          this.rolesList = res.data.data
          this.setRoleDialogVisible = true
          console.log('rolesList', this.rolesList)
        } else {
          this.$message.info('获取全部角色失败')
        }
      }).catch((res) => {
        this.$message.error('获取全部角色请求失败')
        console.log('rolesError', res)
      })
      console.log('info', info)
      //   这里获取已有的角色ID
      await this.$axios.get(`${this.HOST}/admin/user/${info.userId}/role`, {
        params: {
          id: info.userId
        }
      }).then((res) => {
        console.log('checkecSetList', res)
        let check = []
        if (res.data.code == 200) {
          this.$message.success('获取用户的角色成功')
          if (res.data.data != null) {
            res.data.data.forEach(function (item) {
              check.push(item.id)
            })
            this.checkList = check
          } else {
            this.checkList = []
          }
        } else {
          this.$message.info('获取用户的角色失败')
        }
      }).catch((res) => {
        console.log('checkecSetListError', res)
        this.$message.error('获取用户的角色请求失败')
      })
    }
    ,
    //点击按钮分配角色
    async saveRoleInfo() {
      // if(this.checkList.length==0){
      //   return this.$message.error('请选择要分配放入角色')
      // }
      console.log('checkList', this.checkList)
      await this.$axios.put(`${this.HOST}/admin/user/role`, {
        "id": this.userInfo.userId,
        "roleIds": this.checkList
      }).then((res) => {
        console.log('saveRoleInfo', res)
        if (res.data.code == 200) {
          this.$message.success('分配角色成功！')
          this.setRoleDialogVisible = false
          this.getUserList()
        } else {
          this.$message.info('分配角色失败')
        }
      }).catch((res) => {
        console.log('saveRoleInfoError', res)
        this.$message.error('分配角色他请求失败')
      })

    }
    ,
    //监听分配角色对话框的关闭事件
    setRoleDialogClosed() {
      this.userInfo = {}
    }
    ,
    //计算路径
    Calpath(path) {
      return '/' + Encrypt(path)
    }
    ,
    /**
     * @desc:清除筛选条件
     */
    clearFilter() {
      this.queryInfo.name = ""
      this.queryInfo.tel = ""
      this.queryInfo.state = ""
    }
  }
  ,
  watch: {

    queryInfo: {
      handler: function () {
        this.getUserList()
      }
      ,
      deep: true,

    }
    ,

  }

}
;
</script>
<style lang="less" scoped></style>
