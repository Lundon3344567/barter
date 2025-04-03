<template>
	<view class="login"> 
		<!-- 易物图片 -->
		<image src="../../static/barter.png"></image>
		<!-- 登录界面 -->
		<uni-forms ref="userRefForm" :rules="rules" :modelValue="userForm" :border="true" class="form">
			<uni-forms-item label="用户名" name="userName" required>
				<input type="text" v-model="userForm.userName" placeholder="请输入用户名" />
			</uni-forms-item>

			<uni-forms-item label="密码" name="password" required>
				<input type="text" v-model="userForm.password" password placeholder="请输入密码" />
			</uni-forms-item>
			<view class="button">
				<button type="primary" @click="submit">登录</button>
				<button type="warn" @click="toPage('register')">注册</button>
				<!-- 忘记密码 -->
				<view @click="toPage('lstPwd')" style="color: #007AFF;font-size: 35rpx;margin-top: 10rpx;">
					忘记密码
				</view>
			</view>

		</uni-forms>


	</view>
</template>

<script>
	const CryptoJS = require('../../utils/crypto.js')
	export default {
		data() {
			return {
				flag: 1,
				userForm: {
					password: '',
					userName: '',
					code: '52d7218e-9a91-4379-aafe-36d9564c9d89'
				},
				rules: {
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
		onLoad() {
			// this.getPhoto()
		},
		methods: {
			bindData(info, value) {
				console.log(info, value)
			},
			async submit() {
				this.$refs.userRefForm.validate().then(async (res) => {
					console.log('登录表单数据信息：', res);
					// let [err2,res2] = await this.$http.get('/captcha')

					this.userForm.password = CryptoJS.Encrypt(this.userForm.password);

					uni.request({
						url: 'http://localhost:8081/login',
						method: 'POST',
						data: {
							username: this.userForm.userName,
							password: this.userForm.password,
							code: this.userForm.code
						},
						header: {
							'Content-Type': 'application/x-www-form-urlencoded'
						},
						success: (res) => {
							console.log('login', res)
							if (res.data.code == 200) {
								uni.showToast({
									title: '登录成功',
									icon: 'success',
									duration: 800
								})
								if (res.data.data.userState != 1) {
									uni.showToast({
										title: '用户状态异常',
										icon: 'error'
									})
								} else {
									// uni.setStorage({
									// 	key:'userId',
									// 	data:res.data.data.userId
									// })
									// uni.setStorage({
									// 	key:'userName',
									// 	data:res.data.data.username
									// })
									uni.setStorageSync('userId', res.data.data.userId)
									uni.setStorageSync('userName', res.data.data.username)
									uni.setStorageSync('token',res.header.Authorization)
									
									// uni.setStorage({
									// 	key:'token',
									// 	data:res.header.Authorization
									// })
										setTimeout(function(){
											uni.switchTab({
												url:'../my/my',
											})
										},900)
								}
							} else {
								uni.showToast({
									title: '用户名或者密码错误',
									icon: 'error',
									duration: 800
								})
								this.userForm.password = ''
								this.userForm.userName = ''
							}
						}
					})
					console.log('login', res)
				}).catch(err => {
					console.log('登录表单错误信息：', err);
				})

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
	.login {
		font-size: 50rpx;
		width: 100%;
		text-align: center;

		image {
			width: 500rpx;
			height: 500rpx;
		}

		.form {}

		.registerForm {}

		.button {
			display: flex;
			justify-content: space-between;
			margin: 20rpx;

			button {
				width: 150rpx;
				height: 90rpx;
			}
		}

	}
</style>
