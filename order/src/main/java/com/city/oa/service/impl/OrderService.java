package com.city.oa.service.impl;

import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.city.oa.config.AlipayConfig;
import com.city.oa.dao.IOrderDao;
import com.city.oa.model.CommoditySale;
import com.city.oa.model.Order;
import com.city.oa.model.Shopper;
import com.city.oa.service.ICommoditySaleService;
import com.city.oa.service.IOrderService;
import com.city.oa.utils.RedisUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class OrderService implements IOrderService {

    @Resource
    private IOrderDao dao;

    @Resource
    private ICommoditySaleService commodityService;

    @Resource
    private RedisUtil redis;

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd--hh-mm-ss");

    public Order add(Integer commodityId, HttpServletRequest request) {
        Order order = new Order();
        Shopper shopper = JSONUtil.toBean(redis.get(request.getHeader("token")), Shopper.class);
        CommoditySale sale = new CommoditySale();
        sale.setId(commodityId);
        order.setCommoditySale(sale);
        order.setShopper(shopper);
        order.setStatus(1);
        order.setPayMoney(commodityService.getCommoditySalePrice(commodityId).getData());
        order.setEstimate("");
        order.setCreateTime(format.format(new Date()));
        order.setPayTime("");
        dao.addOrder(order);
        return order;
    }

    public Boolean cancel(Integer id, HttpServletRequest request) {
        Order order = dao.selectOrderById(id);
        if (order.getStatus() != 1) return false;
        Shopper shopper = JSONUtil.toBean(redis.get(request.getHeader("token")), Shopper.class);
        if (order.getShopper().getId().equals(shopper.getId()) || shopper.getPower() > 2) {
            dao.modifyOrderStatus(id, 2);
            return true;
        }
        return false;
    }

    public String pay(Integer id, HttpServletRequest request) throws AlipayApiException {
        Order order = dao.selectOrderById(id);
        if (order.getStatus() > 2) return "订单状态异常！";
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = order.getId().toString();
        // 款金额，必填
        String total_amount = order.getPayMoney().toString();
        //订单名称，必填
        String subject = order.getCommoditySale().getName();
        //商品描述，可空
        String body = order.getCommoditySale().getIntroduce();

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String result = alipayClient.pageExecute(alipayRequest).getBody();

        return result;
    }

    public Boolean payNotify(int id, HttpServletRequest request) {
        Order order = dao.selectOrderById(id);
        System.out.println(order.toString());
        if (order.getStatus() != 1) return false;
        Shopper shopper = JSONUtil.toBean(redis.get(request.getHeader("token")), Shopper.class);
        if (order.getShopper().getId().equals(shopper.getId())) {
            System.out.println("修改订单付款时间、订单状态");
            dao.modifyOrderPayTime(id, format.format(new Date()));
            dao.modifyOrderStatus(id, 3);
            return true;
        }
        return false;
    }

    public Boolean confirm(Integer id, HttpServletRequest request) {
        Order order = dao.selectOrderById(id);
        if (order.getStatus() != 3) return false;
        Shopper shopper = JSONUtil.toBean(redis.get(request.getHeader("token")), Shopper.class);
        if (order.getShopper().getId().equals(shopper.getId())) {
            dao.modifyOrderSuccessTime(id, format.format(new Date()));
            dao.modifyOrderStatus(id, 4);
            return true;
        }
        return false;
    }

    public Boolean estimate(Integer id, String words, HttpServletRequest request) {
        Order order = dao.selectOrderById(id);
        if (order.getStatus() != 4) return false;
        Shopper shopper = JSONUtil.toBean(redis.get(request.getHeader("token")), Shopper.class);
        if (order.getShopper().getId().equals(shopper.getId())) {
            dao.estimateOrder(id, words);
            dao.modifyOrderEstimateTime(id, format.format(new Date()));
            dao.modifyOrderStatus(id, 5);
            return true;
        }
        return false;
    }

    public Boolean delete(Integer id, HttpServletRequest request) {
        Shopper shopper = JSONUtil.toBean(redis.get(request.getHeader("token")), Shopper.class);
        Order order = dao.selectOrderById(id);
        if (order.getShopper().getId().equals(shopper.getId()) || shopper.getPower() > 2) {
            dao.deleteOrder(id);
            return true;
        }
        return false;
    }

    public Order getById(Integer id) {
        return dao.selectOrderById(id);
    }

    public List<Order> getListByShopper(Integer status, HttpServletRequest request, Integer row, Integer page) {
        int id = JSONUtil.toBean(redis.get(request.getHeader("token")), Shopper.class).getId();
        List<Order> orders = dao.selectOrderByShopper(id);
        if (status == 0) return subMyList(orders,page,row);
        return subMyList(orders.stream().filter(s -> s.getStatus().equals(status)).collect(Collectors.toList()),page,row);
    }

    public Integer getCountByShopperAndStatus(HttpServletRequest request, Integer status) {
        int id = JSONUtil.toBean(redis.get(request.getHeader("token")), Shopper.class).getId();
        return dao.selectCountByCondition(id, status);
    }

    public Boolean deleteEstimate(Integer id, HttpServletRequest request) {
        Shopper shopper = JSONUtil.toBean(redis.get(request.getHeader("token")), Shopper.class);
        Order order = dao.selectOrderById(id);
        if (order.getShopper().getId().equals(shopper.getId()) || shopper.getPower() > 2) {
            dao.deleteEstimate(id);
            dao.modifyOrderEstimateTime(id,"");
            dao.modifyOrderStatus(id, 4);
            return true;
        }
        return false;
    }

    public List subMyList(List list, Integer pageNum, Integer pageSize) {
        if (list == null||pageNum<1||pageSize<1) {
            return null;
        }
        if (list.size() == 0) {
            return null;
        }

        Integer count = list.size(); //记录总数
        Integer pageCount = 0; //页数
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }

        int fromIndex = 0; //开始索引
        int toIndex = 0; //结束索引

        if (pageNum > pageCount) {
            pageNum = pageCount;
        }
        if (!pageNum.equals(pageCount)) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }

        List pageList = list.subList(fromIndex, toIndex);

        return pageList;
    }


}
