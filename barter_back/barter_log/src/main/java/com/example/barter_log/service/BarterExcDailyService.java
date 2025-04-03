package com.example.barter_log.service;

import com.example.barter_authority.domain.form.PageForm;
import com.example.barter_log.domain.entity.BarterExcDaily;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * (BarterExcDaily)表服务接口
 *
 * @author makejava
 * @since 2021-11-22 16:22:08
 */
public interface BarterExcDailyService {

    /**
     * 通过ID查询单条数据
     *
     * @param excId 主键
     * @return 实例对象
     */
    BarterExcDaily queryById(String excId);

    /**
     * 分页查询
     *
     * @param barterExcDaily 筛选条件
     * @param pageRequest    分页对象
     * @return 查询结果
     */
    Page<BarterExcDaily> queryByPage(BarterExcDaily barterExcDaily, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param barterExcDaily 实例对象
     * @return 实例对象
     */
    BarterExcDaily insert(BarterExcDaily barterExcDaily);


    /**
     * 通过主键删除数据
     *
     * @param excId 主键
     * @return 是否成功
     */
    boolean deleteById(String excId);

    /**
     * 通过分页查询异常信息
     *
     * @param pageForm 分页信息
     * @return 异常日志
     */
    PageInfo<BarterExcDaily> queryAllByPages(PageForm pageForm);

    /**
     * 导出异常日志
     *
     * @param response HttpServletResponse
     */
    void export(HttpServletResponse response);
}
