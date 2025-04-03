package com.barter.orderservice.common.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.barter.orderservice.domain.form.PageForm;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chengke
 * @version 1.0
 * @className PageUtil
 * @description 利用Page对象直接对List分页
 * @date 2021/11/24 17:03
 */
@Component
public class PageUtil {

    public static <T> PageInfo pageList(PageForm pageForm, List<T> list){
        Page page = new Page(pageForm.getRequestPage(),pageForm.getPageSize());
        int total = list.size();
        page.setTotal(total);

        int startIndex = (pageForm.getRequestPage()-1)*pageForm.getPageSize();
        int endIndex = Math.min(startIndex+pageForm.getPageSize(),total);

        page.addAll(list.subList(startIndex,endIndex));

        return (PageInfo<T>) new PageInfo(page);
    }

}