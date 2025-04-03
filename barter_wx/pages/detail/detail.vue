<template>
	<view class="good">
		<image :src="goodInfo.imgUrl"></image>
		<view class="info">
			<view>{{goodInfo.goodName}}</view>
			<view>￥{{goodInfo.goodPrice}}</view>
			<view>
				<text>库存：{{goodInfo.goodNum}}</text>
				<text>浏览量：{{goodInfo.viewCount}}</text>
				<text>发布时间：{{goodInfo.createTime|formDate}}</text>
			</view>
		</view>

		<uni-card title="商品描述" :is-full="true" :shadow="true" class="card">
			<text>{{goodInfo.goodDescribe}}</text>
		</uni-card>

		<uni-goods-nav :fill="true" :options="options" :button-group="buttonGroup" @click="onClick"
			@buttonClick="buttonClick" />
	</view>
</template>

<script>
	export default {
		data() {
			return {
				goodId: 1,
				userId: '',
				userName: '',
				goodInfo: {
					imgUrl: '',
					goodName: '',
					goodPrice: 0.,
					goodNum: 0,
					viewCount: 0,
					createTime: ''
				},
				options: [{
						icon: 'headphones',
						text: '客服'
					},
					{
						icon: 'shop',
						text: '卖家',
						infoColor: "red"
					},
					{
						icon: 'cart',
						text: '购物车',
						info: 0
					}
				],
				buttonGroup: [{
					text: '加入购物车',
					backgroundColor: '#ff0000',
					color: '#fff'
				}]
			}
		},
		onLoad(options) {
			console.log(options)
			this.goodId = options.id
			this.userId = uni.getStorageSync('userId')
			this.userName = uni.getStorageSync('userName')
			this.getGoodDetail()
			this.queryIsAddtoCart()
		},
		filters: {
			formDate(date) {
				return date
			}
		},
		methods: {
			async getGoodDetail() {
				let  res = await this.$http.get(`/goods/id/${this.goodId}`, {
					id: this.goodId
				});
				console.log('getGoodDetail', res.data.data)
				if (res.data.data != null) {
					this.goodInfo = res.data.data
				} else {
					uni.showToast({
						title: res.data.message,
						icon: "error"
					})
				}
			},
			onClick(e) {
				console.log('e',e)
				if(e.index==2){
					if(e.content.info==0){
						uni.showToast({
							title:'还未加入购物车',
							icon:'none'
						})
					}else{
						uni.showToast({
							title:'已加入购物车',
							icon:'none'
						})
					}
				}
			},
			buttonClick(e) {
				console.log(e)
				if (!this.userName) {
					uni.showToast({
						title: '请先登录',
						icon: 'error',
						duration: 800
					})
					setTimeout(function() {
						uni.navigateTo({
							url: '../login/login'
						})
					}, 900)
				}else{
					if(this.options[2].info==0){
						if(this.goodInfo.ownerId==this.userId){
							uni.showToast({
								title:'自己的商品不能加入购物车',
								icon:'error'
							})
						}else{
							this.addToCart()
						}
					}else{
						uni.showToast({
							title:'已加入购物车',
							icon:'error'
						})
					}
				}
			},
			async queryIsAddtoCart() {
				if (!this.userName) {
					return;
				}
				let  res = await this.$http.get('/shoppingcart/good', {
					goodId: this.goodId,
					userId: this.userId
				})
				if (res.data.message == '用户还未将该商品加入购物车') {
					this.options[2].info = 0
				} else {
					this.options[2].info = 1
				}
				console.log('queryIsAddtoCartRes', res)
			},
			async addToCart(){
				let res = await this.$http.post('/shoppingcart',{
					goodNum:1,
					userId:this.userId,
					goodId:this.goodId
				})
				console.log('addToCartRes',res)
				if(res.data.code==200){
					uni.showToast({
						title:'加入购物车成功'
					})
					this.options[2].info=1
				}else{
					uni.showToast({
						title:'加入购物车失败',
						icon:'error'
					})
				}
			}
		}
	}
</script>

<style lang="scss">
	.good {
		image {
			width: 750rpx;
			height: 550rpx;
		}

		.info {
			background: #fff;
			font-size: 40rpx;

			view:nth-child(2) {
				color: $shop-color;
			}

			view:nth-child(3) {
				display: flex;
				flex-direction: raw;
				font-size: 30rpx;
				color: #b6b6b6;
				justify-content: space-between;
				margin: 15rpx;
			}
		}

		.card {
			background: #fff;
			margin: 15rpx;
		}
	}
</style>
