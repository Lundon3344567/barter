/**
 * @Description: 消息通知
 * @author Lion-Air14
 * @date 2021/12/19
 * @time 17:02
*/
<template>
  <div>
    <div style="text-align: right">
      <el-button type="text" @click="deleteMessAll">一键删除已读</el-button>
    </div>
    <div v-for="item in meassList">
      <el-card>
        <span style="float: left">{{item.sendTime}}</span>
        {{item.sendInfo}}
        <el-button  v-if="item.chatterSendId==0" type="text" class="iconfont barter-weidu" style="float: right" @click="changeMess(item.chatId)"></el-button>
        <el-button v-else type="text" class="iconfont barter-yidu1" style="float: right" disabled></el-button>
        <el-button type="text" class="iconfont barter-shanchu" style="float: right" @click="deleteMess(item.chatId)"></el-button>
      </el-card>
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
</template>
<script>
// 安装并引入相关模块
export default {
  data() {
    return {
      meassList:[],
      pageNum:1,
      pageSize:5,
      total:0
    };
  },
  methods: {
    async getMeassList(){
      await this.$axios.get(`${this.HOST}/message`,{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          userId:window.sessionStorage.getItem('userId')
        }
      }).then((res)=>{
        console.log('getMeassListRes',res)
        if(res.data.code==200){
          this.meassList = res.data.data.list
          this.total = res.data.data.total
          // console.log('meassList',this.meassList)
        }
      }).catch((res)=>{
        console.log('getMeassListError',res)
      })
    },
    // 监听pagesize该报事件
    handleSizeChange(newSize) {
      this.pageSize = newSize;
      this.getMeassList()
    },
    // 监听页面值改变的事件
    handleCurrentChange(newPage) {
      this.pageNum = newPage;
      this.getMeassList()
    },
    async changeMess(id){
      await this.$axios.put(`${this.HOST}/message`,{
        chatId:id
      }).then((res)=>{
        console.log('changeMess',res)
        this.getMeassList()
      }).catch((res)=>{
        console.log('changeMessError',res)
      })
    },
    async deleteMess(id){
      console.log('id',id)
      await this.$axios.delete(`${this.HOST}/message/${id}`,{
        params:{
          id:id
        }
      }).then((res)=>{
        console.log('changeMess',res)
        if(res.data.code==200){
          this.getMeassList()
        }
      }).catch((res)=>{
        console.log('changeMessError',res)
      })
    },
    async deleteMessAll(){
      let id = window.sessionStorage.getItem('userId')
      await this.$axios.delete(`${this.HOST}/message/id/${id}`).then((res)=>{
        console.log('deleteMessAll',res)
        if(res.data.code==200){
          this.$message.success('删除已读信息成功')
          this.getMeassList()
        }
      }).catch((res)=>{
        console.log('deleteMessAllError',res)
      })
    }
  },
  created() {
    this.getMeassList()
  }
};
</script>
<style lang="less" scoped>
.el-card{
  margin: 5px;
}
.iconfont{
  font-size: 40px;
  margin: 5px;
}
.barter-weidu{
  color: red;
}
.barter-shanchu{
  color: @fontColorGrey;
}
</style>
