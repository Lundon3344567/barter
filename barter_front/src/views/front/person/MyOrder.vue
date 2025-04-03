<template>
  <div>
    <div v-if="step==1">
      <h3>我的订单</h3>
      <el-select v-model="value" @change="getOthers" >
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-table :data="orderList">
        <el-table-column label="#" type="index">
        </el-table-column>
        <el-table-column prop="payMoney" label="金额" width="180" align="center">
          <template slot-scope="prices">
            <span>￥{{prices.row.payMoney}}</span>
          </template>
        </el-table-column>
        <el-table-column label="订单状态" width="180" prop="orderFlag" align="center">
          <template slot-scope="prices">
            <el-tag v-if="prices.row.orderFlag==0" type="danger">提交订单未支付
              <countdown :endTime='prices.row.lastEditTime'></countdown>
            </el-tag>
            <el-tag v-else-if="prices.row.orderFlag==1" type="success">支付成功</el-tag>
            <el-tag v-else-if="prices.row.orderFlag==-1" type="info">订单超时</el-tag>
          </template>
        </el-table-column>
<!--        <el-table-column label="支付时间" prop="payTime" align="center">-->
<!--          <template slot-scope="isPay">-->
<!--            <span v-if="isPay.row.payTime!=null">{{isPay.row.payTime.substring(0,19)}}</span>-->
<!--            <span v-else>暂未支付</span>-->
<!--          </template>-->
<!--        </el-table-column>-->
        <el-table-column label="创建时间" prop="createTime" width="155px" align="center">
          <template slot-scope="creTime">
            <span >{{creTime.row.createTime.substring(0,19)}}</span>
          </template>
        </el-table-column>
        <el-table-column label="商品数量" prop="goodNum" align="center"></el-table-column>
        <el-table-column label="可操作" width="150px">
          <template slot-scope="pay">
            <div v-if="pay.row.orderFlag==0" >
              <el-button size="mini" type="primary" @click="toSettle(pay.row)">去支付</el-button>
              <el-button size="mini" type="danger" style="margin: 2px" @click="CancelOrder(pay.row)">取消订单</el-button>

            </div>
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
    </div>

    <div v-else-if="step==2" align="center">
      <el-image :src="require('../../../assets/imgs/pay.jpg')"
                :preview-src-list="srcList"
                style="display: block;width: 200px;height: 200px"
      ></el-image>
      <h3 style="color: #1989fa">请使用支付宝扫码支付</h3>
      <div style="color: red">
        <span style="font-size: large">待支付金额：</span>
        <span style="font-size: xx-large">￥{{this.sum}}</span>
      </div>
      <div style="margin: 10px">
        <el-button @click="payed" type="warning">支付</el-button>
        <el-button @click="stepBack" type="primary">稍后支付</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import countdown from "@/components/countdown";
import {Decrypt} from "@/utools/utils";
export default {
  name: "myOrder",
  data(){
    return{
      pageNum:1,
      pageSize:10,
      total:10,
      orderList:[],
      step:1,
      srcList: [
        'https://fuss10.elemecdn.com/8/27/f01c15bb73e1ef3793e64e6b7bbccjpeg.jpeg'
      ],
      options:[
        {value: 2, label: '全部'},
        {value: 0, label: '订单未支付'},
        {value: 1, label: '成功支付'},
        {value: -1, label: '订单超时'},
      ],
      value:2,
      sum:0,
      orderId:'',
    }
  },
  methods:{
    /**
     * @desc 获取所有订单
     * @returns {Promise<void>}
     */
    async getOrders(){
      console.log('useId',Decrypt(window.sessionStorage.getItem('userId')))
      await this.$axios.get(`${this.HOST}/order`,{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          userId:(window.sessionStorage.getItem('userId'))
        }
      }).then((res)=>{
        console.log('getOrders',res)
        this.orderList = res.data.data.list
        this.total = res.data.data.total
      }).catch((res)=>{
        console.log('getOrdersError',res)
        this.$message.error('订单请求失败')
      })
    },
    /**
     * @desc 获取不同类型的订单
     * @returns {Promise<void>}
     */
    async getOthers(){
      // console.log('value',this.value)
      if(this.value==2){
        await this.getOrders()
        return;
      }
      await this.$axios.get(`${this.HOST}/order/user`,{
        params:{
          flag:this.value,
          pageSize:this.pageSize,
          pageNum:this.pageNum,
          userId:window.sessionStorage.getItem('userId')
        }
      }).then((res)=>{
        console.log('getOthers',res)
        if(res.data.code==200){
          this.$message.success('获取订单分类成功')
          this.orderList = res.data.data.list
          this.total = res.data.data.total
        }else{
          this.$message.info('获取订单分类失败')
        }
      }).catch((res)=>{
        this.$message.error('获取订单分类请求失败')
        console.log('getOthersError',res)
      })
    },
    /**
     * @desc 监听pagesize该报事件
     * @param newSize
     */
    handleSizeChange(newSize) {
      this.pageSize = newSize;
      this.getOthers()
    },
    /**
     * @desc 监听页面值改变的事件
     * @param newPage
     */
    handleCurrentChange(newPage) {
      this.pageNum = newPage;
      this.getOthers()
    },
    /**
     * @desc 取消订单
     * @param info
     * @returns {Promise<ElMessageComponent>}
     * @constructor
     */
    async CancelOrder(info){
      console.log('info',info)
      const confirmResult = await this.$confirm(
          "是否删除订单?",
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
      await this.$axios.put(`${this.HOST}/order`,{
        "id":info.id
      }).then((res) =>{
        console.log('CancelOrder',res)
        if(res.data.code==200){
          this.$message.success('成功取消订单')
          this.getOthers()
        }else{
          this.$message.info('取消订单失败')
        }
      }).catch((res)=>{
        console.log('CancelOrderError',res)
        this.$message.error('取消订单请求失败')
      })
    },
    /**
     * @desc 去结算页面
     * @param info
     */
    toSettle(info){
      this.sum=info.payMoney
      this.orderId = info.id
      this.step=2
    },
    /**
     * @desc 支付成功
     * @returns {Promise<void>}
     */
    async payed(){
      //  修改状态
      this.$axios.put(`${this.HOST}/order/id`,{
        id:this.orderId
      }).then((res)=>{
        console.log('payed',res)
        if(res.data.code==200){
          this.$message.success('订单支付成功')
        }else{
          this.$message.info('订单支付失败')
        }
      }).catch((res)=>{
        console.log('payedError',res)
        this.$message.error('订单支付请求失败')
      })
      this.step=1
      this.getOrders()
    },
    /**
     * @desc 返回事件
     */
    stepBack() {
      this.step = 1
      this.sum=0
    },
  },
  created() {
    this.getOrders()
  },
  components:{
    countdown
  }
}
</script>

<style scoped>

</style>
