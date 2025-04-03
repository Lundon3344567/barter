<template>
	<view class="container">
		<uni-card :is-full="true">
			<view class="list-cell" @click="navTo('个人资料')" >
				<text class="cell-tit">个人资料</text>
				<text class="iconfont icon-xiangyou1"></text>
			</view>
			<view class="list-cell" @click="navTo('收货地址')" >
				<text class="cell-tit">收货地址</text>
				<text class="iconfont icon-xiangyou1"></text>
			</view>
			<view class="list-cell" @click="navTo('实名认证')">
				<text class="cell-tit">实名认证</text>
				<text class="iconfont icon-xiangyou1"></text>
			</view>
			
			<view class="list-cell">
				<text class="cell-tit">消息推送</text>
				<switch checked color="#fa436a" @change="switchChange" />
			</view>
			<view class="list-cell m-t " @click="navTo('清除缓存')" >
				<text class="cell-tit">清除缓存</text>
				<text class="iconfont icon-xiangyou1"></text>
			</view>
			<view class="list-cell " @click="navTo('关于Dcloud')">
				<text class="cell-tit">关于Dcloud</text>
				<text class="iconfont icon-xiangyou1"></text>
			</view>
			<view class="list-cell">
				<text class="cell-tit">检查更新</text>
				<text>当前版本 1.0.3</text><text class="iconfont icon-xiangyou1"></text>
			</view>
		</uni-card>

	</view>
</template>

<script>
	import {  
	    mapMutations  
	} from 'vuex';
	export default {
		data() {
			return {
				
			};
		},
		methods:{
			...mapMutations(['logout']),

			navTo(url){
				uni.showToast({
					title:`跳转到${url}`,
					icon:'none'
				})
			},
			
			//switch
			switchChange(e){
				let statusTip = e.detail.value ? '打开': '关闭';
				uni.showToast({
					title:`${statusTip}消息推送`,
					icon:'none'
				})
			},

		}
	}
</script>

<style lang='scss'>
	.container{
		background-color: #eee;
	}
		.list-cell{
			display: flex;
			justify-content: space-between;
			border-bottom: 2rpx solid;
			height: 80rpx;
			text{
				margin: 20rpx;
			}
		}
		switch{
			transform: translateX(16upx) scale(.84);
		}
</style>
