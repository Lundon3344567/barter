<template>
  <div class="classification">
    <!--    面包屑导航-->
    <el-breadcrumb separator=">">
      <el-breadcrumb-item :to="Calpath('allgoods')">首页</el-breadcrumb-item>
      <el-breadcrumb-item>{{ this.parentInfo.name }}</el-breadcrumb-item>
      <el-breadcrumb-item>{{ this.sonInfo.name }}</el-breadcrumb-item>
      <!--    排序-->
      <el-cascader
          v-model="value"
          :options="options"
          :props="{ expandTrigger: 'hover' }"
          @change="handleChange">
      </el-cascader>
    </el-breadcrumb>

    <!--    主体显示区域-->
    <div style="display: inline;margin-left: 50px;margin-top: 150px">
      <div v-for="item in goodsList" @click="toGoodsDetail(item)" style="margin: 20px;display: inline">
        <el-link type="danger">
          <div class="locOfPop" :key="item.classifyId">
            <el-image
                style="width: 150px;height: 150px;"
                :src="item.imgUrl"
                fit="fit"

            ></el-image>
            <div style="text-align: center;width: 100%">
              <div style="font-size: medium;font-family:'You Yuan';color: #8f9091;text-align: center;width: 150px;">
                {{ item.goodName }}
              </div>
              <span style="font-size: medium;color: red;text-align: center">￥{{ item.goodPrice }}</span>
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
    >
    </el-pagination>
  </div>
</template>
<script>
import axios from "axios";
import Utils from '../../utools/utils'
import {Calpath, Encrypt} from "@/utools/utils";

export default {
  data() {
    return {
      pageNum: 1,
      pageSize: 10,
      total: 10,
      parenId: 1,
      sonId: 18,
      classficationTree: [],
      activeName: '手机',
      sortType: 0,
      tiemType: 0,
      priceType: 0,
      viewType: 0,
      collectType: 0,
      parentInfo: {},
      sonInfo: {},
      goodsList: [],
      value: -1,
      options: [
        {value: -1, label: '综合'},
        {
          value: 1, label: '价格', children: [
            {value: 11, label: '价格从高到低'},
            {value: 12, label: '价格从低到高'},
          ]
        },
        {
          value: 3, label: '浏览量', children: [
            {value: 31, label: '浏览量从高到低'},
            {value: 32, label: '浏览量从低到高'},
          ]
        },
        {
          value: 2, label: '时间', children: [
            {value: 21, label: '最新'},
            {value: 22, label: '最久'},
          ]
        },
      ],
    }
  },
  methods: {
    //计算路径
    Calpath(path) {
      return '/' + Encrypt(path)
    },
    handleChange(info) {
      console.log('info', info)
      if(this.value.length==2)
        this.value = this.value[1]
      console.log('value',this.value)
      switch (this.value){
        case -1:this.getGoodsList();
        case 11:this.pricreSort(1);break;
        case 12:this.pricreSort(0);break;
        case 31:this.viewSort(1);break;
        case 32:this.viewSort(0);break;
        case 21:this.timeSort(1);break;
        case 22:this.timeSort(0);break;
      }
    },
    //  获取商品分类
    async getClassficationTree() {
      await this.$axios.get(`${this.HOST}/released/classify/goods`).then((res) => {
        console.log('getClassficationTree', res)
        if (res.data.code == 200) {
          // this.$message.success('商品分类信息获取成功')
          this.classficationTree = res.data.data

        } else {
          this.$message.info('获取商品分类失败！')
        }
      }).catch((res) => {
        this.$message.error('获取商品分类请求失败！')
        console.log('getClassficationTreeError', res)
      })
    },
    //根据id获取父类分类信息
    async getParentInfoByGoodId(id) {
      await this.$axios.get(`${this.HOST}/released/classify/id/${id}`).then((res) => {
        console.log('getInfoByGoodId', res.data.data)
        if (res.data.code == 200) {
          // this.$message.success('商品父类分类信息获取成功')
          this.parentInfo = res.data.data
          console.log(this.parentInfo)
        }
      }).catch((res) => {
        console.log('getInfoByGoodIdError', res)
        this.$message.error('商品父类分类信息请求失败！')
      })
    },
    //根据id获取子类分类信息
    async getSonInfoByGoodId(id) {
      await this.$axios.get(`${this.HOST}/released/classify/id/${id}`).then((res) => {
        console.log('getSonInfoByGoodId', res.data.data)
        if (res.data.code == 200) {
          // this.$message.success('商品子类分类信息获取成功')
          this.sonInfo = res.data.data
          console.log(this.parentInfo)
        }
      }).catch((res) => {
        console.log('getInfoByGoodIdError', res)
        this.$message.error('商品子类分类信息请求失败！')
      })
      this.getGoodsList()
    },
    //获取子分类商品
    async getGoodsList() {
      this.select1 = false
      this.select2 = this.select3 = this.select4 = this.select5 = true
      await this.$axios.get(`${this.HOST}/goods/classify`, {
        params: {
          id: this.sonId,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then((res) => {
        console.log('goodsInfo', res)
        if (res.data.code == 200) {
          // this.$message.success('获取子分类商品成功')
          this.goodsList = res.data.data.list
          this.total = res.data.data.total
          console.log('goodsList', this.goodsList)
        } else {
          this.$message.info('获取子分类商品失败')
        }
      }).catch((res) => {
        this.$message.error('获取子分类商品请求失败！')
        console.log('goodsInfoError', res)
      })

    },
    //按照时间排序
    async timeSort(type) {
      await this.$axios.get(`${this.HOST}/goods/id/name/type`, {
        params: {
          classifyId: this.sonId,
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          timeType: type
        }
      }).then((res) => {
        console.log('timeSort', res)
        if (res.data.code == 200) {
          // this.$message.success('按照时间排序获取商品成功')
          this.goodsList = res.data.data.list
          this.total = res.data.data.total
        }

      }).catch((res) => {
        console.log('timeSortError', res)
        this.$message.error('按照时间排序请求商品失败')
      })
      this.timeType = this.timeType == 1 ? 0 : 1
      console.log('timeType', this.timeType)

    },
    //  按照价格排序
    async pricreSort(type) {
      await this.$axios.get(`${this.HOST}/goods/id/name/type`, {
        params: {
          classifyId: this.sonId,
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          priceType: type
        }
      }).then((res) => {
        console.log('priceSort', res)
        if (res.data.code == 200) {
          // this.$message.success('按照价格排序获取商品成功')
          this.goodsList = res.data.data.list
          this.total = res.data.data.total
        }

      }).catch((res) => {
        console.log('priceSortError', res)
        this.$message.error('按照价格排序请求商品失败')
      })
      this.priceType = this.priceType == 1 ? 0 : 1
      console.log('priceType', this.priceType)

    },
    //  按照浏览量排序
    async viewSort(type) {
      await this.$axios.get(`${this.HOST}/goods/id/name/type`, {
        params: {
          classifyId: this.sonId,
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          viewCountType: type
        }
      }).then((res) => {
        console.log('viewSort', res)
        if (res.data.code == 200) {
          // this.$message.success('按照浏览量排序获取商品成功')
          this.goodsList = res.data.data.list
          this.total = res.data.data.total
        }

      }).catch((res) => {
        console.log('viewSortError', res)
        this.$message.error('按照浏览量排序请求商品失败')
      })
      this.viewCountType = this.viewCountType == 1 ? 0 : 1
      console.log('viewCountType', this.viewCountType)

    },
    //  按照收藏数排序
    async collectSort() {
      this.select5 = false
      this.select1 = this.select3 = this.select4 = this.select2 = true
      await this.$axios.get(`${this.HOST}/goods/id/name/type`, {
        params: {
          classifyId: this.sonId,
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          collectionType: this.collectionType
        }
      }).then((res) => {
        console.log('collectSort', res)
        if (res.data.code == 200) {
          // this.$message.success('按照时间排序获取商品成功')
          this.goodsList = res.data.data.list
          this.total = res.data.data.total
        }

      }).catch((res) => {
        console.log('collectSortError', res)
        this.$message.error('按照时间排序请求商品失败')
      })
      this.collectionType = this.collectionType == 1 ? 0 : 1
      console.log('collectionType', this.collectionType)

    },
    // 监听pagesize该报事件
    handleSizeChange(newSize) {
      this.pageSize = newSize;
      this.handleChange()
    },
    // 监听页面值改变的事件
    handleCurrentChange(newPage) {
      this.pageNum = newPage;
      this.handleChange()
    },
    //  去商品详情页面
    toGoodsDetail(item) {
      window.sessionStorage.setItem('goodId', item.goodId)
      this.$router.push({path: Calpath('goodsdetail')})
    }
  },
  created() {
    this.getClassficationTree()
    this.parenId = this.$route.query.info[0]
    this.sonId = this.$route.query.info[1]
    this.getParentInfoByGoodId(this.parenId)
    this.getSonInfoByGoodId(this.sonId)
  },
  mounted() {
    var that = this
    Utils.$on('test', function (param) {
      that.parenId = param[0]
      that.sonId = param[1]
      that.getParentInfoByGoodId(that.parenId)
      that.getSonInfoByGoodId(that.sonId)
      // console.log('parenId',that.parenId)
    })
  }
}

</script>
<style lang="less" scoped>
.classification {
  display: block;
  width: 100%;
  //background-color: #8f918f;
}

.headNav {
  width: 500px;
  display: flex;
  flex-direction: row;
  white-space: nowrap;
}
</style>
