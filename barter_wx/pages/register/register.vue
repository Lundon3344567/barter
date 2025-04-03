<template>
	<view class="register">
		<!-- 易物图片 -->
		<image src="../../static/barter.png"></image>
		<!-- 注册界面 -->
		<uni-forms class="registerForm" ref="registerRefForm" :rules="rules" :modelValue="registerForm" :border="true">
			<uni-forms-item label="邮箱" name="email" required>
				<input type="text" v-model="registerForm.email" placeholder="请输入邮箱" @blur="emailUsel" />
			</uni-forms-item>
			<uni-forms-item label="用户名" name="userName" required="">
				<input type="text" v-model="registerForm.userName" placeholder="请输入用户名" @blur="UserNameUsel" />
			</uni-forms-item>
			<uni-forms-item label="验证码" required name="code">
				<input type="text" v-model="registerForm.code" placeholder="请输入邮箱验证码" />
				<button type="primary" size="mini" :disabled="useNotFul" @click="getLostCodell">{{countll}}</button>
				<uni-countdown color="#fff" background-color="#007aff" :start="start" :showDay="false"
					:hour="0" :minute="0" :second="seconds" @timeup="timeUpll"></uni-countdown>
			</uni-forms-item>
			<uni-forms-item label="密码" required name="password">
				<input type="password" v-model="registerForm.password" placeholder="请输入密码" />
			</uni-forms-item>
			<view class="button">
				<button type="primary" @click="register">注册</button>
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
				registerForm: {
					email: '',
					password: '',
					code: '',
					veritifyCode: '52d7218e-9a91-4379-aafe-36d9564c9d89',
					userName: ''
				},
				seconds: 30,
				// 用户名和邮箱都是否可用
				useNotFul: true,
				// 倒计时是否可以用
				start: false,
				// 用户名是否可用
				nameUse:false,
				// 邮箱是否可用
				emailUse:false,
				countll: '获取验证码',
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
					userName: {
						rules: [{
							required: true,
							errorMessage: '用户名不能为空',
						}]
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
			async register() {
				this.$refs.registerRefForm.validate().then(async (res) => {
					// console.log('注册表单数据信息：', res);
					// 邮箱接口验证
					let res1 = await this.$http.post('/email', {
						email: this.registerForm.email,
						code: this.registerForm.code
					});
						console.log('res1', res)
						this.registerForm.password = CryptoJS.Encrypt(this.registerForm.password)
						// 注册验证
						let  res2 = await this.$http.post('/user/register', {
							email: this.registerForm.email,
							password: this.registerForm.password,
							code: this.registerForm.veritifyCode,
							username: this.registerForm.userName
						});
						if(res2.data.code==200){
							uni.showToast({
								title:'注册成功，请前往登录',
								icon:'success',
								duration:800
							})
							setTimeout(function(){
								uni.navigateTo({
									url:'../login/login'
								},900)
							})
						}else{
							uni.showToast({
								title:'注册有误',
								icon:'error',
								duration:800
							})
						}
						
						console.log('res2',res2)

				}).catch(err => {
					console.log('注册表单错误信息：', err);
				})
			},
			// 用户名是否可用
			async UserNameUsel() {
				let res = await this.$http.get('/user',{
					username:this.registerForm.userName
				})
				if(res.data.code!=200){
					uni.showToast({
						title:'用户名已存在',
						icon:'error'
					})
					this.nameUse = false
				}else{
					this.nameUse=true
				}
				if(this.nameUse&&this.emailUse){
					this.useNotFul = false
				}
				console.log('UserNameUsel',res)
			},
			// 注册
			async emailUsel(e) {
				console.log('e', e.detail.value)
				if (e.detail.value) {
					let  res = await this.$http.get('/email', {
						email: e.detail.value
					})
					console.log('email', e.detail.value)
					console.log('emailUsel', res)
					if (res.data.code == 400) {
						uni.showToast({
							title: '邮箱已被注册',
							icon: 'error'
						})
					} else if (res.data.code == 200) {
						this.emailUse = true
					}
					if(this.nameUse&&this.emailUse){
						this.useNotFul = false
					}
				}
			},
			// 注册的时间到了
			timeUpll() {
				this.seconds = 30
				this.countll = '重新获取验证码'
				this.useNotFul = false
			},
			async getLostCodell() {
				this.useNotFul = true
				this.start = true
				this.seconds = 30
				let  res = await this.$http.get('/email/register/massage', {
					email: this.registerForm.email
				})
				console.log('获取邮箱验证码')
				this.emailUsellStart = true
				this.emailUsell = true
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
	.register {
		width: 100%;
		text-align: center;
	}

	image {
		text-align: center;
		width: 500rpx;
		height: 500rpx;
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
