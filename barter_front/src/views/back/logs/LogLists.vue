/**
 * @Description: 日志列表
 * @author Lion-Air14
 * @date 2021/12/8
 * @time 15:26
*/
<template>
  <div class="logs">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="Calpath('AdminHome')">首页</el-breadcrumb-item>
      <el-breadcrumb-item>日志</el-breadcrumb-item>
      <el-breadcrumb-item>正常日志</el-breadcrumb-item>
      <el-select v-model="value" @change="getLogs">
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        >
        </el-option>
      </el-select>
      <el-button size="mini" style="float: right;margin-left: 5px" icon="el-icon-delete" type="warning" @click="removeLog">
      </el-button>
      <!-- 导出日志 -->
      <el-row style="float: right">
        <el-col>
          <el-button type="primary" size="mini" @click="downloadLog" style="margin-bottom: 10px">导出日志</el-button>
        </el-col>
      </el-row>
    </el-breadcrumb>

    <!-- 卡片视图 -->
    <el-card style="width: 100%;text-align: center">

      <el-table :data="logLists" border stripe>
        <el-table-column label="操作名称" prop="operateDesc" align="center"></el-table-column>
        <el-table-column align="center" width="100px" label="操作类型" prop="operateType"></el-table-column>
        <el-table-column label="操作人" prop="operateUserName" align="center"></el-table-column>
        <el-table-column label="操作时间" width="250px" prop="operateTime" align="center"></el-table-column>
        <el-table-column label="操作IP" prop="operateIp" align="center"></el-table-column>
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
  </div>
</template>
<script>
import fileDownload from 'js-file-download'
import {Encrypt} from "@/utools/utils";
export default {
  data() {
    return {
      logLists: {},
      pageNum: 1,
      pageSize: 10,
      total: 50,
      excelData: [],
      value:'查询',
      options: [{
        value: '查询',
        label: '查询'
      }, {
        value: '更新',
        label: '更新'
      }, {
        value: '删除',
        label: '删除'
      }],
    }
  },
  methods: {
    /**
     * @desc 监听pagesize该报事件
     * @param newSize
     */
    handleSizeChange(newSize) {
      this.pageSize = newSize;
      this.getLogLists()
    },
    /**
     * @desc 监听页面值改变的事件
     * @param newPage
     */
    handleCurrentChange(newPage) {
      this.pageNum = newPage;
      this.getLogLists()
    },
    async getLogLists() {
      await this.$axios.get(`${this.HOST}/logs/operate/page`, {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then((res) => {
        console.log('getLogLists', res)
        if (res.data.code == 200) {
          // this.$message.success('获取日志列表成功')
          this.logLists = res.data.data.list
          this.total = res.data.data.total
        }
      }).catch((res) => {
        console.log('getLogListsError', res)
      })
    },
    async getLogs(){
      await this.$axios.get(`${this.HOST}/logs/operate/condition`,{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          type:this.value
        }
      }).then((res)=>{
          console.log('getLogs',res)
        if(res.data.code==200){
          this.total = res.data.data.total
          this.logLists = res.data.data.list
        }
      }).catch((res)=>{
        console.log('getLogsError',res)
      })
    },
    async downloadLog() {
      await this.$axios.get(`${this.HOST}/logs/operate/file`,{
        responseType:'blob'
      }).then((res) => {
        console.log('downloadFile', res)
        fileDownload(res.data,'日志列表.xls')
      }).catch((res) => {
        console.log('downloadFileError', res)
      })
    },
    //计算路径
    Calpath(path) {
      return "/" + Encrypt(path);
    },
    async removeLog(){
      // 弹框询问用户是否删除数据
      const confirmResult = await this.$confirm(
          "此操作将永久删除所有的日志, 是否继续?",
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
      await this.$axios.delete(`${this.HOST}/logs/operate`,{
      }).then((res)=>{
        console.log('removeLog',res)
        if(res.data.code==200){
          this.$message.success('删除所有日志成功')
        }
      }).catch((res)=>{
        console.log('removeLogError',res)
        this.$message.error('删除所有日志失败')
      })
    }
  },
  created() {
    this.getLogs()
  }
}
</script>
<style lang="less" scoped>
.logs{
  width: 100%;
}
</style>
