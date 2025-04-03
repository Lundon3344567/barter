/**
 * @Description: 用户订单页面的倒计时组件
 * @author Lion-Air14
 * @date 2021/12/9
 * @time 19:05
*/
<template>
  <p style="font-size: 18px">{{time}}</p>
</template>
<script>
export default{
  data () {
    return {
      time : '',
      flag : false
    }
  },
  mounted () {
    let time = setInterval(() => {
      if (this.flag == true) {
        clearInterval(time);
      }
      this.timeDown();
    }, 500);
  },
  props : {
    endTime : {
      type: String
    }
  },
  methods : {
    timeDown () {

      const endTime = new Date(this.endTime);
      // console.log('endTime',endTime)
      const nowTime = new Date();
      // console.log('nowTime',nowTime)
      let leftTime = parseInt((endTime.getTime() - nowTime.getTime()) / 1000);
      // console.log('leftTime',leftTime)
      let d = parseInt(leftTime / (24 * 60 * 60));
      let h = this.formate(parseInt(leftTime / (60 * 60) % 24));
      let m = this.formate(parseInt(leftTime / 60 % 60));
      let s = this.formate(parseInt(leftTime % 60));
      if(leftTime <= 0){
        this.flag = true;
        this.$emit('time-end');
        this.time = '倒计时已完成';
      }else{
        this.time = `${m} 分 ${s} 秒`;    // 需要修改时间样式的话 ，比如需要什么30分钟倒计时，就只保留分和秒
      }
    },
    formate(time) {
      if (time >= 10) {
        return time;
      } else {
        return `0${time}`;
      }
    }
  }
}
</script>
<style scoped>
</style>
