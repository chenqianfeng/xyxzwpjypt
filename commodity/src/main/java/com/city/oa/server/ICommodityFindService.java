package com.city.oa.server;

import com.city.oa.model.CommodityFind;
import com.city.oa.model.CommoditySale;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface ICommodityFindService {
    int addCommodityFind(CommodityFind commodityFind, HttpServletRequest request);
    Boolean deleteCommodityFind(Integer id, HttpServletRequest request);
    void updateCommodityFind(CommodityFind commodityFind);
    void modifyCommodityFindStatus(Integer id,Integer status);
    CommodityFind getCommodityFindDetails(Integer id);
    List<CommodityFind> getCommodityFindByConditionWithPage(String name, Integer shopperId, Integer status, Double minPrice, Double maxPrice, Integer row, Integer page);
    int getCountByCondition(String name,Integer shopperId, Integer status,Double minPrice, Double maxPrice);
    List<CommodityFind> getListCommodityFindByShopperWithPage(HttpServletRequest request, Integer row, Integer page);
    int getCountByShopper(HttpServletRequest request);
}
