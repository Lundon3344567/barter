<template>
  <div class="person" align="center">
    <div style="text-align: left;margin-left: 20px">
      <el-button v-model="one"  type="primary" :plain="select1" @click="allGet">正常</el-button>
<!--      <el-button v-model="one" type="primary" :plain= "select2" @click="unsold">未售出</el-button>-->
<!--      <el-button v-model="one" type="primary" :plain= "select3" @click="sold">已售出</el-button>-->
      <el-button v-model="one" type="primary" :plain= "select4" @click="audit">审核中</el-button>
      <el-button v-model="one" type="primary" :plain= "select5" @click="freeze">已冻结</el-button>
      <el-button v-model="one" type="primary" :plain= "select6" @click="auditNo">审核不通过</el-button>
    </div>
    <el-empty  v-if="isEmpty" description="空空如也~快去发布商品吧"></el-empty>
    <div v-else style="display: flex;flex-direction: row">
      <div v-for="item in goodsList" @click="toGoodsDetail(item)" style="margin: 10px">
        <el-link type="danger">
          <div class="locOfPop" :key="item.goodId">
<!--            <el-image-->
<!--                style="width: 150px; height: 150px"-->
<!--                :src="item.imgUrl"-->
<!--                fit="fit"-->
<!--            ></el-image>-->
            <img :src="item.imgUrl" style="width: 150px; height: 150px">
            <div style="text-align: center; width: 100%">
              <div
                  style="
                  font-size: medium;
                  font-family: 'You Yuan';
                  color: #8f918f;
                  text-align: center;
                  width: 100%;
                "
              >
                {{ item.goodName }}
              </div>
              <span style="font-size: medium; color: red; text-align: center"
              >￥{{ item.goodPrice }}</span>
            </div>
          </div>
        </el-link>
      </div>
    </div>
    <!--  分页区域-->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[1, 2, 5, 10]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        style="margin-top: 15px"
    >
    </el-pagination>
  </div>
</template>
<script>
import {Calpath, Decrypt} from "@/utools/utils";

  export default {
    data(){
      return{
          userId:'',
          type:0,
          pageNum:1,
          pageSize:5,
          goodsList:[
          ],
          total:1,
          isEmpty:1,
          one:'',
          select1:false,
          select2:true,
          select3:true,
          select4:true,
          select5:true,
          select6:true,
      }
    },
    methods: {
      //  获取用户的商品
      async getPersonalGoods() {
        await this.$axios.get(`${this.HOST}/goods/user`, {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            userId: this.userId,
            type: this.type
          }
        }).then((res) => {
          console.log('getPersonalGoods', res)
          if (res.data.code == 200) {
            // this.$message.success('获取个人商品成功')
            if (res.data.data.size == 0) {
              this.isEmpty = 1
            } else {
              this.isEmpty = 0
              this.goodsList = res.data.data.list
              this.total = res.data.data.total
            }
            // if(length(this.goodsList)==0)
            //   this.isEmpty=1
            // else
            //   this.isEmpty=0
            this.total = res.data.data.total
          }
        }).catch((res) => {
          console.log('getPersonalGoodsError', res)
        })
      },
      // 去商品详情页面
      toGoodsDetail(item) {
        // this.$router.push({
        //   path: Calpath("goodsdetail"),
        //   query: {goodId: item.goodId},
        // });
        window.sessionStorage.setItem('goodId',item.goodId)
        this.$router.push({
          path:Calpath("goodsdetail"),
        })
      },
      allGet(){
        this.type=0
        this.getPersonalGoods()
        this.select1=false
        this.select2 = this.select3=this.select4=this.select5=this.select6=true
      },
      unsold(){
        this.type=0
        this.getPersonalGoods()
        this.select2=false
        this.select1 = this.select3=this.select4=this.select5=this.select6=true
      },
      sold(){
        this.type=-1
        this.getPersonalGoods()
        this.select3=false
        this.select2 = this.select1=this.select4=this.select5=this.select6=true
      },
      audit(){
        this.type=1
        this.getPersonalGoods()
        this.select4=false
        this.select2 = this.select1=this.select3=this.select5=this.select6=true
      },
      freeze(){
        this.type=2
        this.getPersonalGoods()
        this.select5=false
        this.select2 = this.select3=this.select4=this.select1=this.select6=true
      },
      auditNo(){
        this.type=100
        this.getPersonalGoods()
        this.select6=false
        this.select2 = this.select3=this.select4=this.select5=this.select1=true
      },
      // 监听pagesize该报事件
      handleSizeChange(newSize) {
        this.pageSize = newSize;
        this.getPersonalGoods()
      },
      // 监听页面值改变的事件
      handleCurrentChange(newPage) {
        this.pageNum = newPage;
        this.getPersonalGoods()
      },
    },
    created() {
      this.userId = (window.sessionStorage.getItem('userId'))
      console.log('userId',this.userId)
      this.getPersonalGoods(1000)
    }
  }
</script>
<style lang="less" scoped>

</style>
