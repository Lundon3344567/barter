import CryptoJS from 'crypto-js/crypto-js'
import Vue from 'vue'
import Mock from "mockjs";

const KEY = CryptoJS.enc.Utf8.parse("barter");
// const KEY = Mock.mock('string|5-20','@string')
// console.log('key',KEY)
// const IV = CryptoJS.enc.Utf8.parse("barter");

export function Encrypt(word, keyStr) {
    // 判断是否存在ksy，不存在就用定义好的key
    keyStr = keyStr ? keyStr : KEY;
    var key = CryptoJS.enc.Utf8.parse(keyStr);
    var srcs = CryptoJS.enc.Utf8.parse(word);
    var encrypted = CryptoJS.AES.encrypt(srcs, key, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });
    return encrypted.toString();
}

export function Decrypt(word, keyStr, ) {
    keyStr = keyStr ? keyStr : KEY;
    var key = CryptoJS.enc.Utf8.parse(keyStr);

    let base64 = CryptoJS.enc.Base64.parse(word);
    let src = CryptoJS.enc.Base64.stringify(base64);

    var decrypt = CryptoJS.AES.decrypt(src, key, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });

    var decryptedStr = decrypt.toString(CryptoJS.enc.Utf8);
    return decryptedStr.toString();
}
//计算路径
export function Calpath(path){
    return '/'+Encrypt(path)
}
export function  array_remove_repeat(a) { // 去重
    var r = [];
    for (var i = 0; i < a.length; i++) {
        var flag = true;
        var temp = a[i];
        for (var j = 0; j < r.length; j++) {
            if (temp === r[j]) {
                flag = false;
                break;
            }
        }
        if (flag) {
            r.push(temp);
        }
    }
    return r;
}
export function array_difference(a, b) { // 差集 a - b

    //clone = a
    var clone = a.slice(0);
    for (var i = 0; i < b.length; i++) {
        var temp = b[i];
        for (var j = 0; j < clone.length; j++) {
            if (temp === clone[j]) {
                //remove clone[j]
                clone.splice(j, 1);
            }
        }
    }
    return array_remove_repeat(clone)
}

export default new Vue
