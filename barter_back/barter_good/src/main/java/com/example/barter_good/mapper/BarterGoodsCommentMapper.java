package com.example.barter_good.mapper;

import com.example.barter_good.domain.entity.BarterGoodsComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (BarterGoodsComment)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-09 16:05:46
 */
@Mapper
public interface BarterGoodsCommentMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param commentId 主键
     * @return 实例对象
     */
    BarterGoodsComment queryById(Long commentId);

    List<BarterGoodsComment> queryByGoodsId(long goodsId);

    /**
     * 查询指定行数据
     *
     * @param barterGoodsComment 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<BarterGoodsComment> queryAllByLimit(BarterGoodsComment barterGoodsComment, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param barterGoodsComment 查询条件
     * @return 总行数
     */
    long count(BarterGoodsComment barterGoodsComment);

    /**
     * 新增数据
     *
     * @param barterGoodsComment 实例对象
     * @return 影响行数
     */
    int insert(BarterGoodsComment barterGoodsComment);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterGoodsComment> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<BarterGoodsComment> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterGoodsComment> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<BarterGoodsComment> entities);

    /**
     * 修改数据
     *
     * @param barterGoodsComment 实例对象
     * @return 影响行数
     */
    int update(BarterGoodsComment barterGoodsComment);

    /**
     * 通过主键删除数据
     *
     * @param commentId 主键
     * @return 影响行数
     */
    int deleteById(Long commentId);

}

