package com.city.oa.server.impl;

import cn.hutool.json.JSONUtil;
import com.city.oa.dao.ICommoditySaleDao;
import com.city.oa.dao.IWordsDao;
import com.city.oa.model.CommoditySale;
import com.city.oa.model.Shopper;
import com.city.oa.model.Words;
import com.city.oa.server.ICommoditySaleServer;
import com.city.oa.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CommoditySaleService implements ICommoditySaleServer {

    @Resource
    private ICommoditySaleDao dao;


    @Resource
    private IWordsDao wordsDao;

    @Resource
    private RedisUtil redis;


    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public int addCommoditySale(CommoditySale commoditySale, HttpServletRequest request) {
        Shopper shopper = JSONUtil.parseObj(redis.get(request.getHeader("token"))).toBean(Shopper.class);
        commoditySale.setViewCount(0);
        commoditySale.setStatus(1);
        commoditySale.setIsRecommend(0);
        commoditySale.setReleaseDate(format.format(new Date()));
        commoditySale.setShopper(shopper);
        dao.addCommoditySale(commoditySale);
        return commoditySale.getId();
    }

    @Override
    public void deleteCommoditySale(Integer id) {
        dao.deleteCommoditySaleById(id);
    }

    @Override
    public void updateCommoditySale(CommoditySale commoditySale) {
        commoditySale.setReleaseDate(format.format(new Date()));
        dao.modifyCommoditySale(commoditySale);
    }

    @Override
    public void modifyCommoditySaleStatus(Integer id, Integer status) {
        dao.modifyCommoditySaleStatus(id, status);
    }

    @Override
    public void modifyCommoditySaleViewCount(Integer id) {
        dao.modifyCommoditySaleViewCount(id);
    }

    @Override
    public CommoditySale getCommoditySaleDetails(Integer id) {
        modifyCommoditySaleViewCount(id);
        CommoditySale commoditySale = dao.selectCommoditySaleById(id);
        return commoditySale;
    }

    @Override
    public int getCommoditySaleStatus(int id){
        return dao.selectStatusById(id);
    }

    @Override
    public Double getPriceById(int id){
        return dao.selectPriceById(id);
    }

    @Override
    public int getCountByCondition(Integer label, String key,HttpServletRequest request, Integer status,Integer isRecommend,Double min, Double max) {
        Integer shopperId = null;
        if (request!=null) {
            Shopper shopper = JSONUtil.parseObj(redis.get(request.getHeader("token"))).toBean(Shopper.class);
            shopperId = shopper.getId();
        }
        return dao.selectCountByCondition(label,key,shopperId,status,isRecommend,min,max);
    }

    ;

    @Override
    public void modifyCommoditySaleToRecommend(Integer id){
        dao.modifyCommoditySaleToRecommend(id);
    }

    @Override
    public void modifyCommoditySaleCancelRecommend(Integer id){
        dao.modifyCommoditySaleCancelRecommend(id);
    }

    @Override
    public List<CommoditySale> getListCommoditySaleByShopperWithPage(HttpServletRequest request, Integer row, Integer page) {
        Shopper shopper = JSONUtil.parseObj(redis.get(request.getHeader("token"))).toBean(Shopper.class);
        return dao.selectListCommoditySaleByShopperWithPage(shopper.getId(), row * (page - 1), row);
    }

    @Override
    public List<CommoditySale> getCommodityRecommendByLabelWithPage( Integer row, Integer page) {
        return dao.selectListCommodityRecommendByLabelWithPage(row * (page - 1), row);
    }

    @Override
    public List<CommoditySale> getCommoditySaleByStatusWithPage(int status, Integer row, Integer page) {
        return dao.getCommoditySaleByStatusWithPage(status, row * (page - 1), row);
    }

    @Override
    public List<CommoditySale> getListCommoditySaleByViewCountAndLabelWithPage(Integer label, Integer row, Integer page){
        return dao.selectListCommoditySaleByViewCountAndLabelWithPage(label,row * (page - 1), row);
    }

    @Override
    public List<CommoditySale> getCommoditySaleByLabelWithPage(Integer label, Integer row, Integer page) {
        return dao.selectListCommoditySaleByLabelWithPage(label, row * (page - 1), row);
    }

    @Override
    public List<CommoditySale> getCommoditySaleByConditionWithPage(String key, Double min, Double max, Integer label, Integer row, Integer page) {
        return dao.selectListCommoditySaleByConditionWithPage(key, min, max, label, row * (page - 1), row);
    }

    @Override
    public int addWordsForCommodity(Words words,HttpServletRequest request) {
        Shopper shopper = JSONUtil.toBean(redis.get(request.getHeader("token")),Shopper.class);
        words.setShopperId(shopper.getId());
        words.setWordsTime(format.format(new Date()));
        return wordsDao.addWordsForCommodity(words);
    }

    @Override
    public Boolean deleteWordsForCommodity(int id,HttpServletRequest request) {
        Words words = wordsDao.selectWordsById(id);
        Shopper shopper = JSONUtil.parseObj(redis.get(request.getHeader("token"))).toBean(Shopper.class);
        if (shopper.getPower()>1||shopper.getId().equals(words.getShopperId())) {
            wordsDao.deleteWordsForCommodity(id,request);
            return true;
        }
        return false;
    }


    @Override
    public List<Words> getWordsByShopper(HttpServletRequest request){
        Shopper shopper = JSONUtil.parseObj(redis.get(request.getHeader("token"))).toBean(Shopper.class);
        return wordsDao.selectWordsByShopperId(shopper.getId());
    }

    @Override
    public List<Words> getWordsByCommodity(Integer id) {
        return wordsDao.selectWordsByCommodityId(id);
    }

    @Override
    public List<CommoditySale> getCommoditySoldByStatusWithPage(HttpServletRequest request, Integer row, Integer page) {
        Shopper shopper = JSONUtil.parseObj(redis.get(request.getHeader("token"))).toBean(Shopper.class);
        return dao.getCommoditySaleSoldWithPage(shopper.getId(),row * (page - 1), row);
    }

    @Override
    public int isExistCommoditySale(int id) {
        return dao.isExistCommoditySale(id);
    }
}
