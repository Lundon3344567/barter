<template>
	<view class="home">
		<uni-search-bar @confirm="toSearch"  v-model="searchInfo" />
		<!-- 广告轮播图 -->
		<swiper indicator-dots circular>
			<swiper-item v-for="item in swipers" :key="item.adId">
				<image :src="item.imgUrl"></image>
			</swiper-item>
		</swiper>

		<uni-notice-bar showIcon="true" scrollable="true" single="true" text="请警惕商品信息,辨别真伪,如有疑惑,请联系客服获取帮助" showClose="true"></uni-notice-bar>
		<!-- 推荐商品区域 -->
		<view class="hot_goods">
			<view class="title">
				推荐商品
			</view>
			<goods-list @toDetail="goToDetail" :goods="hotGoods"></goods-list>
			
		</view>
		<view class="isOiver" v-if="flag">没有更多数据啦~</view>
		<zero-back-top :scrollTop="scrollTop"  iconType="arrow"></zero-back-top>		
	</view>
</template>

<script>
	import goodsList from '../../components/goods-list/goods-list.vue'
	export default {
		data() {
			return {
				swipers: [],
				hotGoods: [],
				flag: false,
				pageNum: 1,
				pageSize: 10,
				scrollTop: 500,
				searchInfo:''
			}
		},
		components: {
			"goods-list": goodsList
		},
		onLoad() {
			this.getSwipers()
			this.getHotGoods()
		},
		onShow() {
			this.searchInfo=''
		},
		onReachBottom() {
			console.log('触底啦~')
			if (!this.flag) {
				this.pageSize += 10
				this.getHotGoods()
			}
		},
		methods: {
			// 获取轮播图的数据
			async getSwipers() {
				let res = await this.$http.get('/adadmin/ad/page',{
					pageNum: 1,
					pageSize: 5
				});
				
				// console.log('swipers',res)
				this.swipers = res.data.data.list
			},
			// 获取推荐商品列表
			async getHotGoods(){
				let res = await this.$http.get('/goods/recommend',{
					pageNum: this.pageNum,
					pageSize: this.pageSize
				});
				
				// console.log('res',res)
				if (!res.data.data.hasNextPage)
					this.flag = true
				this.hotGoods = res.data.data.list
			},
			goToDetail(id) {
				uni.navigateTo({
					url: '../detail/detail?' + 'id=' + id
				})
			},
			Scroll(e) {
				this.scrollTop = e.scrollTop;
			},
			toSearch(){
				uni.navigateTo({
					url:'../search/search?'+'info='+this.searchInfo 
				})
			}
		}
	}
</script>

<style lang="scss">
	.home {
		swiper {
			width: 750rpx;
			height: 400rpx;

			image {
				width: 100%;
				height: 100%
			}
		}

		.nav {
			display: flex;

			.nav_item {
				width: 25%;
				text-align: center;

				view {
					width: 120rpx;
					height: 120rpx;
					background: $shop-color;
					border-radius: 60rpx;
					margin: 10px auto;
					line-height: 120rpx;
					color: #ff;
					font-size: 50rpx;
				}

				text {
					font-size: 30rpx;
				}
			}
		}

		.hot_goods {
			background: #eee;
			overflow: hidden;
			margin-top: 10px;

			.title {
				height: 50px;
				line-height: 50px;
				color: $shop-color;
				text-align: center;
				letter-spacing: 20px;
				background: #fff;
				margin: 7rpx 0;
			}
		}

		.isOver {
			font-size: 28rpx;
			text-align: center;
		}
	}
</style>
