// const baseUrl = "http://localhost:8081"
export default {
	config: {
		baseUrl: "http://localhost:8081",
		header: {
			'Content-Type': 'application/json;charset=UTF-8',
			'Authorization':  uni.getStorageSync('token'),
		},
		data: {},
		method: "GET",
		dataType: "json",
		withCredentials: true
	},
	request(options = {}) {
		// options.header || 
		options.header = this.config.header;
		options.method = options.method || this.config.method;
		options.dataType = options.dataType || this.config.dataType;
		options.url = this.config.baseUrl + options.url;
		options.data = options.data
		// return uni.request(options);
		return options
	},
	// get 方法
	 get(url, data, optionss = {}) {
		optionss.url = url;
		optionss.data = data;
		optionss.method = 'GET';
		
		optionss = this.request(optionss)
		// let token = uni.getStorageSync('token')
		console.log('options',this.config.header)
	
		return new Promise((resolve, reject) => {
			// console.log('options',options)
			uni.request({
				url: optionss.url,
				timeout: 15000,
				data: optionss.data,
				method: optionss.method,
				header: this.config.header,
				success: (res) => {
					// console.log('getRes', res)
					if (res.data.code == 500||res.data.code == 410||res.data.code == 423) {
						uni.showToast({
							title: '登录已过期',
							icon: 'error'
						})
						// uni.clearStorageSync()

					} else {
						resolve(res)
					}
				},
				fail: (res) => {
					reject(res)
				}
			})
		})

	},
	// post 方法
	post(url, data, optionss = {}) {
		optionss.url = url;
		optionss.data = data;
		optionss.method = 'POST';
		optionss = this.request(optionss)
		return new Promise((resolve,reject) =>{
			uni.request({
				url: optionss.url,
				timeout: 15000,
				data: optionss.data,
				method: optionss.method,
				header: optionss.header,
				success: (res) => {
					// console.log('getRes', res)
					if (res.data.code == 500||res.data.code == 410||res.data.code == 423) {
						uni.showToast({
							title: '登录已过期',
							icon: 'error'
						})
						// uni.clearStorageSync()
					} else {
						resolve(res)
					}
				},
				fail: (res) => {
					reject(res)
				}
			})
		})
	},
	// PUT方法
	put(url, data, optionss = {}) {
		optionss.url = url;
		optionss.data = data;
		optionss.method = 'PUT';
		optionss = this.request(optionss)
		return new Promise((resovle,reject) =>{
			uni.request({
				url: optionss.url,
				timeout: 15000,
				data: optionss.data,
				method: optionss.method,
				header: optionss.header,
				success: (res) => {
					// console.log('getRes', res)
					if (res.data.code == 500||res.data.code == 410||res.data.code == 423) {
						uni.showToast({
							title: '登录已过期',
							icon: 'error'
						})
						// uni.clearStorageSync()
					} else {
							resovle(res)
					}
				},
				fail: (res) => {
					reject(res)
				}
			})
		})
	},
	// DELETE方法
	delete(url, data, optionss = {}) {
		optionss.url = url;
		optionss.data = data;
		optionss.method = 'DELETE';
		optionss = this.request(optionss)
		return new Promise((resolve,reject)=>{
			uni.request({
				url: optionss.url,
				timeout: 15000,
				data: optionss.data,
				method: optionss.method,
				header: optionss.header,
				success: (res) => {
					// console.log('getRes', res)
					if (res.data.code == 500||res.data.code == 410||res.data.code == 423) {
						uni.showToast({
							title: '登录已过期',
							icon: 'error'
						})
						// uni.clearStorageSync()
					} else {
						resolve(res)
					}
				},
				fail: (res) => {
					reject(res)
				}
			})
		})
	}
}
