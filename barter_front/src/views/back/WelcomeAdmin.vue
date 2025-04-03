<template>
  <div id="main">
<!--    <div ref="saleDataE" style="width:500px;height: 400px">-->
<!--    </div>-->
<!--    <div ref="release" style="width: 400px;height: 300px">-->

<!--    </div>-->
    <el-row :gutter="30" type="flex" justify="space-around">
      <el-col >
        <div ref="saleDataE" :style="{ height: '400px' ,width:'500px' }"></div>
      </el-col>
      <el-col >
        <div ref="release" :style="{ height: '400px' ,width:'400px' }"></div>
      </el-col>
    </el-row>
    <div ref="view" :style="{ height: '400px' ,width:'400px' }"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import Vue from "vue";
require('echarts/theme/vintage');
require('echarts/theme/fruit')
require('echarts/theme/bee-inspired')
require('echarts/theme/azul')
require('echarts/theme/london')
export default {
  data() {
    return {
      saleData: [],
      releaseAmount:[],
      viewCount:[ ],
      title: ['手机', '电脑', '乐器', '运动', '图书音像', '办公用品', '生活用品']
    }
  },
  mounted() {

  },
  methods: {
    async getSaleData() {
      await this.$axios.get(`${this.HOST}/view/order`).then((res) => {
        console.log('getSaleData', res)
        if (res.data.code == 200) {
          // this.$message.success('获取销量成功', res)
          this.saleData = res.data.data.slice(1, 9)
          console.log('saleData', this.saleData)
          this.drawSaleData()
        } else {
          this.$message.info('请求销量失败')
        }
      }).catch((res) => {
        this.$message.error('订单销量请求失败')
        console.log('getSaleDataError', res)
      })
    },
    async getReleaseAmount() {
      await this.$axios.get(`${this.HOST}/view/released`).then((res) => {
        console.log('getReleaseAmount', res)
        if (res.data.code == 200) {
          // this.$message.success('获取销量成功', res)
          this.releaseAmount = res.data.data.slice(1, 9)
          console.log('ReleaseAmount', this.releaseAmount)
          this.drawReleaseAmount()
        } else {
          this.$message.info('请求发布量失败')
        }
      }).catch((res) => {
        this.$message.error('发布量请求失败')
        console.log('getReleaseAmountError', res)
      })
    },
    async getViewCount(){
      await this.$axios.get(`${this.HOST}/view/view`).then((res) => {
        console.log('getSaleData', res)
        if (res.data.code == 200) {
          // this.$message.success('获取浏览量成功', res)
          let view = res.data.data.slice(1, 8)
          for (let i = 0; i < view.length; i++) {
            var obj = {}
            obj.value = view[i]
            obj.name  = this.title[i]
            this.viewCount.push(obj)
            // console.log('title',obj.name)
          }
          console.log('viewCount',this.viewCount)
          this.drawViewCount()
        } else {
          this.$message.info('请求浏览量失败')
        }
      }).catch((res) => {
        this.$message.error('浏览量请求失败')
        console.log('getViewCount', res)
      })
    },
    drawSaleData(){
      let myChart =echarts.init(this.$refs.saleDataE, 'london')
      // .init(this.$refs.myEcharts);
      // 绘制图表
      let option;
      option = {
        title: {

        },
        tooltip: {},
        legend: {
          data: ['金额']
        },
        xAxis: {
          data: this.title
        },
        yAxis: {},
        series: [{
          name: '金额',
          type: 'bar',
          data: this.saleData
          // data:[33265, 100, 0, 3727, 853, 500, 8000, 0]
        }]
      };
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },
    drawReleaseAmount(){
      let myChart =echarts.init(this.$refs.release, 'fruit')
      myChart.setOption({
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['近七日商品发布量']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },

        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ["1","2","3","4","5","6","7"]
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          name: '近七日商品发布量',
          type: 'line',
          stack: '总量',
          data: this.releaseAmount
        }]
      })
    },
    drawViewCount(){
      let myChart =echarts.init(this.$refs.view, 'bee-inspire')
      myChart.setOption({
        tooltip: {
          trigger: 'item',
        },
        legend: {
          orient: 'horizontal',
          x: 'right',
          // data:this.title
        },
        series: [
          {
            name:'浏览量',
            type:'pie',
            radius:['50%','70%'],
            avoidLabelOverlap: false,
            label: {
              normal: {
                show: false,
                position: 'center'
              },
              emphasis: {
                show: true,
                textStyle: {
                  fontSize: '30',
                  fontWeight: 'blod'
                }
              }
            },
            labelLine: {
              normal: {
                show: false
              }
            },
            data:this.viewCount
          }
        ]
      })
    }
  },

  created() {
    this.getSaleData()
    this.getReleaseAmount()
    this.getViewCount()
  }
}
</script>
<style lang="less" scoped>
.main{
  display: inline;
  flex-direction: row;

}
</style>
