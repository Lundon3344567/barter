package com.example.barter_good.mapper;

import com.example.barter_good.domain.entity.BarterGoodsClassify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (BarterGoodsClassify)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-09 16:05:45
 */
@Mapper
public interface BarterGoodsClassifyMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param classifyId 主键
     * @return 实例对象
     */
    BarterGoodsClassify queryById(Long classifyId);

    /**
     * 查询指定行数据
     *
     * @param barterGoodsClassify 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<BarterGoodsClassify> queryAllByLimit(BarterGoodsClassify barterGoodsClassify, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param barterGoodsClassify 查询条件
     * @return 总行数
     */
    long count(BarterGoodsClassify barterGoodsClassify);

    /**
     * 新增数据
     *
     * @param barterGoodsClassify 实例对象
     * @return 影响行数
     */
    int insert(BarterGoodsClassify barterGoodsClassify);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterGoodsClassify> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<BarterGoodsClassify> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterGoodsClassify> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<BarterGoodsClassify> entities);

    /**
     * 修改数据
     *
     * @param barterGoodsClassify 实例对象
     * @return 影响行数
     */
    int update(BarterGoodsClassify barterGoodsClassify);

    /**
     * 通过主键删除数据
     *
     * @param classifyId 主键
     * @return 影响行数
     */
    int deleteById(Long classifyId);

    /**
     * 通过父类id筛选
     * @param parentId parentId
     * @return List<BarterGoodsClassify>
     */
    List<BarterGoodsClassify> selectByParentId(long parentId);

    /**
     * 查询全部
     * @return List<BarterGoodsClassify>
     */
    List<BarterGoodsClassify> selectTree();

    /**
     * 获取父级id
     * @param classifyId classifyId
     * @return long
     */
    long selectparent(long classifyId);

    int countParent();

    /**
     * 通过二级id获取三级id
     * @param id id
     * @return List<Long>
     */
    List<Long> queryThirdLevelId(Long id);

}

