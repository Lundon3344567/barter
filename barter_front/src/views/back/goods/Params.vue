/**
 * @Description: 后台展示商品参数
 * @author Lion-Air14
 * @date 2021/12/8
 * @time 15:25
*/
<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/adminHome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>参数列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图区域 -->
    <el-card>
      <!-- 头部的警告区域 -->
      <el-alert
          title="注意：只允许第三级分类设置相关参数！"
          type="warning"
          :closable="false"
          show-icon
      >
      </el-alert>
      <!-- 选择商品分类区域 -->
      <el-row class="cat_opt">
        <el-col>
          <span>选择商品分类：</span>
          <!-- 选择商品分类的级联选择框 -->
          <el-cascader
              v-model="selectedCateKeys"
              :options="cateList"
              expand-trigger="hover"
              :props="cateProps"
              @change="handleChange"></el-cascader>
        </el-col>
      </el-row>

      <!--      tab页签区域-->
      <el-tabs v-model="activeName" @tab-click="handleTabClick">
        <!--        添加动态参数的面板-->
        <el-tab-pane label="动态参数" name="many">
          <el-button type="primary" size="mini" :disabled="isBtnDisabled">添加参数</el-button>
        </el-tab-pane>
        <!--        添加静态属性的面板-->
        <el-tab-pane label="静态属性" name="only">
          <el-button type="primary" size="mini" :disabled="isBtnDisabled">添加属性</el-button>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>
<script>
export default {
  data() {
    return {
      //商品分类列表
      cateList: [],
      //级联选择框的配置对象
      cateProps: {
        value: 'cat_id',
        label: 'cat_name',
        children: 'children'
      },
      //纪律选择框双向绑定到的数组
      selectedCateKeys: [],
      //被激活的页签名称
      activeName: 'many'
    };
  },
  created() {
    this.getCateList()
  },
  methods: {
    //获取所有商品分类列表
    async getCateList() {
      const {data: res} = await this.$http.get('categories')
      if (res.meta.status !== 200) {
        return this.$message.error('获取商品分类失败！')
      }
      this.cateList = res.data
      console.log('cateList', this.cateList)
    },
    //级联选择框选中项变化，会触发这个函数
     async handleChange() {
      //   根据所选分类的ID，和当前所处的面板，获取对应的参数
      const{data:res} = await this.$http.get(`categories/${this.cateId}/attributes`, {params: {sel: this.activeName}})
       if(res.meta.status!==200){
         return this.$message.error('获取参数列表失败！')
       }
      console.log('333',res)
    },
    //tab页面点击事件的处理函数
    handleTabClick() {
      console.log(this.activeName)
    },
  },
  computed: {
    //如果按钮需要被禁用，则返回true，否则返回flase
    isBtnDisabled() {
      if (this.selectedCateKeys.length !== 3) {
        return true
      }
        return false
    },
    //  当前选中的三级分类的ID
    cateId() {
      if (this.selectedCateKeys.length === 3) {
        return this.selectedCateKeys[2]
      }
      return null;
    },

  }
};
</script>
<style lang="less" scoped>
.cat_opt {
  margin: 15px 0;
  display: flex;
}
</style>
