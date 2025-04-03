package com.example.barter_log.service;

import com.example.barter_authority.domain.form.PageForm;
import com.example.barter_log.domain.entity.BarterOperateDaily;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * (BarterOperateDaily)表服务接口
 *
 * @author makejava
 * @since 2021-11-22 16:21:45
 */
public interface BarterOperateDailyService {

    /**
     * 通过ID查询单条数据
     *
     * @param operateId 主键
     * @return 实例对象
     */
    BarterOperateDaily queryById(String operateId);

    /**
     * 分页查询
     *
     * @param barterOperateDaily 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<BarterOperateDaily> queryByPage(BarterOperateDaily barterOperateDaily, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param barterOperateDaily 实例对象
     * @return 实例对象
     */
    BarterOperateDaily insert(BarterOperateDaily barterOperateDaily);

    /**
     * 修改数据
     *
     * @param barterOperateDaily 实例对象
     * @return 实例对象
     */
    BarterOperateDaily update(BarterOperateDaily barterOperateDaily);

    /**
     * 通过主键删除数据
     *
     * @param operateId 主键
     * @return 是否成功
     */
    boolean deleteById(String operateId);

    /**
     * 通过分页查询日志
     * @param pageForm 分页信息
     * @return 日志信息
     */
    PageInfo<BarterOperateDaily> queryAllByPages(PageForm pageForm);

    /**
     * 导出所有日志
     * @param response HttpServletResponse
     */
    void export(HttpServletResponse response);

    /**
     * 删除所有日志
     */
    void deleteAllOperate();

    /**
     * 删除所有操作日志
     */
    void deleteAllException();

    /**
     * 查询指定日志
     * @param pageForm 分页信息
     * @param type 类型
     * @return 日志信息
     */
    PageInfo<BarterOperateDaily> queryByPages(PageForm pageForm, String type);
}
