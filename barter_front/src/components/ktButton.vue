/**
 * @Description: 后台权限级控制按钮
 * @author Lion-Air14
 * @date 2021/12/9
 * @time 19:04
*/
<template>
  <el-button :size="size" :type="type"
            :loading="loading" :disabled="!hasPerms(perms)" :icon="icon" @click="handleClick">
    {{label}}
  </el-button>
</template>

<script>
// 禁用标识
// :disabled="!hasPerms(perms)"
import {hasPermission} from "@/views/back";
export default {
  name: 'KtButton',
  props: {
    label: {  // 按钮显示文本
      type: String,
      default: null
    },
    size: {  // 按钮尺寸
      type: String,
      default: 'mini'
    },
    type: {  // 按钮类型
      type: String,
      default: 'primary'
    },
    loading: {  // 按钮加载标识
      type: Boolean,
      default: false
    },
    disabled: {  // 按钮是否禁用
      type: Boolean,
      default: false
    },
    perms: {  // 按钮权限标识，外部使用者传入
      type: String,
      default: null
    },
    icon:{
      type:String,
      default:null
    }
  },
  data() {
    return {
    }
  },
  methods: {
    handleClick: function () {
      // 按钮操作处理函数
      this.$emit('click', {})
    },
    hasPerms: function (perms) {
      // 根据权限标识和外部指示状态进行权限判断
      return hasPermission(perms) //& !this.disabled
    }
  },
  mounted() {
  }
}
</script>
