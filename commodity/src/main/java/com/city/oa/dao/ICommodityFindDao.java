package com.city.oa.dao;

import com.city.oa.model.CommodityFind;
import com.city.oa.model.CommoditySale;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ICommodityFindDao {
    //增加物品
    int addCommodityFind(CommodityFind c);

    //删除物品
    void deleteCommodityFindById(Integer id);

    //根据Id修改物品信息
    void modifyCommodityFind(CommodityFind c);

    //根据Id修改物品求购状态
    void modifyCommodityFindStatus(@Param("id") Integer id,
                                   @Param("status") Integer status);

    //根据id查看求购信息
    CommodityFind selectCommodityFindById(int id);

    //通过特定条件查找符合的物品
    List<CommodityFind> getCommodityFindByConditionWithPage(@Param("name")String name,
                                                            @Param("shopperId")Integer shopperId,
                                                            @Param("status")Integer status,
                                                            @Param("minPrice")Double minPrice,
                                                            @Param("maxPrice")Double maxPrice,
                                                            @Param("start") Integer start,
                                                            @Param("row") Integer row);


    int getCountByCondition(@Param("name")String name,
                            @Param("shopperId")Integer shopperId,
                            @Param("status")Integer status,
                            @Param("minPrice")Double minPrice,
                            @Param("maxPrice")Double maxPrice);

}
