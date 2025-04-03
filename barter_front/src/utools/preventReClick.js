import Vue from 'vue'

const preventReClick = Vue.directive('preventReClick', {
    inserted: function (el, binding) {
        el.addEventListener('click', () => {
            if (!el.disabled) {
                this.$message.info('请勿频繁点击')
                el.disabled = true
                setTimeout(() => {
                    el.disabled = false
                }, binding.value || 3000)
            }
        })
    }
});
/**
 * 防抖
 * @param {Function} func 要执行的回调函数
 * @param {Number} wait 延时的时间
 * @param {Boolean} immediate 是否立即执行
 * @return null
 */
let timeout;
export function Debounce(func, wait=300, immediate = false) {
    // 清除定时器
    if (timeout !== null) clearTimeout(timeout);
    // 立即执行，此类情况一般用不到
    if (immediate) {
        var callNow = !timeout;
        timeout = setTimeout(function() {
            timeout = null;
        }, wait);
        if (callNow) typeof func === 'function' && func();
    } else {
        // 设置定时器，当最后一次操作后，timeout不会再被清除，所以在延时wait毫秒后执行func回调方法
        timeout = setTimeout(function() {
            typeof func === 'function' && func();
        }, wait);
    }
}
/**
 * 节流
 * @param {Function} func 要执行的回调函数
 * @param {Number} wait 延时的时间
 * @param {Boolean} immediate 是否立即执行
 * @return null
 */
let timer, flag;
export function throttle(func, wait = 300, immediate = true) {
    if (immediate) {
        if (!flag) {
            flag = true;
            // 如果是立即执行，则在wait毫秒内开始时执行
            typeof func === 'function' && func();
            timer = setTimeout(() => {
                console.log('placeOrder1')
                flag = false;
            }, wait);
        }
    } else {
        if (!flag) {
            flag = true
            // 如果是非立即执行，则在wait毫秒内的结束处执行
            timer = setTimeout(() => {
                flag = false
                typeof func === 'function' && func();
                console.log('placeOrder1')
            }, wait);
        }

    }
};
export { preventReClick }
