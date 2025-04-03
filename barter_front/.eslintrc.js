module.exports = {
    root: true,
    parserOptions: {
        sourceType: 'module'
    },
    env: {
        browser: true,
    },
    plugins: [
        'vue'
    ],
    'rules': {
        'no-underscore-dangle': 2, //不允许标识符以下划线开头
        'no-extra-parens': 2, //不允许出现多余的括号
        'no-empty': 2, //不允许出现空的代码块
        'no-empty-character-class': 2, //正则表达式中不允许出现空的字符组
        'guard-for-in': 0, //监视for in循环，防止出现不可预料的情况
        'no-self-compare': 2, //不允许自己和自己比较
        'func-names': 0, //函数表达式必须有名字
        'vue/singleline-html-element-content-newline': 'off',//数组间不换行
        'vue/no-v-html': 'error',//禁止未声明的变量
        'accessor-pairs': 2,//在对象和类中强制使用getter/setter对
        'brace-style': [2, '1tbs', {//允许块的左大括号和右大括号在同一行
            'allowSingleLine': true
        }],
        'camelcase': [0, {//对属性名称强制使用驼峰风格
            'properties': 'always'
        }],
        'comma-dangle': [2, 'never'],//禁止在对象和数组文字中使用尾随逗号
        'no-mixed-spaces-and-tabs': [2, false],//禁止混用tab和空格
        'no-extra-semi': 2,//禁止多余的冒号
        'no-new': 1,//禁止在使用new构造一个实例后不赋值
        'no-redeclare': 2,//禁止重复声明变量
        'no-undef': 1,//不能有未定义的变量
        'indent': ['error', 'tab'],//缩进为tab
        'quotes': ['error', 'single'],//全用单引号
        'semi': [2, 'always'],//语句强制分号结尾
    }
}
