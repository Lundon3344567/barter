module.exports = {
  devServer: {
    port: 8083, //端口号
    open: true, //自动在浏览器上打开项目
    disableHostCheck:true,

    proxy: {
      "/api": {
        // http://127.0.0.1:8081
        // http://barter.free.idcfengye.com
        // target: "http://127.0.0.1:8081", //要跨域请求的地址url
        // target:"tcp://dh46534125.zicp.vip:56918",
        changeOrigin: true,
        ws: false,
        // ws: true,
        // target: "ws://localhost:8081",
        target: "http://127.0.0.1:8081",
        secure: false,
        withCredentials: true,
        pathRewrite: {
          "^/api": "", //重写的路径
        },
      },
    },

  },
  // configureWebpack: {
  //   plugins: [
  //     new webpack.ProvidePlugin({
  //       introJs: ['intro.js', 'introJs']
  //     })
  //   ]
  // },
  transpileDependencies: [
    'vue-echarts',
    'resize-detector'
  ],
  chainWebpack: config => {
    // config.module
    //     .rule('css')
    //     .test(/\.css$/)
    //     .oneOf('vue')
    //     .resourceQuery(/\?vue/)
    //     .use('px2rem')
    //     .loader('px2rem-loader')
    //     .options({
    //       remUnit: 50//设计稿的宽度的1/10
    //     })
    const oneOfsMap = config.module.rule("less").oneOfs.store;
    oneOfsMap.forEach(item => {
      item
          .use("style-resources-loader")
          .loader("style-resources-loader")
          .options({
            // or an array : ["./path/to/vars.less", "./path/to/mixins.less"] 这里的路径不能使用@，否则会报错
            patterns: "./src/assets/css/common.less"
          })
          .end()
    })
    //不同浏览器兼容问题
    config.entry('main').add('babel-polyfill')
  },

  // css: {
  //   loaderOptions: {
  //     css: {},
  //     postcss: {
  //       plugins: [
  //         require('postcss-px2rem')({
  //           remUnit: 37.5
  //         })
  //       ]
  //     }
  //   }
  //   }
  css: {
    loaderOptions: {
      postcss: {
        plugins: [
          require('postcss-px2rem')({
              remUnit: 60,
              exclude: /node_modules/
          })
        ]
      },
      scss: {
        prependData: `@import "~@/styles/mixin.scss";`
      }
    }
  }
};
