<template>
	<view class="my">
		<!-- 未登录 -->
		<view v-if="!userId" class="unLoginInfo">
			<view @click="login" class="iconfont icon-touxiang">
				<text v-if="!userId">游客</text>
				<text v-else>{{userName}}</text>
			</view>
		</view>
		<!-- 已登录 -->
		<view v-else class="loginInfo">
			<view class="user">
				<image src="../../static/barter.png"></image>
				<text>{{userName}}</text>
			</view>

			<view class="order-section">
				<view class="order-item" @click="toOrders(2)">
					<text class="iconfont icon-shouye2"></text>
					<text>全部订单</text>
				</view>
				<view class="order-item" @click="toOrders(0)">
					<text class="iconfont icon-daifukuan"></text>
					<text>待付款</text>
				</view>
				<view class="order-item" @click="toOrders(1)">
					<text class="iconfont icon-daishouhuo"></text>
					<text>待收货</text>
				</view>
				<view class="order-item" @click="toOrders(-1)">
					<text class="iconfont icon-tuikuantuihuo"></text>
					<text>超时</text>
				</view>
			</view>


			<uni-card class="personSet" :isFull="true">
				<text class="iconfont icon-shangpin"></text>
				<text>我的商品</text>
				<scroll-view scroll-x="true" class="myGoods">
					<view v-for="item in myGoods" :key="item.goodId">
						<image :src="item.imgUrl"></image>
					</view>
				</scroll-view>
				
				<!-- 设置 -->
				<view class="setting" @click="toPage('setting')">
					<view class="iconfont icon-shezhi">
						<text>设置</text>
					</view>
					<view class="iconfont icon-xiangyou1"></view>
				</view>
				<!-- 账号安全 -->
				<view class="setting" @click="toPage('security')">
					<view class="iconfont icon-zhanghaoanquan">
						<text>账号安全</text>
					</view>
					<view class="iconfont icon-xiangyou1"></view>
				</view>
				<!-- 帮助中心 -->
				<view class="setting" @click="toPage('help')">
					<view class="iconfont icon-bangzhuzhongxin">
						<text>帮助中心</text>
					</view>
					<view class="iconfont icon-xiangyou1"></view>
				</view>

				<!-- 关于我们 -->
				<view class="setting" @click="toPage('about')">
					<view class="iconfont icon-guanyuwomen">
						<text>关于我们</text>
					</view>
					<view class="iconfont icon-xiangyou1"></view>
				</view>
				
				<button type="warn" @click="exit">退出登录</button>
			</uni-card>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				userName: '',
				userId: 1,
				myGoods: [],
				pageNum:1,
				pageSize:5,
			}
		},
		methods: {
			login() {
				console.log('去登录')
				uni.navigateTo({
					url: '../login/login'
				})
			},
			toPage(page) {
				uni.navigateTo({
					url:`../`+page+'/'+page
				})
			},
			// 获取个人商品
			async getOwnGoods(){
				let res = await this.$http.get('/goods/user',{
					pageNum:this.pageNum,
					pageSize:this.pageSize,
					userId:this.userId,
					type:0
				})
				console.log('getOwnGoods',res)
				if(res.data.code==200){
					this.myGoods = res.data.data.list
				}
			},
			//退出登录
			exit(){
				uni.showModal({
				    content: '确定要退出登录么',
				    success: (e)=>{
				    	if(e.confirm){
				    		uni.clearStorageSync()
								this.userId = ''
								this.userName = ''
				    		setTimeout(()=>{
				    			uni.navigateBack();
				    		}, 200)
				    	}
				    }
				});
			},
			toOrders(type){
				uni.navigateTo({
					url:'../orders/orders?'+'type='+type
				})
			}
		},
		onShow() {
			this.userName = uni.getStorageSync('userName')
			this.userId  = uni.getStorageSync('userId')
		},
		onLoad(options) {
			this.userName = uni.getStorageSync('userName')
			this.userId  = uni.getStorageSync('userId')
			
			this.getOwnGoods()
			console.log(options)
			// this.userName = options.userName
			// this.userId = options.userId
		},
	}
</script>

<style lang="scss">
	.my {
		display: flex;
		.unLoginInfo {
			display: flex;
			flex-direction: raw;
			width: 100%;
			text-align: center;
			image {
				width: 200rpx;
				height: 200rpx;
			}

			text {
				margin-top: 60rpx;
			}

			.icon-touxiang {
				margin-left: 30rpx;
				font-size: 100rpx;

				text {
					font-size: 50rpx;
					color: #787468;

				}
			}
		}

		.loginInfo {
			.user {
				display: flex;
				flex-direction: raw;
				image {
					width: 200rpx;
					height: 200rpx;
				}
				text {
					margin-top: 60rpx;
					font-size: 50rpx;
					color: #787468;
				}
			}

			.personSet {
				button{
					width: 600rpx;
					margin: 20rpx;
				}
				.icon-shangpin{
					font-size: 55rpx;
					color: #eb8a31;
				}
				text{
					font-size: 33rpx;
				}
				.myGoods {
					width: 700rpx;
					display: flex;
					white-space: nowrap;
					flex-direction: raw;
					
					view {
						display: inline;
						width: 250rpx;
						image {
							display: inline-block;
							width: 200rpx;
							height: 200rpx;
							margin: 10rpx;
						}
					}
				}
			}
			.order-section {
				background-color: #FFFFFF;
				display: flex;
				margin-bottom: 30rpx;
				.iconfont{
					font-size: 55rpx;
					color: #FF5A5F;
				}
				.order-item {
					width: 170rpx;
					height: 120rpx;
					margin: 8rpx;
					font-size: 33rpx;
					text-align: center;
					text {
						display: block;
					}
				}
			}

			.icon-xiangyou1 {
				color: #000000;
				font-size: 40rpx;
			}
			.setting {
				width: 700rpx;
				display: flex;
				justify-content: space-between;
				border-bottom: 2rpx solid;
				margin: 10rpx;
				view{
					padding: 5rpx;
				}
				
				.icon-shezhi {
					color: #F3A73F;
					font-size: 50rpx;
					text {
						color: #000000;
						font-size: 40rpx;
						margin-left: 20rpx;
					}
				}

				.icon-zhanghaoanquan {
					color: #54b4ef;
					font-size: 50rpx;
					text {
						color: #000000;
						font-size: 40rpx;
						margin-left: 20rpx;
					}
				}

				.icon-bangzhuzhongxin {
					color: #54b4ef;
					font-size: 45rpx;
					text {
						color: #000000;
						font-size: 40rpx;
						margin-left: 20rpx;
					}
				}

				.icon-guanyuwomen {
					color: #ee883b;
					font-size: 45rpx;
					text {
						color: #000000;
						font-size: 40rpx;
						margin-left: 20rpx;
					}
				}
			}
		}
	}
</style>
