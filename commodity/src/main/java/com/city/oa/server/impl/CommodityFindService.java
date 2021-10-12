package com.city.oa.server.impl;

import cn.hutool.json.JSONUtil;
import com.city.oa.dao.ICommodityFindDao;
import com.city.oa.model.CommodityFind;
import com.city.oa.model.CommoditySale;
import com.city.oa.model.Shopper;
import com.city.oa.server.ICommodityFindService;
import com.city.oa.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CommodityFindService implements ICommodityFindService {

    @Resource
    ICommodityFindDao dao;

    @Resource
    private RedisUtil redis;

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public int addCommodityFind(CommodityFind commodityFind, HttpServletRequest request) {
        Shopper shopper = JSONUtil.parseObj(redis.get(request.getHeader("token"))).toBean(Shopper.class);
        commodityFind.setReleaseDate(format.format(new Date()));
        System.out.println(format.format(new Date()));
        commodityFind.setStatus(1);
        commodityFind.setShopper(shopper);
        dao.addCommodityFind(commodityFind);
        return commodityFind.getId();
    }

    @Override
    public Boolean deleteCommodityFind(Integer id, HttpServletRequest request) {
        Shopper shopper = JSONUtil.parseObj(redis.get(request.getHeader("token"))).toBean(Shopper.class);
        if (shopper.getPower()>1||shopper.getId()==dao.selectCommodityFindById(id).getShopper().getId()) {
            dao.deleteCommodityFindById(id);
            return true;
        }
        else
            return false;
    }

    @Override
    public void updateCommodityFind(CommodityFind commodityFind) {
        dao.modifyCommodityFind(commodityFind);
    }

    @Override
    public void modifyCommodityFindStatus(Integer id, Integer status) {
        dao.modifyCommodityFindStatus(id, status);
    }

    @Override
    public CommodityFind getCommodityFindDetails(Integer id) {
        return dao.selectCommodityFindById(id);
    }

    @Override
    public List<CommodityFind> getCommodityFindByConditionWithPage(String name, Integer shopperId, Integer status, Double minPrice, Double maxPrice, Integer row, Integer page) {
        return dao.getCommodityFindByConditionWithPage(name,shopperId,status,minPrice,maxPrice,row*(page-1),row);
    }

    @Override
    public int getCountByCondition(String name,Integer shopperId, Integer status,Double minPrice, Double maxPrice) {
        return dao.getCountByCondition(name,shopperId,status,minPrice,maxPrice);
    }

    @Override
    public List<CommodityFind> getListCommodityFindByShopperWithPage(HttpServletRequest request, Integer row, Integer page){
        Integer shopperId = 0;
        if (request!=null) {
            Shopper shopper = JSONUtil.parseObj(redis.get(request.getHeader("token"))).toBean(Shopper.class);
            shopperId = shopper.getId();
        }
        return dao.getCommodityFindByConditionWithPage("",shopperId,-1,0d,0d,row*(page-1),row);
    }
    @Override
    public int getCountByShopper(HttpServletRequest request){
        Integer shopperId = 0;
        if (request!=null) {
            Shopper shopper = JSONUtil.parseObj(redis.get(request.getHeader("token"))).toBean(Shopper.class);
            shopperId = shopper.getId();
        }
        return dao.getCountByCondition("",shopperId,-1,0d,0d);
    }

}
