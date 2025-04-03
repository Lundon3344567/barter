<template>
    <div class="search">
      <!-- 面包屑导航区域 -->
      <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom: 30px;margin-left: 25px">
        <el-breadcrumb-item :to="Calpath('allgoods')">首页</el-breadcrumb-item>
        <el-breadcrumb-item>搜索</el-breadcrumb-item>
      </el-breadcrumb>

      <el-card>
      <div style="display: flex; flex-direction: row; margin-right: 30px; margin-left: 30px">
        <div v-for="item in sameGoods" @click="toGoodsDetail(item)">
          <el-link type="danger">
            <div  :key="item.goodId" style="margin: 10px">
              <el-image
                  style="width: 150px; height: 150px"
                  :src="item.imgUrl"
                  fit="fit"
              ></el-image>
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
                >￥{{ item.goodPrice }}</span
                >
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
          style="margin-top: 20px"
      >
      </el-pagination>
      </el-card>
    </div>
</template>
<script>
import {Calpath, Encrypt} from "@/utools/utils";
import {Debounce} from "@/utools/preventReClick";
export default {
  data(){
    return{
      searchInfo: '',
      pageNum:1,
      pageSize:5,
      total:10,
      sameGoods:[],
    }
  },
  methods:{
    //计算路径
    Calpath(path) {
      return "/" + Encrypt(path);
    },
    getSearchInfo(){
      this.searchInfo = this.$route.query.searchInfo
      console.log('searchInfo',this.searchInfo)
      Debounce(this.getSameGoods())
    },
     async getSameGoods(){
      await this.$axios.get(`${this.HOST}/goods/name`,{
        params:{
          name:this.searchInfo,
          pageNum:this.pageNum,
          pageSize:this.pageSize
        }
      }).then((res) =>{
        console.log('getSameGoods',res)
        if(res.data.code==200){
          this.$message.success('获取相似商品成功')
          this.total = res.data.data.total
          this.sameGoods = res.data.data.list
        }
      }).catch((res)=>{
        console.log('getSameGoodsError',res)
      })
    },
    //  去商品详情页面
    toGoodsDetail(item) {
      this.$router.push({
        path: Calpath("goodsdetail"),
        query: { goodId: item.goodId },
      });
    },
    // 监听pagesize该报事件
    handleSizeChange(newSize) {
      this.pageSize = newSize;
      this.getSameGoods()
    },
    // 监听页面值改变的事件
    handleCurrentChange(newPage) {
      this.pageNum = newPage;
      this.getSameGoods()
    },
  },
  created() {

    this.getSearchInfo()
  }
}
</script>
<style lang="less" scoped>

</style>
