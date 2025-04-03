<template>
  <div>
    <div  v-if="step==1">
      <h3>我的购物车</h3>
          <el-table :data="goods" @select="handleSum" @select-all="handleSum" ref="multipleTable">
            <!-- 复选框 -->
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="goodName" label="名称" width="180"></el-table-column>
            <el-table-column label="数量" width="280">
              <!-- 使用计数器来添加数量，绑定表格数据行对应的count -->
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.goodNum" @change="handlesum(scope.row)" :min="1" :max="10"></el-input-number>
              </template>
            </el-table-column>
            <el-table-column prop="price" label="单价" width="180">
              <template slot-scope="prices">
                <span>￥{{prices.row.goodPrice}}</span>
              </template>
            </el-table-column>
            <el-table-column label="金额" width="180">
              <template slot-scope="money">
                <span>￥{{money.row.goodPrice*money.row.goodNum}}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="100">
              <template slot-scope="scope">
                <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div style="margin: 20px;">
            总价：<span style="color: red;">￥{{sum}}</span>
            <el-button style="margin-left: 30px" :disabled="this.sum==0" type="primary" @click="toSettle">去结算</el-button>
          </div>
        <!--  分页区域-->
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5,10,20, 30, 50, 100]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
        >
      </el-pagination>
    </div>
    <div v-else-if="step==2" align="center" v-loading="loading">
<!--      <el-image :src="require('../../assets/pay.jpg')"-->
<!--                :preview-src-list="srcList"-->
<!--                style="display: block"-->
<!--      ></el-image>-->
<!--      <h3 style="color: #1989fa">请使用支付宝扫码支付</h3>-->
      <div style="color: red">
        <span style="font-size: large">待支付金额：</span>
        <span style="font-size: xx-large">￥{{this.sum}}</span>
      </div>
      <div style="margin: 10px">
        <el-button  @click="stepBack" type="danger" >返回</el-button>
        <el-button @click="placeOrder" type="warning">提交订单稍后支付</el-button>
      </div>
    </div>
  </div>
</template>
<script>
  import {Decrypt} from "@/utools/utils";
  import {throttle} from "echarts";

  export default {
    data(){
      return{
        step:1,
        pageNum:1,
        pageSize:5,
        total:10,
        sum: 0,
        // 设置的商品
        goods: [
        ],
        srcList: [

        ],
        order:[],
        loading:false
      }
    },
    methods: {
      async getCartInfo(){
        await this.$axios.get(`${this.HOST}/shoppingcart/page`,{
          params:{
            pageNum:this.pageNum,
            pageSize:this.pageSize,
            userId:(window.sessionStorage.getItem('userId'))
          }
        }).then((res) =>{
          console.log('getCartInfoRes',res)
          if(res.data.code==200){
            this.total = res.data.data.total
            this.goods = res.data.data.list
            console.log(this.goods)
          }
        }).catch((res) =>{
          console.log('getCartInfoError',res)
        })
      },
      // 计算总额
      handleSum() {
        this.sum = 0;
        this.$nextTick(() => {
          // console.log(this.$refs.multipleTable.selection);
          // this.$refs.multipleTable.selection获取被选中的行
          this.$refs.multipleTable.selection.forEach(item => {
            this.sum += (item.goodPrice * item.goodNum)
          });
        })
      },
      async handlesum(info){
        console.log(info)
        this.sum = 0;
        this.$nextTick(() => {
          // console.log(this.$refs.multipleTable.selection);
          // this.$refs.multipleTable.selection获取被选中的行
          this.$refs.multipleTable.selection.forEach(item => {
            this.sum += (item.goodPrice * item.goodNum)
          });
        })
       await this.$axios.put(`${this.HOST}/shoppingcart`,{
            goodId:info.goodId,
            goodNum:info.goodNum,
            userId:window.sessionStorage.getItem('userId')
       }).then((res) =>{
         console.log('handlesum',res)
         if(res.data.code==200){
           this.getCartInfo()
         }else if(res.data.message=='库存不足，无法购买更多该商品'){
           this.$message.info('库存不足，无法购买更多该商品')
           // info.$index.goodNum--
           info.goodNum=1
         }
       }).catch((res)=>{
         console.log('handlesumError',res)
       })
      },
      // 删除操作
      async handleDelete(info) {
        // this.handleSum();
        // console.log(info)
        // 弹框询问用户是否删除数据
        const confirmResult = await this.$confirm('是否删除购物车中的'+info.goodName+'?', '警告', {
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
        await this.$axios.delete(`${this.HOST}/shoppingcart`,{
         params:{
           "goodId":info.goodId,
           "userId":window.sessionStorage.getItem('userId')
         }
        }).then((res) =>{
          console.log('handleDelete',res)
          if(res.data.code==200){
            this.$message.success('从购物车中删除成功')
            this.getCartInfo()
          }else{
            this.$message.info('从购物车中删除失败')
          }
        }).catch((res) =>{
          this.$message.error('从购物车中删除请求失败')
          console.log('handleDeleteError',res)
        })
      },
      // 监听pagesize该报事件
      handleSizeChange(newSize) {
        this.pageSize = newSize;
        this.getCartInfo()
      },
      // 监听页面值改变的事件
      handleCurrentChange(newPage) {
        this.pageNum = newPage;
        this.getCartInfo()
      },
       async toSettle() {
         await this.$refs.multipleTable.selection.forEach(item => {
           console.log('item', item)
           var obj={}
           obj.buyerId = window.sessionStorage.getItem('userId')
           obj.goodNum = item.goodNum
           obj.goodId = item.goodId
           this.order.push(obj)
         })
         console.log('order', this.order)
         this.step = 2
       },
      placeOrder(){
        //防止反复请求
        throttle(this.pay())
      },
      //提交订单并未支付
       async pay(){
        this.loading=true
         await this.$axios.post(`${this.HOST}/order`,{
          "orderFromList":this.order
        }).then((res)=>{
          console.log('toSettle',res)
          if(res.data.code==200){
            this.$message.success('提交成功，可到我的订单支付')
            this.step=1
            this.getCartInfo()
            this.sum=0
            this.loading=false
          }else{
            this.$message.info('提交订单失败！')
          }
        }).catch((res)=>{
          console.log('toSettleError',res)
          this.$message.error('提交订单请求失败')
        })
        this.order=[]
        this.step=1
      },
      stepBack(){
        this.step=1
        this.sum = 0
        this.order=[]
      }
    },
    created() {
      this.getCartInfo()
    },

  }
</script>
<style lang="less" scoped>

</style>




