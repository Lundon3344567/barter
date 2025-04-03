package com.example.barter_authority.common.util;

/**
 * @author walnut
 * @version 1.0
 * @className PatternUtil
 * @description 转义特殊字符(正则表达式)
 * @date 2021/12/9 8:56 下午
 */
public class PatternUtil {

    public static String escapeExprSpecialWord(String keyword) {

        if (keyword != null) {

            String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };

            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }
}
