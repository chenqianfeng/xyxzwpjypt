package com.city.oa.server;

import com.city.oa.model.CommoditySale;
import com.city.oa.model.Words;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface ICommoditySaleServer {
    int addCommoditySale(CommoditySale commoditySale, HttpServletRequest request);
    void deleteCommoditySale(Integer id);
    void updateCommoditySale(CommoditySale commoditySale);
    void modifyCommoditySaleStatus(Integer id, Integer status);
    void modifyCommoditySaleViewCount(Integer id);
    void modifyCommoditySaleToRecommend(Integer id);
    void modifyCommoditySaleCancelRecommend(Integer id);
    int isExistCommoditySale(int id);
    CommoditySale getCommoditySaleDetails(Integer id);
    int getCommoditySaleStatus(int id);
    Double getPriceById(int id);
    int getCountByCondition(Integer label,String key,HttpServletRequest request,Integer status,Integer isRecommend,Double minPrice,Double maxPrice);
    List<CommoditySale> getCommodityRecommendByLabelWithPage(Integer row, Integer page);
    List<CommoditySale> getCommoditySaleByStatusWithPage(int status, Integer row, Integer page);
    List<CommoditySale> getListCommoditySaleByShopperWithPage(HttpServletRequest request, Integer row, Integer page);
    List<CommoditySale> getListCommoditySaleByViewCountAndLabelWithPage(Integer label, Integer row, Integer page);
    List<CommoditySale> getCommoditySaleByLabelWithPage(Integer label, Integer row, Integer page);
    List<CommoditySale> getCommoditySaleByConditionWithPage(String key, Double min,Double max,Integer label,Integer row, Integer page);
    int addWordsForCommodity(Words words,HttpServletRequest request);
    Boolean deleteWordsForCommodity(int id,HttpServletRequest request);
    List<Words> getWordsByShopper(HttpServletRequest request);
    List<Words> getWordsByCommodity(Integer id);

    List<CommoditySale> getCommoditySoldByStatusWithPage(HttpServletRequest request, Integer row, Integer page);
}
