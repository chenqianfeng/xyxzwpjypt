package com.city.oa.service;

import com.city.oa.dao.IShopperDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminService {

    @Resource
    IShopperDao dao;

    public String changeCredit(Integer shopperId, Integer credit) {
        dao.changeCredit(shopperId,credit);
        return "积分修改成功";
    }

    public void changePower(Integer shopperId, Integer power) {
        dao.changePower(shopperId,power);
    }

}
