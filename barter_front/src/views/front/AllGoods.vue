<template>
  <el-card class="goods" style="border-radius: 1%">
    <!--    广告轮播图-->
    <div class="block">
      <el-carousel :interval="4000" type="card" height="220px">
        <el-carousel-item v-for="item in adsList" :key="item.adId">
          <el-link :href="item.adUrl">
            <el-image class="imgs" :src="item.imgUrl"></el-image>
          </el-link>
        </el-carousel-item>
      </el-carousel>
    </div>
    <!--人气推荐区域-->
    <p class="title">
      <span class="titleName">人气推荐</span>
    </p>

    <div class="items">
      <div
        v-for="item in populaList"
        @click="toGoodsDetail(item)"
        :key="item.goodId"
      >
        <el-button
        >
          <div class="locOfPop">
            <el-image :src="item.imgUrl" ></el-image>
<!--            fit="scale-down"-->
            <div>
              <div class="itemName">
                {{ item.goodName }}
              </div>
              <span class="itemPrice">￥{{ item.goodPrice }}</span>
            </div>
          </div>
        </el-button>
      </div>
    </div>

    <!--    最新发布-->
    <p class="title">
      <span class="titleName">最新发布</span>
    </p>
    <div class="items">
      <div v-for="item in latestList"
      @click="toGoodsDetail(item)"
      :key="item.goodId">
        <el-button

        >
          <div class="locOfPop">
            <el-image :src="item.imgUrl"></el-image>
            <div>
              <div class="itemName">
                {{ item.goodName }}
              </div>
              <span class="itemPrice">￥{{ item.goodPrice }}</span>
            </div>
          </div>
        </el-button>
      </div>

    </div>

    <!--    教材头部-->
    <p class="title">
      <span class="titleName"
        >教材
        <el-tag effect="dark" color="#ee6c4d" style="border: none">
          {{ booksDecri }}</el-tag
        ></span
      >
    </p>
    <div class="items">
      <div
          v-for="item in booksList"
          :ref="item.link"
          :key="item.id"
          @click="toGoodsDetail(item)">
        <el-button

        >
          <div class="locOfPop">
            <el-image :src="item.imgUrl" fit="scale-down"></el-image>
            <div class="itemName">
              {{ item.goodName }}
              <div class="itemPrice">￥{{ item.goodPrice }}</div>
            </div>
          </div>
        </el-button>
      </div>
    </div>

    <!--    工具类区域-->
    <!--    工具类图书头部-->
<!--    <p class="title">-->
<!--      <span class="titleName"-->
<!--        >工具类图书-->
<!--        <el-tag effect="dark" color="red" style="border: none">{{-->
<!--          toolDecri-->
<!--        }}</el-tag>-->
<!--      </span>-->
<!--    </p>-->
    <!--    工具类图书列表-->
    <div class="items">
    <div v-for="item in toolBooks"
          :ref="item.link"
          @click="toGoodsDetail(item)"
          :key="item.id">
      <el-button>
        <div class="locOfPop">
          <el-image
            :src="item.imgUrl"
          ></el-image>
          <div class="itemName">
            {{ item.goodName }}
          </div>
          <div class="itemPrice">￥{{ item.goodPrice }}</div>
        </div>
      </el-button>
    </div>
    </div>

    <!--    手机区域-->
    <!--    手机头部-->
    <p class="title">
      <span class="titleName">手机
        <el-tag effect="dark" color="#ee6c4d" style="border: none">{{
            phoneDecri
          }}</el-tag>
      </span>
    </p>
      <!--    手机列表-->
      <div class="items">
          <div v-for="item in phoneList"
              ref="#"
              @click="toGoodsDetail(item)"
              :key="item.goodId">
            <el-button

            >
              <div class="locOfPop" >
                <el-image :src="item.imgUrl"></el-image>
                <div class="itemName">
                  {{ item.goodName }}
                <div class="itemPrice"
                >￥{{ item.goodPrice }}</div
                >
                </div>
              </div>
            </el-button>
          </div>

      </div>


    <!--    电脑区域-->
    <!--    电脑头部-->
    <p class="title">
      <span class="titleName">电脑
      <el-tag effect="dark" color="#ee6c4d" style="border: none">
          {{ computerDecri }}</el-tag></span>
    </p>
    <!--    电脑列表-->
    <div class="items">
        <div v-for="item in computerList"
            @click="toGoodsDetail(item)"
            :key="item.goodId">
        <el-button

        >
          <div class="locOfPop" >
            <el-image
                :src="item.imgUrl"
            ></el-image>
            <div class="itemName">

              {{ item.goodName }}
              <div class="itemPrice">
                ￥{{ item.goodPrice }}</div
              >
            </div>
          </div>
        </el-button>
      </div>
      </div>

    <!--    乐器区域-->
    <!--    乐器头部-->
      <p class="title">
        <span class="titleName">
          乐器
          <el-tag effect="dark" color="#ee6c4d" style="border: none">
          {{ MusicalInstrumentsDecri }}</el-tag>
        </span>
      </p>
    <!--    乐器列表-->
      <div class="items">
        <div v-for="item in MusicalInstruments"
            @click="toGoodsDetail(item)"
            :key="item.goodId">
          <el-button

          >
            <div class="locOfPop" >
              <el-image
                  :src="item.imgUrl"
              ></el-image>
              <div class="itemName">
                {{ item.goodName }}
                <div class="itemPrice"
                >￥{{ item.goodPrice }}</div
                >
              </div>
            </div>
          </el-button>
        </div>
      </div>
    </el-card>
</template>
<script>
import axios from "axios";
import { Calpath, Encrypt } from "@/utools/utils";

export default {
  data() {
    return {
      pageNum: 1,
      pageSize: 6,
      adsList: [],
      size:18,
      populaList: [],
      path: "goodsdetail",
      latestList: [],
      //手机介绍语
      phoneDecri: "最新二手潮货手机",
      //手机介绍图片
      phoneImgUrl: "",
      phoneList: [],
      computerList: [],
      //滇南介绍语
      computerDecri: "好电脑",
      computerImgUrl: "",
      booksList: [],
      booksImgUrl: "",
      //  图书音像介绍语
      booksDecri: "大学生读物，读你所想",
      //  工具类图书
      toolBooks: [],
      toolImgUrl: "",
      toolDecri: "工具类图书",
      //  乐器
      MusicalInstruments: [],
      MusicalInstrumentsImgUrl: "",
      MusicalInstrumentsDecri: "乐器~",
    };
  },
  methods: {
    //获取广告
    async getAds() {
      await this.$axios
        .get(`${this.HOST}/adadmin/ad/pages`, {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
          },
        })
        .then((res) => {
          console.log("AdsRes", res);
          console.log("Ads res.data.data", res.data.data.list);
          this.adsList = res.data.data.list;
          // console.log('adList',this.adsList)
        })
        .catch((res) => {
          this.$message.info("广告获取请求失败!");
        });
    },
    //  获取人气推荐
    async getPopularity() {
      await this.$axios
        .get(`${this.HOST}/goods/recommend`, {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
          },
        })
        .then((res) => {
          console.log("goods/recommend", res);
          if (res.data.code == 200) {
            this.populaList = res.data.data.list;
            console.log("populaList", this.populaList);
          } else {
            this.$message.info("推荐商品信息获取失败");
          }
        })
        .catch((res) => {
          this.$message.error("商品推荐请求失败！");
          console.log("ErrorRecommend", res);
        });
    },
    //计算路径
    Calpath(path) {
      return "/" + Encrypt(path);
    },
    //  获取最新发布
    async getLatestList() {
      await this.$axios
        .get(`${this.HOST}/goods/time`, {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
          },
        })
        .then((res) => {
          console.log("getLatestList", res);
          if (res.data.code == 200) {
            // this.$message.success("获取最新商品成功！");
            this.latestList = res.data.data.list;
          }
        })
        .catch((res) => {
          this.$message.error("获取最新商品请求失败！");
          console.log("getLatestListError", res);
        });
    },
    //  获取电脑列表
    async getComputerList() {
      await this.$axios
        .get(`${this.HOST}/released/classify/page`, {
          params: {
            parentId: 2,
            pageNum: 1,
            pageSize: this.size,
          },
        })
        .then((res) => {
          console.log("getComputerList", res);
          if (res.data.code == 200) {
            // this.$message.success("获取电脑列表成功");
            this.computerList = res.data.data.list;
            this.computerImgUrl = this.computerList[0].imgUrl;
          } else {
            this.$message.info("获取电脑列表失败！");
          }
        })
        .catch((res) => {
          console.log("getComputerListError", res);
          this.$message.error("获取电脑列表请求失败");
        });
    },
    //  获取手机列表
    async getPhoneList() {
      await this.$axios
        .get(`${this.HOST}/released/classify/page`, {
          params: {
            parentId: 1,
            pageNum: 1,
            pageSize: this.size,
          },
        })
        .then((res) => {
          console.log("getPhoneList", res);
          if (res.data.code == 200) {
            // this.$message.success("获取手机列表成功");
            this.phoneList = res.data.data.list;
            this.phoneImgUrl = this.phoneList[0].imgUrl;
            console.log("phoneImgUrl", this.phoneList);
          } else {
            this.$message.info("获取手机列表失败！");
          }
        })
        .catch((res) => {
          console.log("getPhoneListError", res);
          this.$message.error("获取手机列表请求失败");
        });
    },
    //  获取教材列表
    async getBooks() {
      await this.$axios
        .get(`${this.HOST}/released/classify/page`, {
          params: {
            parentId: 5,
            pageNum: 1,
            pageSize: this.size,
          },
        })
        .then((res) => {
          console.log("getBooks", res);
          if (res.data.code == 200) {
            // this.$message.success("获取书籍列表成功");
            this.booksList = res.data.data.list;
            this.booksImgUrl = this.booksList[0].imgUrl;
          } else {
            this.$message.info("获取书籍列表失败！");
          }
        })
        .catch((res) => {
          console.log("getBooksError", res);
          this.$message.error("获取书籍列表请求失败");
        });
    },
    //获取工具列图书
    async getToolBooks() {
      await this.$axios
        .get(`${this.HOST}/released/classify/page`, {
          params: {
            parentId: 44,
            pageNum: 1,
            pageSize: this.size,
          },
        })
        .then((res) => {
          console.log("getToolBooks", res);
          if (res.data.code == 200) {
            // this.$message.success("获取工具类书籍列表成功");
            this.toolBooks = res.data.data.list;
            this.toolImgUrl = this.toolBooks[0].imgUrl;
          } else {
            this.$message.info("获取工具类书籍列表失败！");
          }
        })
        .catch((res) => {
          console.log("getToolBooksError", res);
          this.$message.error("获取工具类书籍列表请求失败");
        });
    },
    //获取乐器
    async getMusicalInstruments() {
      await this.$axios
        .get(`${this.HOST}/released/classify/page`, {
          params: {
            parentId: 3,
            pageNum: 1,
            pageSize: this.size,
          },
        })
        .then((res) => {
          console.log("getMusicalInstruments", res);
          if (res.data.code == 200) {
            // this.$message.success("获取乐器列表成功");
            this.MusicalInstruments = res.data.data.list;
            this.MusicalInstrumentsImgUrl = this.MusicalInstruments[0].imgUrl;
          } else {
            this.$message.info("获取乐器列表失败！");
          }
        })
        .catch((res) => {
          console.log("getToolBooksError", res);
          this.$message.error("获取乐器请求失败");
        });
    },
    //  去商品详情页面
    toGoodsDetail(item) {
      // this.$router.push({
      //   path: Calpath("goodsdetail"),
      //   query: { goodId: item.goodId },
      // });
      window.sessionStorage.setItem("goodId", item.goodId);
      this.$router.push({
        path: Calpath("goodsdetail"),
      });
    },
  },
  created() {
    this.getAds(),
      this.getPopularity(),
      this.getLatestList(),
      this.getComputerList(),
      this.getPhoneList(),
      this.getBooks();
    // this.getToolBooks();
    this.getMusicalInstruments();
  },
};
</script>
<style scoped lang="less">
body{
  background-color: rgba(237, 238, 157, 0.2);
}
.goods {
  text-align: center;
  margin: 30px;

  // overflow: hidden;
  padding: 15px;
  // position: relative;
}
.block{
  // .el-link{
    // width: 200px;
    // height: 250px;
    // .el-image{
    //   width: 100%;
    //   height: 100%;
    // }
  // }
  border: #f4f4f4 3px solid;
}
.title {
  height: 30px;
  padding-top: 10px;
  padding-left: 15px;
  background-color:#faf6f0;
}
.items {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  //border-radius: 2%;
  border: #f4f4f4 3px solid;
  border-bottom-color:#f4f4f4;
	border-left:#98c1d9 15px solid;
  .el-button {
    border-radius: 10%;
    border: none;
    // background-color: antiquewhite;
  }
}
.titleName {
  float: left;
  display: inline;
  font-size: @font-size;
  font-weight: bold;
  font-family: @font;
}

.imgs{

    height: 100%;
    width: 100%;
    object-fit: cover;

}

.itemName {
  font-size: @font-size;
  color: @fontColorBlack;
  text-align: center;
  width: @widthOut;
  // height: @heigthOut;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
  font-family: @font;
  padding-top: 7px;
}
.itemPrice {
  .itemName;
  color: @red;
  font-family: @fontDigital;
  text-align: center;
  height: 70px;

}

.locOfPop {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: flex-end;
  text-align: center;
  height: @heigthOut;
  width: @widthOut;
  // background-color: @backgroundColorGrey;

  > .el-image {
    height: @heigthInside;
    width: @widthInside;
    padding-right: 15px;
  }
}
</style>
