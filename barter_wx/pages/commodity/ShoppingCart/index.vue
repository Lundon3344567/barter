<template>
	<view>
		<ShoppingCart :list="comarr" @del="del" @submit="submit" ></ShoppingCart>
	</view>
</template>

<script>
import ShoppingCart	from '../../../components/Shopping-Cart/Shopping-Cart.vue';
 export default{
	 components:{ShoppingCart},
	 data(){
		 return {
			 ChildArray:[], //商品选择
			 total:0, //合计
			 AllState:false, //全选状态
			 SumMoney:[],
			 comarr:[
				 {name:'1111',
				 business:"马大姐",
				 icon:'../../../static/HM-shophome/category-img/6.png',
				 checked:false,
				 child:[
					 {name:'希思黎漾泽美唇彩笔 1号裸色SISLEY PHYTO-LIP TWIST 2.5g长效持色，持久润泽。',goods_img:'../../../static/HM-shophome/img/p1.jpg',checked:false,count:2,price:200},
					 {name:'希思黎漾泽美唇彩笔 1号裸色SISLEY PHYTO-LIP TWIST 2.5g长效持色，持久润泽。',goods_img:'../../../static/HM-shophome/img/p1.jpg',checked:false,count:1,price:250},
					 {name:'希思黎漾泽美唇彩笔 1号裸色SISLEY PHYTO-LIP TWIST 2.5g长效持色，持久润泽。',goods_img:'../../../static/HM-shophome/img/p1.jpg',checked:false,count:1,price:250},
					 ]
				 },
				 {
				name:'1111',
				checked:false,
				icon:'../../../static/HM-shophome/category-img/6.png',
				business:"马大姐",
				 child:[
					 {name:'希思黎漾泽美唇彩笔 1号裸色SISLEY PHYTO-LIP TWIST 2.5g长效持色，持久润泽。',goods_img:'../../../static/HM-shophome/img/p1.jpg',checked:false,count:2,price:200},
					 {name:'希思黎漾泽美唇彩笔 1号裸色SISLEY PHYTO-LIP TWIST 2.5g长效持色，持久润泽。',goods_img:'../../../static/HM-shophome/img/p1.jpg',checked:false,count:1,price:250},
					 {name:'希思黎漾泽美唇彩笔 1号裸色SISLEY PHYTO-LIP TWIST 2.5g长效持色，持久润泽。',goods_img:'../../../static/HM-shophome/img/p1.jpg',checked:false,count:1,price:250},
					 ]
				 },{
				  name:'1111',
				  checked:false,
				  icon:'../../../static/HM-shophome/category-img/6.png',
				  business:"马大姐",
				 child:[
					 {name:'希思黎漾泽美唇彩笔 1号裸色SISLEY PHYTO-LIP TWIST 2.5g长效持色，持久润泽。',goods_img:'../../../static/HM-shophome/img/p1.jpg',checked:false,count:2,price:200},
					 {name:'希思黎漾泽美唇彩笔 1号裸色SISLEY PHYTO-LIP TWIST 2.5g长效持色，持久润泽。',goods_img:'../../../static/HM-shophome/img/p1.jpg',checked:false,count:1,price:250},
					 {name:'希思黎漾泽美唇彩笔 1号裸色SISLEY PHYTO-LIP TWIST 2.5g长效持色，持久润泽。',goods_img:'../../../static/HM-shophome/img/p1.jpg',checked:false,count:1,price:250},
					 ]
				  },
				  ]
		 }
	 },
	 onLoad() {
	 },
	 methods:{
		del(data){
		    console.log(data);	
		},
		submit(data){
			console.log(data);
		}
	 }
 }
</script>

<style lang="scss" scoped>
	
</style>
