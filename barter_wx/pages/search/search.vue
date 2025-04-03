<template>
	<view class="search">
		<goods-list @toDetail="goToDetail" :goods="searchGoods"></goods-list>
		<view class="isOver" v-if="flag"><text>没有更多数据啦</text>~</view>
		<zero-back-top scrollTop="0" right iconType="arrow"></zero-back-top>
	</view>
</template>

<script>
	import goodsList from '../../components/goods-list/goods-list.vue'
	export default {
		data() {
			return {
				searchInfo: '',
				pageNum: 1,
				pageSize: 6,
				searchGoods: [],
				flag: false
			}
		},
		onLoad(options) {
			this.searchInfo = options.info
			// console.log(options)
			this.getSearchGoods()
		},
		components: {
			"goods-list": goodsList
		},
		onReachBottom() {
			console.log('触底啦~')
			if (!this.flag) {
				this.pageSize += 10
				this.getSearchGoods()
			}
		},
		methods: {
			async getSearchGoods() {
				let res = await this.$http.get('/goods/name',{
					name: this.searchInfo,
					pageNum: this.pageNum,
					pageSize: this.pageSize
				})
				console.log(res)
				this.searchGoods = res.data.data.list
				if (!res.data.data.hasNextPage)
					this.flag = true
			},
			goToDetail(id) {
				uni.navigateTo({
					url: '../detail/detail?' + 'id=' + id
				})
			},
		}
	}
</script>

<style lang="scss">
	.search{
		width: 100%;
			.isOver {
			width: 100%;
			font-size: 30rpx;
			text-align: center;
			margin: 5rpx auto ;
		}
	}
	
</style>
