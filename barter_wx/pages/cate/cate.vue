<template>
	<view class="pics">
		<!-- 左侧区域 -->
		<scroll-view class="left" scroll-y>
			<view :class="active===index?'active':''" v-for="(item,index) in classifiyTree" :key="item.classifyId"
				@click="leftClickHandle(index,item.classifyId)">
				{{item.name}}
			</view>
		</scroll-view>
		<!-- 右侧区域 -->
		<view class="right">
			<!-- 分段筛选 -->
			<!-- <uni-segmented-control :current="current" :values="items" @clickItem="clickItems" styleType="button"
				activeColor="#007aff" style="width: 25rpx;">   
			</uni-segmented-control> -->
			<uni-icons type="settings"  color="#2b9939" style="margin-left:500rpx;" size="30" @click="toProduct()"></uni-icons>
			<!-- 抽屉 -->
			<uni-drawer ref="showRight" mode="right" style="font-size: 35rpx;" :width="120">
				<view v-for="item in children" :key="item.classifyId" :class="item.classifyId==sonId?'select':''">
					<view @click="getSonGoods(item.classifyId)">
						{{item.name}}
					</view>
				</view>
			</uni-drawer>
			<zero-back-top :scrollTop="scrollTop" ></zero-back-top>	
			<!-- 获取的商品展示区域 -->
			<scroll-view scroll-y class="scroll" @scroll="refreash()" @scrolltolower="refreash()">
				<view class="item" v-for="item in classifiyGoods" :key="item.goodId" @click="toDetail(item.goodId)">
					<image :src="item.imgUrl"></image>
					<view class="intro">
						<view>￥{{item.goodPrice}}</view>
						<view style="width: 400rpx;text-align: center;margin-left: 60rpx;">{{item.goodName}}</view>
					</view>
				</view>
				
				<view v-if="flag" style="color: #6A6A6A;margin-top: 30rpx;">没有更多数据啦~</view>
			</scroll-view>
			
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				classifiyTree: [],
				classifiyGoods: [],
				active: 0,
				pageNum: 1,
				pageSize: 5,
				flag: false,
				children: [],
				scrollTop:0,
				drawerVisible: false,
				current: '综合排序',
				items: ['综合排序', '价格排序', '时间排序'],
				parentId:0,
				sonId: 1,
			}
		},
		onReachBottom() {
			console.log('触底了')
			if (!this.flag) {
				this.pageSize += 10
				this.getGoods()
			}
		},
		methods: {
			async getClassifyTree() {
				let  res = await this.$http.get('/released/classify/goods', {
				});
				console.log('res', res)
				this.classifiyTree = res.data.data
				this.parentId = this.classifiyTree[0].classifyId
				this.children = this.classifiyTree[0].children
				this.sonId = this.classifiyTree[0].children[0].classifyId
				// console.log('children',this.children)
			},
			leftClickHandle(index, id) {
				this.active = index
				this.parentId = id
				this.flag = false
				if(this.classifiyTree[index].children!=null){
						this.children = this.classifiyTree[index].children
						// console.log('children',this.children)
						this.sonId = this.classifiyTree[index].children[0].classifyId
						this.getGoods()
				}else{
					this.classifiyGoods = []
					this.children = []
					this.flag = true
				}
			
			},
			async getGoods() {
				let res = await this.$http.get('/goods/classify',{
					id:this.sonId,
					pageNum:this.pageNum,
					pageSize:this.pageSize
				})
				console.log('getSonGoods',res)
				if (res.data.data.hasNextPage == false)
						this.flag = true
				console.log('flag',this.flag)
				if(res.data.code==200){
					this.classifiyGoods = res.data.data.list
					this.$refs.showRight.close()
				}
				else{
					uni.showToast({
						title:'商品获取失败',
						icon:'error'
					})
				}
			},
			refreash() {
				// console.log(e)
				// console.log('refreash')
			},
			toDetail(id) {
				console.log('去商品详情页面')
				uni.navigateTo({
					url: '../detail/detail?' + 'id=' + id,
				})
			},
			// 展示子项
			toProduct() {
				// uni.navigateTo({
				// 	url:'../product/product?'+'id='+this.id
				// })
				this.$refs.showRight.open();
			},
			clickItems() {
				this.current = this.items
			},
			 getSonGoods(sonId){
					this.sonId = sonId
					this.flag = false
					this.getGoods()
			},
		},
		onLoad() {
			this.getClassifyTree()
			this.getGoods()
		},

	}
</script>

<style lang="scss">
	page {
		height: 100%;
	}
	.select{
		color: #FF5A5F;
	}
	.pics {
		height: 100%;
		display: flex;
		.scroll{
			padding-bottom: 120rpx;
		}
		.left {
			width: 200rpx;
			height: 100%;
			border-right: 1px solid #eee;

			view {
				height: 60px;
				line-height: 60px;
				color: #333;
				text-align: center;
				font-size: 30rpx;
				border-bottom: 1px solid #eee;
			}

			.active {
				background: $shop-color;
				color: #fff;
			}
		}

		.right {
			text-align: center;
			.item {
				image {
					width: 250rpx;
					height:250rpx;
				}
				.intro {
					view:nth-child(1) {
						color: $shop-color;
					}
				}
			}

			.isOver {
				text-align: center;
				font-size: 30rpx;
			}
		}

	}
</style>
