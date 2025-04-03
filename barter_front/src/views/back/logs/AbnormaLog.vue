/**
 * @Description: 展示异常日志
 * @author Lion-Air14
 * @date 2021/12/8
 * @time 15:26
*/
<template>
  <div class="logs">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="Calpath('AdminHome')">首页</el-breadcrumb-item>
      <el-breadcrumb-item>日志</el-breadcrumb-item>
      <el-breadcrumb-item>异常日志</el-breadcrumb-item>
      <!-- 导出异常日志 -->
      <el-row style="float: right">
        <el-col>
          <el-button type="primary" size="mini" @click="downloadLog" style="margin-bottom: 10px">导出日志</el-button>
        </el-col>
      </el-row>

    </el-breadcrumb>

    <!-- 卡片视图 -->
    <el-card style="width: 100%;text-align: center">
      <el-table :data="logLists" border stripe>
        <el-table-column label="标识符" :show-overflow-tooltip="true" prop="excId" align="center"></el-table-column>
        <el-table-column label="操作用户名" prop="operateUserName" align="center"></el-table-column>
        <el-table-column label="异常名" :show-overflow-tooltip="true" prop="excName" align="center"></el-table-column>
<!--        <el-table-column label="异常具体信息" prop="excMes"></el-table-column>-->
        <el-table-column label="操作方法" :show-overflow-tooltip="true" prop="operateMethod" align="center"></el-table-column>
        <el-table-column label="操作时间" :show-overflow-tooltip="true" prop="operateTime" align="center"></el-table-column>

      </el-table>
      <!--  分页区域-->
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[ 10, 50, 100]"
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
    }
  },
  methods: {
    // 监听pagesize该报事件
    handleSizeChange(newSize) {
      this.pageSize = newSize;
      this.getExceptionLogLists()
    },
    // 监听页面值改变的事件
    handleCurrentChange(newPage) {
      this.pageNum = newPage;
      this.getExceptionLogLists()
    },
    async getExceptionLogLists() {
      await this.$axios.get(`${this.HOST}/logs/exception/page`, {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then((res) => {
        console.log('getExceptionLogLists', res)
        if (res.data.code == 200) {
          // this.$message.success('获取日志列表成功')
          this.logLists = res.data.data.list
          this.total = res.data.data.total
        }
      }).catch((res) => {
        console.log('getExceptionLogListsError', res)
      })
    },
    async downloadLog() {
      await this.$axios.get(`${this.HOST}/logs/operate/file`,{
        responseType:'blob'
      }).then((res) => {
        console.log('downloadFile', res)
        fileDownload(res.data,'异常日志.xls')
      }).catch((res) => {
        console.log('downloadFileError', res)
      })
    },
    //计算路径
    Calpath(path) {
      return "/" + Encrypt(path);
    },
  },
  created() {
    this.getExceptionLogLists()
  }
}
</script>
<style lang="less" scoped>
.logs{
  width: 100%;
}
</style>
