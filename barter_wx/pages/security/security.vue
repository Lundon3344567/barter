<template>
	<view class="security">

		<uni-card :is-full="true">

			<view v-if="userPhone!=''" class="item">
				<view class="iconfont icon-zhengque">
					<text style="font-size: 40rpx;">绑定的手机是{{userPhone}}</text>
				</view>
				<view class="items" @click="navTo('更换手机')">更换手机</view>
			</view>

			<view v-else class="item">
				<view class="iconfont icon-cuowu">
					<text style="font-size: 40rpx;">绑定手机</text>
				</view>
				<view class="items" @click="navTo('绑定手机')">绑定手机</view>
			</view>

			<view class="item">
				<view class="iconfont icon-zhengque">
					<text style="font-size: 40rpx;">绑定的邮箱是{{email}}</text>
				</view>
				<view class="items" @click="navTo('更换邮箱')">更换邮箱</view>
			</view>

			<button type="warn" @click="toLstPwd">修改密码</button>
		</uni-card>


	</view>
</template>

<script>
	export default {
		data() {
			return {
				userPhone: '11111111111',
				email: '3235833452@qq.com',
				password: '',
			}
		},
		methods: {
			navTo(info) {
				uni.showToast({
					title: '跳至' + info + '页面',
					icon: 'none'
				})
			},
			toLstPwd(){
				uni.clearStorageSync()
				uni.navigateTo({
					url:'../lstPwd/lstPwd'
				})
			}
		}
	}
</script>

<style lang="scss">
	.security {
		justify-content: space-between;

		.item {
			display: flex;
			border-bottom: 2rpx solid #666666;
			justify-content: space-between;
			margin-bottom: 50rpx;

			.icon-zhengque {
				color: #18BC37;
				font-size: 50rpx;
			}

			.icon-cuowu {
				color: $shop-color;
				font-size: 50rpx;
			}

			.items {
				margin: 10rpx;
				width: 300rpx;
				font-size: 35rpx;
				color: #409eff;
			}

		}

		button {
			width: 400rpx;
			text-align: center;
		}
	}
</style>
