<template>
  <div id="app">
    <div id="nav">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <router-view/>
    </div>

  </div>
</template>
<script>
export default {
  name:'App',
  provide(){
    return{

    }
  },
  data(){
    return{

    }
  },
  methods:{
      isDeviceMobile() {//判断函数 返回值true为移动端 false为PC端
        var u = navigator.userAgent
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1 //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/) //ios终端
        if (isiOS || isAndroid) {
          return true
        } else {
          return false
        }
        
},
  // mounted: function () {
  //   // 页面开始加载时修改font-size
  //   var html = document.getElementsByTagName("html")[0];
  //   var oWidth =
  //     document.body.clientWidth || document.documentElement.clientWidth;
  //   // 这里的1920是指设计图的大小,自己根据实际情况改变
  //   html.style.fontSize = (oWidth / 1920) * 100 + "px";
  //   console.log("rem:", html.style.fontSize);
  // },
}
}
</script>
<style lang="less">
@import url("./assets/css/common");
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  //background-color: rgba(255, 162, 0,0.2);
  /*min-width: 1000px;*/
}

</style>
