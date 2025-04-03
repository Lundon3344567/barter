package com.example.barter_authority.common.util;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author walnut
 * @version 1.0
 * @className CompareUtils
 * @description TODO
 * @date 2021/12/21 23:39
 */
public class CompareUtil {
    public static List<Integer> getDifference(List<Integer> list1, List<Integer> list2) {
        Map<Integer, Integer> tempMap = list2.parallelStream().collect(Collectors.toMap(Function.identity(), Function.identity(), (oldData, newData) -> newData));
        return list1.parallelStream().filter(str-> !tempMap.containsKey(str)).collect(Collectors.toList());
    }
}
