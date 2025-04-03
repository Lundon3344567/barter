/**
 * @Description: 订单列表
 * @author Lion-Air14
 * @date 2021/12/8
 * @time 15:26
*/
<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="Calpath('AdminHome')">首页</el-breadcrumb-item>
      <el-breadcrumb-item>订单管理</el-breadcrumb-item>
      <el-breadcrumb-item>订单列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-table :data="orderList" border stripe width="100%">
<!--        <el-table-column label="卖主ID" prop="sellerId" align="center">-->
<!--        </el-table-column>-->
<!--        <el-table-column label="买主ID" prop="buyerId"  align="center">-->
<!--        </el-table-column>-->
        <el-table-column label="商品数量" prop="goodNum"  align="center" width="100px">
        </el-table-column>
        <el-table-column label="创建时间" prop="createTime"   align="center">
          <template slot-scope="creTime">
            <span >{{creTime.row.createTime.substring(0,19)}}</span>
          </template>
        </el-table-column>
        <el-table-column label="支付时间" prop="payTime"   align="center">
          <template slot-scope="isPay">
            <span v-if="isPay.row.payTime!=null">{{isPay.row.payTime.substring(0,19)}}</span>
            <span v-else>暂未支付</span>
          </template>
        </el-table-column>
        <el-table-column prop="payMoney" label="金额" width="100"  align="center">
        <template slot-scope="prices">
          <span>￥{{prices.row.payMoney}}</span>
        </template>
        </el-table-column>
        <el-table-column label="订单状态" width="180" prop="orderFlag"  align="center">
          <template slot-scope="prices">
            <el-tag v-if="prices.row.orderFlag==0" type="danger">提交订单未支付</el-tag>
            <el-tag v-else-if="prices.row.orderFlag==1" type="success">支付成功</el-tag>
            <el-tag v-else-if="prices.row.orderFlag==-1" type="info">订单超时</el-tag>
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

    <
  </div>

</template>

<script>
import {Encrypt} from "@/utools/utils";
// import echarts from 'echarts'
import * as echarts from 'echarts'
import vintage from 'echarts/theme/vintage'
export default {
  name: "order",
  data(){
    return{
      pageNum: 1,
      pageSize:10,
      total:4,
      orderList:[],
      step:2,

    }
  },
  methods:{
    async getOrders(){
      await this.$axios.get(`${this.HOST}/order/all`,{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize
        }
      }).then((res)=>{
        console.log('getOrders',res)
        this.orderList = res.data.data.list
        this.total = res.data.data.total
      }).catch((res)=>{
        console.log('getOrdersError',res)
      })
    },
    // 监听pagesize该报事件
    handleSizeChange(newSize) {
      this.pageSize = newSize;
      this.getOrders()
    },
    // 监听页面值改变的事件
    handleCurrentChange(newPage) {
      this.pageNum = newPage;
      this.getOrders()
    },
    //计算路径
    Calpath(path){
      return '/'+Encrypt(path)
    },
  },
  created() {
    this.getOrders()
  },


}
</script>

<style scoped>

#main {
  width: 50%;
  height: 400px;
  margin: 0 auto;
}
</style>
