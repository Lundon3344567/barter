package com.example.barter_good.mapper;

import com.example.barter_good.domain.entity.BarterGoodsCollect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * (BarterGoodsCollect)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-09 16:05:45
 */
@Mapper
public interface BarterGoodsCollectMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param collectId 主键
     * @return 实例对象
     */
    BarterGoodsCollect queryById(long collectId);

    long selectGoodsById(long collectId);

    /**
     * 查询指定行数据
     *
     * @param barterGoodsCollect 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<BarterGoodsCollect> queryAllByLimit(BarterGoodsCollect barterGoodsCollect, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param barterGoodsCollect 查询条件
     * @return 总行数
     */
    long count(BarterGoodsCollect barterGoodsCollect);

    /**
     * 新增数据
     *
     * @param barterGoodsCollect 实例对象
     * @return 影响行数
     */
    int insert(BarterGoodsCollect barterGoodsCollect);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterGoodsCollect> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<BarterGoodsCollect> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterGoodsCollect> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<BarterGoodsCollect> entities);

    /**
     * 修改数据
     *
     * @param barterGoodsCollect 实例对象
     * @return 影响行数
     */
    int update(BarterGoodsCollect barterGoodsCollect);


    int deleteById(long goodsId,long userId);

    long countByGoodid(long goodsId);

    long countByUserid(long userId);

    List<BarterGoodsCollect> selectByUserid(long userId);

    /**
     * 查看商品是否被用户收藏
     * @param goodsId
     * @param userId
     * @return
     */
    int collectByGoodsidUserid(long goodsId, long userId);


}

