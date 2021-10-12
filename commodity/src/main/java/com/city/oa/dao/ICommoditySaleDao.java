package com.city.oa.dao;

import com.city.oa.model.CommoditySale;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ICommoditySaleDao {

    //增加物品
    int addCommoditySale(CommoditySale c);

    //删除物品
    void deleteCommoditySaleById(Integer id);

    //根据Id修改物品信息
    void modifyCommoditySale(CommoditySale c);

    //根据Id修改物品售卖状态
    void modifyCommoditySaleStatus(@Param("id") Integer id, @Param("status") Integer status);

    //将商品设置为推荐商品
    void modifyCommoditySaleToRecommend(@Param("id")Integer id);

    void modifyCommoditySaleCancelRecommend(@Param("id")Integer id);

    //根据Id自增物品浏览量
    void modifyCommoditySaleViewCount(@Param("id")Integer id);



    //根据ID查商品详细信息
    CommoditySale selectCommoditySaleById(@Param("id")Integer id);


    //查看某类别下的商品
    List<CommoditySale> selectListCommoditySaleByLabelWithPage(@Param("label")Integer label, @Param("start")Integer start,@Param("row") Integer row);

    //查看某人购买的的商品
    List<CommoditySale> selectListCommoditySaleByShopperWithPage(@Param("shopperId")Integer shopperId,@Param("start")Integer start,@Param("row") Integer row);

    //查看某类别下的推荐商品
    List<CommoditySale> selectListCommodityRecommendByLabelWithPage(@Param("start")Integer start,@Param("row") Integer row);

    //查看某类别下的热度最高商品
    List<CommoditySale> selectListCommoditySaleByViewCountAndLabelWithPage(@Param("label")Integer label, @Param("start")Integer start,@Param("row") Integer row);

    //通过特定条件查找符合的物品
    List<CommoditySale> selectListCommoditySaleByConditionWithPage(@Param("name") String name,
                                                                   @Param("minPrice")   Double minPrice,
                                                                   @Param("maxPrice")  Double maxPrice,
                                                                   @Param("label")  Integer label,
                                                                   @Param("start")int start,
                                                                   @Param("row")Integer row);

    int isExistCommoditySale(int id);

    List<CommoditySale> getCommoditySaleByStatusWithPage(@Param("status")int status,
                                                         @Param("start")int start,
                                                         @Param("row")Integer row);

    List<CommoditySale> getCommoditySaleSoldWithPage(@Param("shopperId")int shopperId,
                                                     @Param("start")int start,
                                                     @Param("row")Integer row);
    int selectStatusById(int id);

    Double selectPriceById(int id);

    int selectCountByCondition(@Param("label")Integer label,
                               @Param("name")String key,
                               @Param("shopperId")Integer shopperId,
                               @Param("status")Integer status,
                               @Param("isRecommend")Integer isRecommend,
                               @Param("minPrice")Double min,
                               @Param("maxPrice")Double max);
}
