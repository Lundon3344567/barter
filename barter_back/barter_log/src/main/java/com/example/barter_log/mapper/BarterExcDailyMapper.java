package com.example.barter_log.mapper;

import com.example.barter_log.domain.entity.BarterExcDaily;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (BarterExcDaily)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-22 16:22:08
 */
@Mapper
public interface BarterExcDailyMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param excId 主键
     * @return 实例对象
     */
    BarterExcDaily queryById(String excId);

    /**
     * 查询指定行数据
     *
     * @param barterExcDaily 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<BarterExcDaily> queryAllByLimit(BarterExcDaily barterExcDaily, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param barterExcDaily 查询条件
     * @return 总行数
     */
    long count(BarterExcDaily barterExcDaily);

    /**
     * 新增数据
     *
     * @param barterExcDaily 实例对象
     * @return 影响行数
     */
    int insert(BarterExcDaily barterExcDaily);


    /**
     * 通过主键删除数据
     *
     * @param excId 主键
     * @return 影响行数
     */
    int deleteById(String excId);

    /**
     * 查询所有
     * @return 所有的异常日志信息
     */
    List<BarterExcDaily> queryAll();
}

