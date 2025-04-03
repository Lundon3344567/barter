package com.example.barter_log.service.impl;

import com.example.barter_authority.domain.form.PageForm;
import com.example.barter_log.common.exception.ExcelException;
import com.example.barter_log.common.util.ExcelUtil;
import com.example.barter_log.domain.entity.BarterExcDaily;
import com.example.barter_log.mapper.BarterExcDailyMapper;
import com.example.barter_log.service.BarterExcDailyService;
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
 * (BarterExcDaily)表服务实现类
 *
 * @author makejava
 * @since 2021-11-22 16:22:08
 */
@Service("barterExcDailyService")
public class BarterExcDailyServiceImpl implements BarterExcDailyService {
    @Resource
    private BarterExcDailyMapper barterExcDailyDao;

    /**
     * 通过ID查询单条数据
     *
     * @param excId 主键
     * @return 实例对象
     */
    @Override
    public BarterExcDaily queryById(String excId) {
        return this.barterExcDailyDao.queryById(excId);
    }

    /**
     * 分页查询
     *
     * @param barterExcDaily 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<BarterExcDaily> queryByPage(BarterExcDaily barterExcDaily, PageRequest pageRequest) {
        long total = this.barterExcDailyDao.count(barterExcDaily);
        return new PageImpl<>(this.barterExcDailyDao.queryAllByLimit(barterExcDaily, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param barterExcDaily 实例对象
     * @return 实例对象
     */
    @Override
    public BarterExcDaily insert(BarterExcDaily barterExcDaily) {
        this.barterExcDailyDao.insert(barterExcDaily);
        return barterExcDaily;
    }



    /**
     * 通过主键删除数据
     *
     * @param excId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String excId) {
        return this.barterExcDailyDao.deleteById(excId) > 0;
    }

    @Override
    public PageInfo<BarterExcDaily> queryAllByPages(PageForm pageForm) {
        PageHelper.startPage(pageForm.getRequestPage(), pageForm.getPageSize());
        //查询整体
        List<BarterExcDaily> barterExcDailies = barterExcDailyDao.queryAll();

        return new PageInfo<BarterExcDaily>(barterExcDailies);
    }

    @Override
    public void export(HttpServletResponse response) {

        List<BarterExcDaily> barterExcDailies = barterExcDailyDao.queryAll();
        Class<? extends BarterExcDaily> aClass = new BarterExcDaily().getClass();
        LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap();
        for (Field declaredField : aClass.getDeclaredFields()) {
            linkedHashMap.put(declaredField.getName(),declaredField.getName());
        }
        try {
            ExcelUtil.listToExcel(barterExcDailies,linkedHashMap,"异常日志" + new Date().toString(),response);
        } catch (ExcelException e) {
            e.printStackTrace();
        }
    }
}
