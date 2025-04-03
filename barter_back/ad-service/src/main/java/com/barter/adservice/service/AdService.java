package com.barter.adservice.service;

import com.github.pagehelper.PageInfo;
import com.barter.adservice.domain.entity.BarterAdInfo;
import com.barter.adservice.domain.form.PageForm;

import java.util.List;

/**
 * @ClassName AdService
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-11-10 13:42
 * @Version 1.0
 **/
public interface AdService {

    /**
     *广告增加
     * @param barterAdInfo
     * @return
     */
    int adAdd(BarterAdInfo barterAdInfo);

    /**
     * 广告删除
     * @param adId
     * @return
     */
    int adDeleteById(long adId);

    /**
     *广告修改
     * @param barterAdInfo
     * @return
     */
    int adModify(BarterAdInfo barterAdInfo);


    /**
     * 分页查询广告
     * @param pageForm
     * @return
     */
    PageInfo<BarterAdInfo> queryAdByPageAll(PageForm pageForm);

    /**
     * 根据时间排序
     * @param pageForm
     * @return
     */
    PageInfo<BarterAdInfo> adSelectOrderBy(PageForm pageForm);

    /**
     * 查询所有广告
     * @return
     */
    List<BarterAdInfo> adSelect();

    /**
     * 根据id查询单条广告
     * @param adId
     * @return
     */
    BarterAdInfo selectAdById(long adId);


}
