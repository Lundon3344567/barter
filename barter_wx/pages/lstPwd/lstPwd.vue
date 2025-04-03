<template>
	<view class="lstPwd">
		<!-- 易物图片 -->
		<image src="../../static/barter.png"></image>
		<!-- 忘记密码界面 -->
		<uni-forms ref="lostPwdRefForm" :rules="rules" :modelValue="lostPwdForm" :border="true">
			<uni-forms-item label="邮箱" name="email" required>
				<input type="text" v-model="lostPwdForm.email" placeholder="请输入邮箱" @blur="emailUseful" />
			</uni-forms-item>
			<uni-forms-item label="验证码" required name="code" style="display: flex;">
				<input type="text" v-model="lostPwdForm.code" placeholder="请输入邮箱验证码" />
				<button type="primary" :disabled="emailUse" size="mini" @click="getLostCode">{{count}}</button>
				<uni-countdown color="#fff" background-color="#007aff" :start="emailUseStart" :showDay="false" :hour="0"
					:minute="0" :second="second" @timeup="timeUp"></uni-countdown>
			</uni-forms-item>
			<uni-forms-item label="密码" required name="password">
				<input type="password" v-model="lostPwdForm.password" placeholder="请输入密码" />
			</uni-forms-item>
			<view class="button">
				<button type="primary" @click="lostPwd">修改</button>
				<button type="warn" @click="toPage('login')">登录</button>
			</view>
		</uni-forms>
	</view>
</template>

<script>
	const CryptoJS = require('../../utils/crypto.js')
	export default {
		data() {
			return {
				lostPwdForm: {
					email: '',
					code: '',
					password: ''
				},
				second: 30,
				emailUse: true,
				emailUseStart: false,
				count: '获取验证码',
				rules: {
					email: {
						rules: [{
								required: true,
								errorMessage: '邮箱不能为空',

							},
							{
								format: 'email',
								errorMessage: '邮箱格式错误'
							}
						],
					},
					code: {
						rules: [{
							required: true,
							errorMessage: '邮箱验证码不能为空',

						}, ],
					},
					password: {
						rules: [{
								required: true,
								errorMessage: '密码不能为空'
							},
							{
								minLength: 5,
								maxLength: 15,
								errorMessage: '密码长度在 {minLength} 到 {maxLength} 个字符',
							}
						]
					}
				},
			}
		},
		methods: {
			async getLostCode() {
				console.log('获取邮箱验证码')
				this.emailUseStart = true
				this.emailUse = true
				let res = await this.$http.get('/email/forget/massage',{
					email:this.lostPwdForm.email
				})
				if(res.data.code==200){
					uni.showToast({
						title:'邮箱验证码发送成功'
					})
				}
				console.log('getLostCode',res)
			},
			async lostPwd() {
				this.$refs.lostPwdRefForm.validate().then(async (res) => {
				// 	console.log('忘记密码表单数据信息：', res);
					this.lostPwdForm.password = CryptoJS.Encrypt(this.lostPwdForm.password)
				let res1 = await this.$http.post('/email/password',{
					email:this.lostPwdForm.email,
					password:this.lostPwdForm.password,
					code:this.lostPwdForm.code
				}).then(res1=>{
					console.log('res1',res1)
					if(res1.data.code==200){
						uni.showToast({
							title:'密码修改成功',
							duration:800
						})
						setTimeout(function(){
							uni.navigateTo({
								url:'../login/login'
							})
						},1000)
					}
				})
				}).catch(err1 => {
					console.log('忘记密码表单错误信息：', err1);
				})
			},
			// 忘记密码
			async emailUseful(e) {
				if(e.detail.value){
					let res = await this.$http.get('/email',{
						email:this.lostPwdForm.email
					})
					if(res.data.code!=400){
						uni.showToast({
							title:'邮箱未被注册！',
							icon:'error'
						})
					}else{
						this.emailUse=false
					}
					console.log('emailUseful',res)
				}
				
			},
			// 忘记密码的时间到了
			timeUp() {
				this.second = 30
				this.count = '重新获取验证码'
				this.emailUseStart = false
				this.emailUse = false
			},
			toPage(page) {
				console.log('toPage', page)
				uni.navigateTo({
					url: `../` + page + '/' + page
				})
			}
		}
	}
</script>

<style lang="scss">
	image {
		width: 100%;
		height: 600rpx;
	}

	.button {
		display: flex;
		justify-content: space-between;
		margin: 20rpx;

		button {
			width: 150rpx;
			height: 90rpx;
		}
	}
</style>
