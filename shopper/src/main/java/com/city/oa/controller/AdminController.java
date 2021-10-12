package com.city.oa.controller;

import cn.hutool.json.JSONUtil;
import com.city.oa.dto.CommonResult;
import com.city.oa.model.Shopper;
import com.city.oa.service.AdminService;
import com.city.oa.utils.RedisUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class AdminController {

    @Resource
    private AdminService service;

    @Resource
    private RedisUtil template;

    @GetMapping("/shopper/credit")
    CommonResult<String> changeCredit(Integer shopperId,Integer credit, HttpServletRequest request){
        Shopper shopper = JSONUtil.toBean(template.get(request.getHeader("token")), Shopper.class);
        if (shopper.getPower()>1) {
            String message = service.changeCredit(shopperId,credit);
            return new CommonResult<>(200,message,"ok");
        }
        return new CommonResult<>(410, "你没有管理员权限", null);
    }

    @GetMapping("/shopper/toAdmin")
    CommonResult<String> upPower(Integer id, HttpServletRequest request){
        Shopper shopper = JSONUtil.toBean(template.get(request.getHeader("token")), Shopper.class);
        if (shopper.getPower()>2) {
            service.changePower(id, 2);
            return new CommonResult<>(200, "升为管理员管理员成功", null);
        }
        return new CommonResult<>(410, "你没有超级管理员权限", null);
    }

    @GetMapping("/shopper/cancelAdmin")
    CommonResult<String> downPower(Integer shopperId, HttpServletRequest request){
        Shopper shopper = JSONUtil.toBean(template.get(request.getHeader("token")), Shopper.class);
        if (shopper.getPower()>2) {
            service.changePower(shopperId,1);
            return new CommonResult<>(200, "取消管理员权限成功", null);
        }
        return new CommonResult<>(410, "你没有超级管理员权限", null);
    }
}
