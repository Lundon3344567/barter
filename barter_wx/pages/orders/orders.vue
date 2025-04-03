<template>
	<view class="orders">
		<uni-segmented-control :current="current" :values="items" @clickItem="onClickItem" styleType="button"
			activeColor="#34c1eb">
		</uni-segmented-control>
		<!-- 全部订单 -->
		<view v-if="type==2" v-for="item in orders" :key="item.id" @click="toDetail(item.goodId)">

			<uni-card title="待付款" extra="点击查看商品详情>" v-if="item.orderFlag==0">
				<view class="info">
					<view style="color: #FF5A5F;font-size: large;">
						￥{{item.payMoney}}
					</view>
					<view>
						共{{item.goodNum}}件商品
					</view>
					<view>
						<button type="warn" size="mini">立即支付</button>
						<button type="default" size="mini">取消订单</button>
					</view>
				</view>
			</uni-card>

			<uni-card title="已付款" extra="点击查看商品详情>" v-if="item.orderFlag==1">
				<view class="info">
					<view style="color: #FF5A5F;font-size: large;">
						￥{{item.orderMoney}}
					</view>
					<view>
						共{{item.goodNum}}件商品
					</view>
				</view>
			</uni-card>

			<uni-card title="超时未付款" extra="点击查看商品详情>" v-if="item.orderFlag==-1">
				<view class="info">
					<view style="color: #FF5A5F;font-size: large;">
						￥{{item.orderMoney}}
					</view>
					<view>
						共{{item.goodNum}}件商品
					</view>
				</view>
			</uni-card>

		</view>
		<!-- 待付款-->
		<view v-else-if="type==0" v-for="item in orders" :key="item.id" @click="toDetail(item.goodId)">
			<uni-card title="待付款" extra="点击查看商品详情>">
				<view class="info">
					<view style="color: #FF5A5F;font-size: large;">
						￥{{item.payMoney}}
					</view>
					<view>
						共{{item.goodNum}}件商品
					</view>
					<view>
						<button type="warn" size="mini" @click.stop="toPay(item.id,item.orderMoney)">立即支付</button>
						<button type="default" size="mini" @click.stop="cancelPay(item.id)">取消订单</button>
					</view>
				</view>
			</uni-card>
		</view>
		<!-- 待收货 -->
		<view v-else-if="type==1" v-for="item in orders" :key="item.id" @click="toDetail(item.goodId)">
			<uni-card title="已付款" :thumbnail="item.imgUrl" extra="点击查看商品详情>">
				<view class="info">
					<view style="color: #FF5A5F;font-size: large;">
						￥{{item.orderMoney}}
					</view>
					<view>
						共{{item.goodNum}}件商品
					</view>
				</view>
			</uni-card>
		</view>
		<!-- 订单超时 -->
		<view v-else-if="type==-1" v-for="item in orders" :key="item.id" @click="toDetail(item.goodId)">
			<uni-card title="超时未付款"  extra="点击查看商品详情>" >
				<view class="info">
					<view style="color: #FF5A5F;font-size: large;">
						￥{{item.orderMoney}}
					</view>
					<view>
						共{{item.goodNum}}件商品
					</view>
				</view>
			</uni-card>

		</view>

		<view v-if="flag" style="width: 100%;text-align: center;margin-top: 30rpx;">
			<text>没有更多订单啦~</text>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				userId: '',
				type: 2,
				current: 0,
				pageNum: 1,
				pageSize: 5,
				flag: false,
				items: [
					'全部订单', '待付款', '待收货', '超时'
				],
				values: [2, 0, 1, -1],
				orders: []
			};
		},
		onReachBottom() {
			if (!this.flag) {
				this.pageSize += 5
				if (this.type == 2) {
					this.getOrders()
				} else {
					this.getOthers()
				}
			}
		},
		onLoad(options) {
			this.userId = uni.getStorageSync('userId')
			console.log('options', options)
			this.type = options.type
			this.getCurrent()
			if (this.type == 2) {
				this.getOrders()
			} else {
				this.getOthers()
			}
		},
		methods: {
			async getOrders() {
				console.log('getOrders')
				let res = await this.$http.get('/order', {
					pageNum: this.pageNum,
					pageSize: this.pageSize,
					userId: this.userId
				})
				if (res.data.code == 200) {
					this.orders = res.data.data.list
					if (!res.data.data.hasNextPage)
						this.flag = true
				} else {
					uni.showToast({
						title: '订单获取失败',
						icon: 'error'
					})
				}
				console.log('getOrdersRes', res)
			},
			async getOthers() {
				console.log('getOthers', this.type)
				let res = await this.$http.get('/order/user', {
					flag: this.type,
					pageSize: this.pageSize,
					pageNum: this.pageNum,
					userId: this.userId
				})
				if (res.data.code == 200) {
					this.orders = res.data.data.list
					if (!res.data.data.hasNextPage)
						this.flag = true
				} else {
					uni.showToast({
						title: '订单获取失败',
						icon: 'error'
					})
				}
				console.log('getOthersRes', res)
			},
			getCurrent() {
				for (var i = 0; i < this.values.length; i++) {
					if (this.values[i] == this.type)
						this.current = i
				}
			},
			onClickItem(e) {
				this.flag = false
				this.pageNum = 1,
					this.pageSize = 5
				// console.log('e',e)
				// console.log('current',this.current)
				this.current = e.currentIndex
				// console.log('values',this.values[e.currentIndex])
				this.type = this.values[e.currentIndex]
				if (this.type == 2) {
					this.getOrders()
				} else {
					this.getOthers()
				}
			},
			toDetail(id) {
				uni.navigateTo({
					url: '../detail/detail?' + 'id=' + id
				})
			},
			 async toPay(orderId, money) {
				console.log('去支付')
				uni.showModal({
					confirmText: '确认支付',
					cancelText: '取消支付',
					title: '支付￥' + money,
					success: async (e) => {
						if (e.confirm) {
							uni.showToast({
								title: '支付中',
								icon: 'loading',
								duration: 1200
							});
							// 订单支付
							let res = await this.$http.put('/order/id',{
								id:orderId
							})
							
							console.log('payRes',res)
							if(res.data.code=200){
								uni.showToast({
									title:'支付成功', 
								})
							}
							this.getOthers()
						}
					},
					fail: (e) => {

					}
				})
			},
			async cancelPay(orderId){
				uni.showModal({
					confirmText:'确认',
					cancelText:'取消',
					title:'是否取消订单',
					success: async (e) => {
						if(e.confirm){
							let res = await this.$http.put('/order',{
								id:orderId
							})
							if(res.data.code==200){
								uni.showToast({
									title:'成功取消订单'
								})
								this.getOthers()
							}
						}
					}
				})
			}
		}
	}
</script>

<style lang="scss">
	.orders {
		.info {
			display: flex;
			justify-content: space-between
		}
	}
</style>
