<template>
  <div class="goodsDetail">
    <el-card style="margin-left:100px;
    margin-top:30px; margin-right:100px">
    <!--      上面简介-->
    <div class="block">
      <!--      轮播图-->
      <div>
        <!--          <el-carousel-item v-for="item in imgs">-->
        <!--            <el-image style="height: 300px; width: 600px" fit="fit"></el-image>-->
        <!--          </el-carousel-item>-->
        <el-image :src="imgs" style="margin: 20px;width: 300px;height: 300px" fit="fit"></el-image>
      </div>
      <!--       价格、介绍 -->
      <div class="introduction">
        <el-card style="height:330px; width:350px;">
          <p>
          <span
              style="
              font-size: 25px;
              font-weight: bolder;
            "
          >{{ goodsName }}</span>
        </p>
        <br/>
          <div>
            <div>
              <span style="font-size: large;color: #9E9E9E">价格：</span>
              <span
                  style="
                color: #ff4400;
                font-size: x-large;
                font-weight: bolder;
              "
              >￥{{ price }}</span
              >
            </div>
            <div
                style="
              font-size: large;
              margin: 0 auto;"
            >
              <span style="white-space: nowrap;color: #9E9E9E">校区：</span>
              <span
                  style="font-weight: bold; white-space: nowrap"
              >{{ campus === 1 ? "成龙" : "狮子山" }}</span
              >
            </div>
            <h2>库存数量：{{ goodNum }}</h2>

          </div>
          <div style="width: 100%">
            <p
                style="
              display: flex;
              flex-direction: row;
              justify-content: space-between;
            "
            >
            <div>
              <i class="iconfont barter-kanguo">{{ viewCount }}</i>
              <el-button type="text" @click="AddToCart" :disabled="isUserValid||isAddToCart==true">
                <i
                    v-if="isAddToCart==false"
                    class="iconfont barter-gouwuchekong"
                    preventReClick
                ></i
                >
                <i v-else-if="isAddToCart==true"  class="iconfont barter-gouwucheman1"></i>
              </el-button>
            </div>
          </div>
        </el-card>
      </div>
      <el-divider direction="vertical"></el-divider>
      <!--        卖主介绍-->
      <div class="saler">
        <el-image
            :src="salerImag"
            style="width: 100px; height: 100px; margin-bottom: 10px; margin-left: 10px"
        ></el-image>
        <div style="margin-top: 20px">
          <div style="margin: 20px; width: 100%">{{ salerName }}</div>
          <div style="margin: 20px; width: 100%">
            <span style="margin-right: 10px">信用积分</span>
            <span style="color: red; font-size: large">{{ salerCredit }}</span>
            <p style="margin-top: 20px" v-if="salerCredit >= 80">
              <i class="iconfont barter-gaozhiliang_tongyong"></i>
              <i class="iconfont barter-gaozhiliang_tongyong"></i>
              <i class="iconfont barter-gaozhiliang_tongyong"></i>
            </p>
            <p style="margin-top: 20px" v-else-if="salerCredit >= 50">
              <i class="iconfont barter-gaozhiliang_tongyong"></i>
              <i class="iconfont barter-gaozhiliang_tongyong"></i>
            </p>
          </div>
          <div style="width: 200px">
            <i class="el-icon-phone">{{ salerPhone }}</i>
            <br>
            <i class="el-icon-message">{{ salerEmail }}</i>
          </div>
          <br>
          <router-link :to="Calpathchat('chat')">
            <button style="margin-left:40px">联系他</button>
          </router-link>
        </div>
      </div>
    </div>
    </el-card>
    <!--        同类商品-->
    <el-card style="height: 400px;margin-left:100px;
    margin-top:30px; margin-right:100px">
      <h3 style="text-align: left;
      background-color:#f4f4f4;
      height:25px;
      padding:15px
      ">同类商品</h3>
      <div
          style="
          display: flex;
          flex-direction: row;
          text-align: center;
        "
      >
        <el-link v-for="item in sameGoods" type="danger">
          <div class="locOfPop" :key="item.goodId" @click="toGoodsDetail(item)">
            <el-image
                style="width: 120px; height: 120px; margin: 15px"
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
    </el-card>
    <!--      商品详情-->
    <div style="padding-top: 20px;margin-left:100px;
    margin-top:30px; margin-right:100px">
      <el-tabs type="border-card" style="height:800px">
        <el-tab-pane label="商品详情">
          <h3>商品名称：{{goodsName}}</h3>
          <h3>商品描述：{{ goodDescribe }}</h3>
        </el-tab-pane>
        <el-tab-pane label="评论留言" style="height:1000px" :model='comment'>
          <img class="avatar" v-bind:src="userInfo.nickname" v-bind:alt="userInfo.username">
          <el-input
                placeholder="输入评论..."
                type="textarea"
                :rows="2"
                maxlength="100"
                show-word-limit
                style="width:700px;height:50px;margin-top:0px"
                v-model="comment.content"
              ></el-input>
              <el-button style="margin-left:80px" @click="submitComment()">发表评论</el-button>
              <el-divider></el-divider>
              <!-- <div v-for="item in commentsList" :key="item.userId" class="items">
                <img class="avatar" v-bind:src="item.nickname">
                <span class="user-name">{{item.username}}</span>
                <span class="user-time">{{item.createdTime}}</span>
                <div>{{item.content}}</div>
              </div> -->
              <!--    評論展示-->
<el-table :data="commentsList" style="border: 0px;margin-left:20px;height:300px" width="80%">
  <!-- <el-table-column prop="userId" width="150px" height="10px" align="center" :show-overflow-tooltip="true">
  </el-table-column> -->
<el-table-column width="120px" height="10px" align="center" :show-overflow-tooltip="true">【匿名用户】
  </el-table-column>
  <el-table-column  prop="content" width="600px" height="10px" align="left" :show-overflow-tooltip="true">
  </el-table-column>
  <el-table-column prop="createdTime" width="200px" height="10px" align="center"></el-table-column>
</el-table>
<!--  分页区域-->
<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
  :page-sizes="[10,20, 30, 50, 100]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
  :total="total">
</el-pagination>


        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
import {Calpath, Encrypt} from "@/utools/utils";

export default {
  data() {
    return {
      inputcomment:'',
      //拥有者ID
      ownerID: 3,
      //投放时间
      time: "",
      //浏览数
      viewCount: 0,
      //商品图片
      imgs: '',
      //收藏数
      collectionNum: 0,
      //商品ID
      goodId: 0,
      //  商品名称
      goodsName: "趣味粉彩陶瓷马克杯",
      //库存数量
      goodNum: 1,
      //商品描述
      goodDescribe: "",
      //商品状态
      goodFlag: 1,
      price: 53,
      campus: 1,
      userId: 13,
      //  买主头像
      salerImag: '',
      // 卖主昵称
      salerName: "易物",
      //  卖主信用积分
      salerCredit: 50,
      //买主电话
      salerPhone: "",
      //买主状态
      salerState: 1,
      //  类似商品
      sameGoods: [],
      //  买主邮箱
      salerEmail: '',
      //请求是否被加入购物车
      isAddToCart: false,
      userName: null,
      //  用户是否被锁定
      isUserValid: true,
      //分类ID，用于请求同类商品
      classifiedNum: 22,
      //评论内容
      comment:{
        content:'',
      },
      //评论商品id
      goodsId:'',
      commentsList:{
        content:'',
        createTime:'',
        userId:'',
      },
      pageNum: 1,
      pageSize:10,
      total:0,
        userInfo: {
            username:'',
            nickname:''
        },
        userId:window.sessionStorage.getItem('userId')
    };
  },
  methods: {
          // 获取用户信息
  async getUserInfor(){
      await this.$axios.get(`${this.HOST}/user/seller/${this.userId}`,{
          params:{
              id:this.userId
          }
      }).then(res =>{
          console.log('getUserInfor',res)
          if (res.data.code == 200) {
              // this.$message.success("获取成功");
              this.userInfo = res.data.data}
      }).catch(res=>{
      console.log('getUserInforError',res);
      // this.$message.error("获取失败");
      })
  },
    //发评论
    addMessage(e) {
      if (e.ctrlKey && e.keyCode === 13 && this.comment.length) {
        this.$store.commit('addMessage', this.comment);
        this.comment = '';
      }
    },
    // directives: {
    //   'scroll-bottom'(el) {
    //       //console.log(el.scrollTop);
    //       setTimeout(function () {
    //           el.scrollTop += 9999;
    //       }, 1)
    //   }
    // },
    //获取点击的物品ID
    getGoodsId() {
      // this.goodId = this.$route.query.goodId;
      this.goodId = window.sessionStorage.getItem('goodId')
      console.log("goodid", this.goodId)
    },
    //获取商品详细信息
    async getGoodsInfo() {
      await this.$axios
          .get(`${this.HOST}/goods/id/${this.goodId}`)
          .then((res) => {
            console.log("getGoodsInfo", res);
            if (res.data.code == 200) {
              // this.$message.success("商品详细信息获取成功");
              this.collectionNum = res.data.data.collectionNum;
              this.imgs = res.data.data.imgUrl;
              this.viewCount = res.data.data.viewCount;
              this.goodsName = res.data.data.goodName;
              this.price = res.data.data.goodPrice;
              this.time = res.data.data.createTime;
              this.goodDescribe = res.data.data.goodDescribe;
              this.ownerID = res.data.data.ownerId;
              this.goodNum = res.data.data.goodNum
              this.classifiedNum = res.data.data.classifiedNum
              this.goodFlag = res.data.data.goodFlag
              // this.ownerID = res.data.data.ownerId
              console.log('ownerId', this.ownerID)
              this.getOwnerInfo();
              this.queryIsAddtoCart()
              if (this.goodFlag != 0) {
                this.isUserValid = true
                this.$message.info('商品正在审核中')
              }
            } else {
              this.$message.info("商品详细信息获取失败");
            }
          })
          .catch((res) => {
            this.$message.error("商品详细信息请求失败！");
            console.log("getGoodsInfoError", res);
          });
    },
    //获取商品拥有者的信息
    async getOwnerInfo() {
      console.log("ownerId", this.ownerID);
      await this.$axios
          .get(`${this.HOST}/user/seller/${this.ownerID}`, {
            params: {
              id: this.ownerID,
            },
          })
          .then((res) => {
            console.log("OwnerInfo", res);
            if (res.data.code == 200 && res.data.data != null) {
              // this.$message.success("商品拥有者信息获取成功");
              this.salerCredit = res.data.data.credit;
              this.salerImag = res.data.data.nickname;
              this.salerPhone = res.data.data.userPhone;
              this.campus = res.data.data.campus;
              this.salerState = res.data.data.userState
              this.salerEmail = res.data.data.email
              this.salerName = res.data.data.username
              //只有在商品销售并且用户账户课用得条件下才能加入购物车
              if (this.goodFlag == 0) {
                this.isUserValid = false
              }

            } else {
              this.$message.info("该商品买主已被锁定，请查看其他相似商品");
              this.isUserValid = true
            }
          })
          .catch((res) => {
            this.$message.error("商品拥有者信息请求失败");
            console.log("OwnerInfoError", res);
          });
    },
    //提交评论
    async submitComment(){
      await this.$axios.post(`${this.HOST}/comment`,{
        userId: window.sessionStorage.getItem('userId'),
        goodsId: this.goodId,
        content:this.comment.content,
      }).then((res)=>{
        console.log('submitComment',res)
      }).catch((res)=>{
        console.log('submitCommentError',res)
        this.$message.error('发表评论失败')
      }),
      this.comment.content='';
      this.$router.go(0)
    },
    //查看评论
    async getCommentList(){
      await this.$axios.get(`${this.HOST}/comment/page`,{
        params:{
          goodsId: this.goodId,
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        },
      }).then((res) => {
          console.log("commentList", res);
          if (res.data.code == 200) {
            // this.$message.success("评论获取成功");
            this.total = res.data.data.total;
            this.commentsList = res.data.data.list;
          } else {
            this.$message.info("评论获取失败！");
          }
        })
        .catch((res) => {
          this.$message.error("评论列表获取失败！");
        });
    },
    //计算路径
    Calpath(path) {
      return "/" + Encrypt(path);
    },
    Calpathchat(path) {
      return '/' + Encrypt(path);
    },
    // 监听pagesize该报事件
    handleSizeChange(newSize) {
      this.pageSize = newSize;
      this.getCommentList()
    },
    // 监听页面值改变的事件
    handleCurrentChange(newPage) {
      this.pageNum = newPage;
      this.getCommentList()
    },
    //  查询用户是否加入购物车
    async queryIsAddtoCart() {
      if (!this.userName) {
        return;
      }
      await this.$axios.get(`${this.HOST}/shoppingcart/good`, {
        params: {
          goodId: this.goodId,
          userId: window.sessionStorage.getItem('userId')
        }
      }).then((res) => {
        console.log('queryIsAddtoCart', res)
        if (res.data.message == '用户已将该商品加入购物车') {
          this.isAddToCart = true
        } else {
          this.isAddToCart = false
        }
      }).catch((res) => {
        console.log('queryIsAddtoCartError', res)
      })

    },
    //加入购物车
    async AddToCart() {
      if (this.userName == null) {
        this.$message.info('请先登录')
        window.sessionStorage.setItem('goodId', this.goodId)
        this.$router.push(Calpath('login'))
      } else {
        if (window.sessionStorage.getItem('userId') == this.ownerID) {
          this.$message.error('自己的商品不能加入购物车！')
          return;
        }
        await this.$axios.post(`${this.HOST}/shoppingcart`, {
          "goodNum": 1,
          "userId": window.sessionStorage.getItem('userId'),
          "goodId": this.goodId
        }).then((res) => {
          console.log('AddToCartRes', res)
          if (res.data.code == 200) {
            this.$message.success('添加购物车成功')
            this.isAddToCart = true
            this.queryIsAddtoCart()
          } else {
            this.$message.info('添加购物车失败')
          }
        }).catch((res) => {
          this.$message.error('添加购物车请求失败')
          console.log('AddToCartError', res)
        })
      }
    },
    //  获取相似商品
    async getSameGoods() {
      this.$axios.get(`${this.HOST}/goods/name`, {
        params: {
          name: this.goodsName.substring(0, -2),
          pageNum: 1,
          pageSize: 6
        }
      }).then((res) => {
        console.log('getSameGoods', res)
        if (res.data.code == 200) {
          // this.$message.success('获取同类商品成功')
          this.sameGoods = res.data.data.list
        } else {
          this.$message.info('获取同类商品失败')
        }
      }).catch((res) => {
        this.$message.error('请求同类商品失败')
        console.log('getSameGoodsError', res)
      })
    },
    //  去商品详情页面
    toGoodsDetail(item) {
      // this.$router.push({
      //   path: Calpath("goodsdetail"),
      //   query: { goodId: item.goodId },
      // });
      window.sessionStorage.setItem('goodId', item.goodId)
      this.$router.go(0)
    },
  },
  created() {
    this.getGoodsId();
    this.getGoodsInfo();
    this.queryIsAddtoCart();
    this.getSameGoods();
    this.userName = window.sessionStorage.getItem('userName')
    this.getCommentList();
    this.getUserInfor()
  },
  dateStr(date){
    //获取js 时间戳
    var time=new Date().getTime();
    //去掉 js 时间戳后三位，与php 时间戳保持一致
    time=parseInt((time-date)/1000);
    //存储转换值
    var s;
    if(time<60*10){//十分钟内
        return '刚刚';
    }else if((time<60*60)&&(time>=60*10)){
        //超过十分钟少于1小时
        s = Math.floor(time/60);
        return  s+"分钟前";
    }else if((time<60*60*24)&&(time>=60*60)){
        //超过1小时少于24小时
        s = Math.floor(time/60/60);
        return  s+"小时前";
    }else if((time<60*60*24*30)&&(time>=60*60*24)){
        //超过1天少于30天内
        s = Math.floor(time/60/60/24);
        return s+"天前";
    }else{
        //超过30天ddd
        var date= new Date(parseInt(date));
        return date.getFullYear()+"/"+(date.getMonth()+1)+"/"+date.getDate();
    }
  }
};
</script>

<style lang="less" scoped>
.el-main {
  padding: 0;
}

.goodsDetail {
  width: 100%;
  // background-color: red;
}

.block {
  padding-left: 50px;
  margin-right: 50px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.introduction {
  display: flex;
  flex-direction: column;
  width: 500px;
}

.el-divider--vertical {
  height: 28em;
  margin-left: 20px;
}

.avatar {
  padding-right:30px;
    width: 40px;
    height: 40px;
    // vertical-align: middle;
    /*图片和文字居中对齐*/
}

.saler {
  margin-left:50px;
  font-size: 15px;

  text-align: left;
  background-color: #ffffff;
}

.barter-gaozhiliang_tongyong {
  font-size: 30px;
  color: @yellow;
}

.barter-kanguo {
  font-size: 30px;
  margin-left: 50px;
  color: @green;
}

.barter-gouwucheman1 {
  font-size: 40px;
  margin-right: 50px;
  color: @orange;

}

.barter-gouwuchekong {
  font-size: 40px;
  margin-right: 50px;
  color: @orange;
}

.user-name{
  display: flex;
  margin-left: 50px;
  color: #000;
  font-size: 18px;
  font-weight: bold;
}

.user-time{
  display: flex;
  margin-left: 50px;
  font-size: 14px;
}

.comment{
  display: flex;
  background-color: red;
}

// #comment {
//   position: absolute;
//   bottom: 0;
//   right: 0;
//   width: 100%;
//   height: 30%;
//   border-top: solid 1px #DDD;

//   > textarea {
//     padding: 10px;
//     width: 100%;
//     height: 100%;
//     border: none;
//     outline: none;
//   }
// }
</style>
