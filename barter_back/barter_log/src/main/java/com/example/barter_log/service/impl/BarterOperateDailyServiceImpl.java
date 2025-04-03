package com.example.barter_log.service.impl;

import com.example.barter_authority.domain.form.PageForm;
import com.example.barter_log.common.exception.ExcelException;
import com.example.barter_log.common.util.ExcelUtil;
import com.example.barter_log.domain.entity.BarterOperateDaily;
import com.example.barter_log.mapper.BarterOperateDailyMapper;
import com.example.barter_log.service.BarterOperateDailyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * (BarterOperateDaily)表服务实现类
 *
 * @author makejava
 * @since 2021-11-22 16:21:45
 */
@Service("barterOperateDailyService")
public class BarterOperateDailyServiceImpl implements BarterOperateDailyService {
    @Resource
    private BarterOperateDailyMapper barterOperateDailyDao;

    /**
     * 通过ID查询单条数据
     *
     * @param operateId 主键
     * @return 实例对象
     */
    @Override
    public BarterOperateDaily queryById(String operateId) {
        return this.barterOperateDailyDao.queryById(operateId);
    }

    /**
     * 分页查询
     *
     * @param barterOperateDaily 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<BarterOperateDaily> queryByPage(BarterOperateDaily barterOperateDaily, PageRequest pageRequest) {
        long total = this.barterOperateDailyDao.count(barterOperateDaily);
        return new PageImpl<>(this.barterOperateDailyDao.queryAllByLimit(barterOperateDaily, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param barterOperateDaily 实例对象
     * @return 实例对象
     */
    @Override
    public BarterOperateDaily insert(BarterOperateDaily barterOperateDaily) {
        this.barterOperateDailyDao.insert(barterOperateDaily);
        return barterOperateDaily;
    }

    /**
     * 修改数据
     *
     * @param barterOperateDaily 实例对象
     * @return 实例对象
     */
    @Override
    public BarterOperateDaily update(BarterOperateDaily barterOperateDaily) {
        this.barterOperateDailyDao.update(barterOperateDaily);
        return this.queryById(barterOperateDaily.getOperateId());
    }

    /**
     * 通过主键删除数据
     *
     * @param operateId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String operateId) {
        return this.barterOperateDailyDao.deleteById(operateId) > 0;
    }

    @Override
    public PageInfo<BarterOperateDaily> queryAllByPages(PageForm pageForm) {
        PageHelper.startPage(pageForm.getRequestPage(), pageForm.getPageSize());

        //查询整体
        List<BarterOperateDaily> barterOperateDailies = barterOperateDailyDao.queryAll();

        return new PageInfo<BarterOperateDaily>(barterOperateDailies);
    }

    @Override
    public void export(HttpServletResponse response) {
        List<BarterOperateDaily> barterOperateDailies = barterOperateDailyDao.queryAll();
        Class<? extends BarterOperateDaily> aClass = new BarterOperateDaily().getClass();
        LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap();
        for (Field declaredField : aClass.getDeclaredFields()) {
            linkedHashMap.put(declaredField.getName(),declaredField.getName());
        }
        try {
            ExcelUtil.listToExcel(barterOperateDailies,linkedHashMap,"异常日志" + new Date().toString(),response);
        } catch (ExcelException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAllOperate() {
        this.barterOperateDailyDao.deleteAllOperate();
    }

    @Override
    public void deleteAllException() {
        this.barterOperateDailyDao.deleteAllException();
    }

    @Override
    public PageInfo<BarterOperateDaily> queryByPages(PageForm pageForm, String type) {
        PageHelper.startPage(pageForm.getRequestPage(), pageForm.getPageSize());

        //查询整体
        List<BarterOperateDaily> barterOperateDailies = barterOperateDailyDao.queryByCondition(type);

        return new PageInfo<BarterOperateDaily>(barterOperateDailies);
    }
}
