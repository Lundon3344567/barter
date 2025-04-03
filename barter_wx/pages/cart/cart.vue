<template>
	<view class="cart">
		<!-- 未登录 -->
		<view v-if="!userName" @click="toLogin" class="noLoginIn">
			<image src="../../static/未登录.png"></image>
			<text>未登录</text>
			<text style="color: #D81E06;">去登陆></text>
		</view>
		<!-- 已登录 -->
		<view v-else class="loginIn">
			<view v-for="(item,index) in cartInfo" :key="item.goodId" class="goodItem">
				<checkbox-group @change="changeSum" :id="index">
					<checkbox value="1" color="#FFCC33" style="transform: scale(0.8);" />
				</checkbox-group>
				<image :src="item.imgUrl" @click="toDetail(item.goodId)"></image>
				<view style="display: flex;flex-direction: column;">
					<text style="font-size: 30rpx;">{{item.goodName}}</text>
					<text style="font-size: 35rpx;color: #FF5A5F;margin-top: 40rpx;">￥{{item.goodPrice}}</text>
				</view>
				<view class="opreation">
					<view @click="changeNum(-1,index)" class="iconfont icon-jian"></view>
					<input v-model="item.goodNum" style="width: 30rpx;font-size: 40rpx;" @blur="changeNum(0,index)" />
					<view class="iconfont icon-jia" @click="changeNum(1,index)"></view>
				</view>

			</view>

			<view style="display: flex;justify-content: space-between">
				总价：<text style="color: #FF5A5F;">￥{{totalPrice}}</text>
				<button type="warn" size="mini" @click="submitOrder">去结算</button>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		components: {

		},
		data() {
			return {
				userId: 0,
				userName: '',
				value: 1,
				pageNum: 1,
				pageSize: 5,
				flag: false,
				sum: 0,
				cartInfo: [],
				order: [],
				payOrder:[],
				orders:[]
			}
		},
		onShow() {
			this.userId = uni.getStorageSync('userId'),
			this.userName = uni.getStorageSync('userName')
			if(this.userId){
				this.getCartInfo()
			}
		},
		onLoad() {
			this.userId = uni.getStorageSync('userId'),
			this.userName = uni.getStorageSync('userName')
			// let token = uni.getStorageSync('token')
			// console.log('token',token)
			if(this.userId){
				this.getCartInfo()
			}
		},
		computed:{
			totalPrice(){
				if(this.cartInfo.length!=0){
					let totalPrice = 0
					this.payOrder.map(item =>{
						console.log('item',item)
						totalPrice += this.cartInfo[item].goodNum*this.cartInfo[item].goodPrice
					})
					return totalPrice
				}else{
					return 0;
				}
			}
		},
		methods: {
			// 请求增加购物车商品数量接口
			async changeNum(type, index) {
				// 输入框输入数量
				if (type == 0) {
					// 删除商品
					if (this.cartInfo[index].goodNum == 0) {
						// 删除商品
						uni.showModal({
							content: '确定要删除商品吗？',
							success: (e) => {
								if (e.confirm) {
									delOrder(this.cartInfo[index].goodId)
									return;
								}
							},
							fail: (e) => {
								this.cartInfo[index].goodNum =1
							}
							
						});
					} else {
						let res = await this.$http.put('/shoppingcart', {
							goodId: this.cartInfo[index].goodId,
							goodNum: this.cartInfo[index].goodNum,
							userId: this.userId
						})
						if (res.data.message == '库存不足，无法购买更多该商品') {
							this.cartInfo[index].goodNum = 1
							uni.showToast({
								title: '库存不足，无法购买更多该商品',
								icon: 'error'
							})
						}
					}
				}
				if (type == 1) {
					this.cartInfo[index].goodNum++;
					let  res = await this.$http.put('/shoppingcart', {
						goodId: this.cartInfo[index].goodId,
						goodNum: this.cartInfo[index].goodNum,
						userId: this.userId
					}).then(res => {
						if (res.data.code == 200) {
							return;
						} else if (res.data.message == '库存不足，无法购买更多该商品') {
							this.cartInfo[index].goodNum--;
							uni.showToast({
								title: "库存不足",
								icon: 'error'
							})
						}
					}).catch(err => {
							console.log('err',err)
					})
				}
				if (type == -1) {
					if (this.cartInfo[index].goodNum > 1) {
						this.cartInfo[index].goodNum--;
						let res = await this.$http.put('/shoppingcart', {
							goodId: this.cartInfo[index].goodId,
							goodNum: this.cartInfo[index].goodNum,
							userId: this.userId
						})
					} else {
						// 删除商品
						uni.showModal({
							content: '确定要删除商品吗？',
							success: (e) => {
								if (e.confirm) {
									this.delOrder(this.cartInfo[index].goodId)
								}
							}
						});
					}
				}
			},
			async delOrder(id) {
				console.log('删除商品')
				uni.request({
					url:'http://localhost:8081/shoppingcart',
					method:'DELETE',
					data:{
						goodId:id,
						userId:this.userId
					},
					header:{
						'Content-Type': 'application/x-www-form-urlencoded',
						'Authorization':  uni.getStorageSync('token'),
					},
					success: (res) => {
						console.log('delOreder',res)
							if(res.data.code==200){
								uni.showToast({
									title:'删除商品成功'
								})
								this.getCartInfo()
						}
					}
				})
				
			},
			toLogin() {
				uni.navigateTo({
					url: '../login/login'
				})
			},
			toDetail(id){
				uni.navigateTo({
					url: '../detail/detail?' + 'id=' + id
				})
			},
			// 获取用户的购物车信息
			async getCartInfo() {
				let res = await this.$http.get('/shoppingcart/page', {
					pageNum: this.pageNum,
					pageSize: this.pageSize,
					userId: this.userId
				})
				if (res.data.code == 200) {
					if (!res.data.data.hasNextPage)
						this.flag = true
					this.cartInfo = res.data.data.list
				}
				console.log('getCartInfoRes', res)
			},

			changeSum(e) {
				console.log('changeSum', e)
				// 添加
				if(e.detail.value.length==1){
					console.log('增加')
					this.payOrder.push(e.currentTarget.id)
				}else{
					console.log('减少')
					for (var i = 0; i < this.payOrder.length; i++) {
						if(this.payOrder[i]==e.currentTarget.id)
								this.payOrder.splice(i,1)
					}
				}
			},
			async submitOrder() {
				if(this.payOrder.length==0){
					uni.showToast({
						title:'请选择商品',
						icon:'error'
					})
					return;
				}
				for (var i = 0; i < this.payOrder.length; i++) {
					var obj={}
					obj.buyerId = this.userId,
					obj.goodNum = this.cartInfo[this.payOrder[i]].goodNum,
					obj.goodId = this.cartInfo[this.payOrder[i]].goodId
					this.orders.push(obj)
				}
				uni.showToast({
					title:'',
					icon:'loading',
					duration:1200
				})
				console.log('orders',this.orders)
				// 提交订单
				await this.$http.post('/order',{
					"orderFromList":this.orders
				}).then(res =>{
					console.log('res',res)
					if(res.data.code==200){
						this.getCartInfo()
						uni.showToast({
							title:'添加订单成功'
						})
						this.getCartInfo()
						this.$forceUpdate()
					}
				}).catch(err=>{
					console.log('err',err)
				})
			}
		}
	}
</script>

<style lang="scss">
	.cart {
		.noLoginIn {
			width: 100%;
			height: 300rpx;
			text-align: center;

			image {
				height: 700rpx;
				width: 100%;
			}
		}

		.loginIn {
			width: 100%;

			.goodItem {
				display: flex;
				margin: 20rpx;
				justify-content: space-between;
			}

			image {
				width: 300rpx;
				height: 200rpx;
			}

			.countNumberBg {
				height: 100rpx;
				display: flex;
				justify-content: flex-end;
				align-items: center;
				margin-top: 40rpx;

				/deep/.countNumber {
					margin: 0 20rpx 0 0;
					height: 66rpx;
				}
			}

			.opreation {
				display: flex;

				// margin-left: 50rpx;
				.icon-jia {
					font-size: 50rpx;
					color: #FF5A5F;
				}

				.icon-jian {
					font-size: 50rpx;
					color: #FF5A5F;
				}
			}
		}

	}
</style>
