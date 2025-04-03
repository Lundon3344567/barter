<template>
	<view class="goods_list">
		<view class="goods_item" v-for="item in goods" :key="item.goodId" @click="navigator(item.goodId)">
			<image :src="item.imgUrl"></image>
			<view class="price">
				<text>￥{{item.goodPrice}}</text>
			</view>
			<view class="name">{{item.goodName}}</view>
		</view>
	</view>
</template>

<script>
	export default{
		props:['goods'],
		methods:{
			navigator(id){
				this.$emit('toDetail',id)
			}
		}
	}
</script>

<style lang="scss">
	.goods_list{
		padding: 0 15rpx;
		display: flex;
		flex-wrap: wrap;
		justify-content:space-between;
		.goods_item{
			background: #fff;
			width: 355rpx;
			margin: 10rpx 0;
			padding:15rpx;
			box-sizing:border-box ;
			image{
				width: 80%;
				height: 270rpx;
				display: block;
				margin: 0 auto;
			}
			.price{
				color: $shop-color;
				font-size: 30rpx;
				margin: 20rpx 0 5rpx 0;
				text:nth-child(2){
					color: #ccc;
					font-size:28rpx ;
					margin-left: 17rpx;
					text-decoration: line-through;
				}
			}
			.name{
				font-size: 28rpx;
				line-height: 50rpx;
				padding-bottom: 15rpx;
				padding-top: 10rpx;
			}
		}
	}
</style>
