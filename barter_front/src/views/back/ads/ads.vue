/**
 * @Description:广告管理
 * @author Lion-Air14
 * @date 2021/12/8
 * @time 15:15
*/
<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="Calpath('AdminHome')">首页</el-breadcrumb-item>
      <el-breadcrumb-item>广告管理</el-breadcrumb-item>
      <el-breadcrumb-item>广告列表</el-breadcrumb-item>
      <el-row style="float: right;">
        <el-col >
          <kt-button
              perms="admin:ad:save"
              label="添加广告"
              @click="addDialogVisible = true"
              ></kt-button>
        </el-col>
      </el-row>
    </el-breadcrumb>


    <el-card>

      <!--    广告展示-->
      <el-table :data="adsList" border stripe>
        <el-table-column label="广告名称" prop="adName" align="center"> </el-table-column>
        <el-table-column label="上次修改时间" prop="createTime" align="center" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column label="广告有效期" prop="adValidity" align="center" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column label="广告状态" align="center">
          <!-- 是否有效 -->
          <!-- <el-button @click="test(scoped)">ceui</el-button> -->
          <template slot-scope="scope">
            <el-tag v-if="scope.row.adState==0">审核中</el-tag>
            <el-tag v-else-if="scope.row.adState==2" type="success">审核通过</el-tag>
            <el-tag v-else-if="scope.row.adState==3" type="success">已投放</el-tag>
            <el-tag v-else-if="scope.row.adState==1" type="warning">审核未通过</el-tag>
            <el-tag v-else-if="scope.row.adState==4" type="info">已过期</el-tag>
            <el-tag v-else-if="scope.row.adState==5" type="danger">已冻结</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180px" align="center">
          <template slot-scope="scoped">
            <!-- 修改按钮 -->
            <kt-button perms="admin:ad:update" icon="el-icon-edit"
                       size="mini"
                       @click="showEditDialog(scoped.row)"></kt-button>
            <!-- 删除按钮 -->
            <kt-button perms="admin:ad:delete" type="danger"
                       icon="el-icon-delete"
                       size="mini"
                       @click="removerAdsById(scoped.row.adId, scoped.row.adName)">
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

    <!-- 添加广告的对话框 -->
    <el-dialog
      title="添加广告"
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
        <el-form-item label="广告名称" prop="ad_name">
          <el-input v-model="addForm.ad_name"></el-input>
        </el-form-item>
        <el-form-item label="图片链接" prop="img_url">
          <el-input v-model="addForm.img_url"></el-input>
        </el-form-item>
        <el-form-item label="广告描述" prop="ad_describe">
          <el-input v-model="addForm.ad_describe"></el-input>
        </el-form-item>
        <el-form-item label="有效期" prop="ad_validity">
          <el-date-picker
              v-model="addForm.ad_validity"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="广告地址" prop="ad_url">
          <el-input v-model="addForm.ad_url"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addAds">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 修改广告的对话框 -->
    <el-dialog
      title="修改广告"
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
        <el-form-item label="广告名称" prop="adName">
          <el-input v-model="editForm.adName"></el-input>
        </el-form-item>
        <el-form-item label="广告有效期" prop="adValidity">
          <el-date-picker
              v-model="editForm.adValidity"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="广告图片" prop="imgUrl">
          <el-image :src="editForm.imgUrl"></el-image>
        </el-form-item>
        <el-form-item label="广告地址" prop="adUrl">
          <el-input v-model="editForm.adUrl"></el-input>
        </el-form-item>
        <el-form-item label="广告状态">
          <template>
            <el-radio v-model="editForm.adState"  :label="1">审核未通过</el-radio>
            <el-radio v-model="editForm.adState" :label="0">审核中</el-radio>
            <el-radio v-model="editForm.adState" :label="2">审核通过</el-radio>
            <el-radio v-model="editForm.adState" :label="3">投放</el-radio>
            <el-radio v-model="editForm.adState" :label="5">冻结</el-radio>
          </template>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editAds">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { Encrypt } from "@/utools/utils";

export default {
  name: "ads",
  data() {
    return {
      pageSize: 10,
      pageNum: 1,
      total: 20,
      //控制添加广告用户的对话框
      addDialogVisible: false,
      //控制修改广告的对话框
      editDialogVisible: false,
      adsList: [],
      addForm: {
        ad_name: "",
        img_url: "",
        ad_describe: "",
        ad_validity: "",
        ad_url: "",
      },
      addFormRules: {
        ad_name: [
          { required: true, trigger: "blur", message: "请输入广告名称" },
        ],
        img_url: [
          { required: true, trigger: "blur", message: "请输入广告图片链接" },
        ],
        ad_describe: [
          { required: true, trigger: "blur", message: "请输入广告描述" },
        ],
        ad_validity: [
          { required: true, trigger: "blur", message: "请输入广告有效期" },
        ],
        ad_url: [
          { required: true, trigger: "blur", message: "请输入广告地址" },
        ],
      },
      editForm: {},
      editFormRules: {
        adName: [
          { required: true, trigger: "blur", message: "请输入广告名称" },
        ],
        adValidity: [
          { required: true, trigger: "blur", message: "请输入广告有效期" },
        ],
        imgUrl: [
          { required: true, trigger: "blur", message: "请输入广告图片" },
        ],
        adUrl: [{ required: true, trigger: "blur", message: "请输入广告地址" }],
      },
    };
  },
  methods: {
    /**
     *@desc 分页获取广告
     * @returns {Promise<void>}
     */
    async getAdsList() {
      await this.$axios
        .get(`${this.HOST}/adadmin/ad/page`, {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
          },
        })
        .then((res) => {
          if (res.data.code == 200) {
            // this.$message.success("广告获取成功");
            console.log("ads", res.data);
            this.total = res.data.data.total;
            this.adsList = res.data.data.list;
          } else {
            this.$message.info("广告获取失败！");
          }
        })
        .catch((res) => {
          this.$message.error("广告列表获取失败！");
        });
    },
    /**
     * @desc 监听pagesize该报事件
     * @param newSize
     */
    handleSizeChange(newSize) {
      this.pageSize = newSize;
      this.getAdsList();
    },
    /**
     * @desc 监听页面值改变的事件
     * @param newPage
     */
    handleCurrentChange(newPage) {
      this.pageNum = newPage;
      this.getAdsList();
    },
    /**
     * @desc 监听添加用户对话框的关闭事件
     */
    addDialogClosed() {
      this.$refs.addFormRef.resetFields();
    },
    /**
     * @desc 添加广告
     * @returns {Promise<void>}
     */
    async addAds() {
      console.log('addAdsInfo',this.addForm)
      this.$refs.addFormRef.validate(async (valid) => {
        if (!valid) return;
        else {
          await this.$axios
            .post(`${this.HOST}/adadmin/ad`, {
              adName: this.addForm.ad_name,
              imgUrl: this.addForm.img_url,
              adDescribe: this.addForm.ad_describe,
              adUrl: this.addForm.ad_url,
              adValidity: this.addForm.ad_validity,
            })
            .then((res) => {
              console.log("addAds", res);
              if (res.data.code == 200) {
                this.addDialogVisible = false;
                this.$message.success("添加广告成功");
                this.getAdsList();
              } else {
                this.$message.info("添加广告失败！");
              }
            })
            .catch((res) => {
              this.$message.error("添加广告请求失败！");
            });
        }
      });
    },
    /**
     * @desc 监听修改用户对话框的关闭事件
     */
    editDialogClosed() {
      this.$refs.editFormRef.resetFields();
    },
    /**
     * @desc 修改广告
     * @returns {Promise<void>}
     */
    async editAds() {
      await this.$axios.put(`${this.HOST}/adadmin/ad`,{
        "adId": this.editForm.adId,
        "adName": this.editForm.adName,
        "imgUrl": this.editForm.imgUrl,
        "adDescribe": this.editForm.adDescribe,
        "adUrl": this.editForm.adUrl,
        "adValidity": this.editForm.adValidity,
        "adState":this.editForm.adState
      }).then((res)=>{
        console.log('editAds',res)
        if(res.data.code==200){
          this.$message.success('修改广告成功')
          this.editDialogVisible = false
          this.getAdsList()
        }else{
          this.$message.info('修改广告失败')
        }
      }).catch((res)=>{
        console.log('editAdsError',res)
      })
    },
    /**
     * @desc 展示编辑广告的对话框
     * @param info
     * @returns {Promise<void>}
     */
    async showEditDialog(info) {
      console.log('editInfo',info);
      // await this.$axios
      //   .get(`${this.HOST}/adadmin/ad/${id}`, {
      //     params: {
      //       id: id,
      //     },
      //   })
      //   .then((res) => {
      //     // console.log('editRes',res)
      //     if (res.data.code == 200) {
      //       this.$message.success("获取广告信息成功");
      //       this.editForm = res.data.data;
      //       this.editDialogVisible = true;
      //     } else {
      //       this.$message.info("获取广告信息失败！");
      //     }
      //   })
      //   .catch((res) => {
      //     console.log("editError", res);
      //     this.$message.error("获取广告信息请求失败!");
      //   });
      this.editForm = info
      this.editDialogVisible = true;
    },
    /**
     * @desc 根据id删除对应的广告信息
     * @param id
     * @param name
     * @returns {Promise<ElMessageComponent>}
     */
    async removerAdsById(id, name) {
      // console.log('name',name)
      // 弹框询问用户是否删除数据
      const confirmResult = await this.$confirm(
        "此操作将删除" + name + "，是否继续?",
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
      // console.log('确认了删除')
      await this.$axios
        .delete(`${this.HOST}/adadmin/ad/${id}`)
        .then((res) => {
          console.log("deleteAds", res);
          if (res.data.code == 200) {
            //更新广告列表
            this.getAdsList();
            this.$message.success("广告删除成功");
          } else {
            this.$message.info("删除广告失败！");
          }
        })
        .catch((res) => {
          this.$message.error("请求失败！");
        });
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

  },
  created() {
    this.getAdsList();
  },
};
</script>

<style scoped></style>
