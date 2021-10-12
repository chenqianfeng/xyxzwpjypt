package com.city.oa.service;

import com.alipay.api.AlipayApiException;
import com.city.oa.model.Order;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IOrderService {

    Order add(Integer commodityId, HttpServletRequest request) ;

    Boolean cancel(Integer id, HttpServletRequest request) ;

    Boolean delete(Integer id, HttpServletRequest request) ;

    Order getById(Integer id);

    Boolean confirm(Integer id, HttpServletRequest request) ;

    List<Order> getListByShopper(Integer status,HttpServletRequest request,Integer row,Integer page) ;

    Integer getCountByShopperAndStatus(HttpServletRequest request,Integer status) ;

    Boolean estimate(Integer id, String words,HttpServletRequest request) ;

    Boolean deleteEstimate(Integer id,HttpServletRequest request) ;

    String pay(Integer id,HttpServletRequest request) throws AlipayApiException;

    Boolean payNotify(int success, HttpServletRequest request);
}
