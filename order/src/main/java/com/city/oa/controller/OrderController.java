package com.city.oa.controller;

import com.city.oa.dto.CommonResult;
import com.city.oa.model.Order;
import com.city.oa.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Resource
    private IOrderService service;

    @GetMapping("/add")
    public CommonResult<String> add(Integer commodityId, HttpServletRequest request) {
        Order order = service.add(commodityId, request);
        if (order != null)
            return new CommonResult<>(200, "新建订单成功", order.getId().toString());
        return new CommonResult<>(420, "新建订单失败", null);
    }

    @GetMapping("/cancel")
    public CommonResult<String> cancel(Integer id, HttpServletRequest request) {
        if (service.cancel(id, request))
            return new CommonResult<>(200, "取消订单成功", "ok");
        return new CommonResult<>(421, "取消订单失败", "没有权限");
    }

    @GetMapping("/delete")
    public CommonResult<String> delete(Integer id, HttpServletRequest request) {
        if ( service.delete(id, request))
            return new CommonResult<>(200, "删除订单成功", "ok");
        return new CommonResult<>(422, "删除订单失败", "ok");
    }

    @GetMapping("/get/{id}")
    public CommonResult<Order> getById(@PathVariable Integer id) {
        Order order = service.getById(id);
        if (order!=null)
            return new CommonResult<>(200, "获取订单成功", order);
        return new CommonResult<>(423, "获取订单成失败", null);
    }

    @GetMapping("/shopper/list/{status}")
    public CommonResult<List<Order>> statusList(HttpServletRequest request,
                                                @PathVariable Integer status,
                                                @RequestParam(defaultValue = "10", required = false) Integer row,
                                                @RequestParam(defaultValue = "1", required = false) Integer page) {
        List<Order> orders = service.getListByShopper(status, request, row, page);
        int count = service.getCountByShopperAndStatus(request, status);
        return new CommonResult<>(200, String.valueOf(count), orders);
    }

    @GetMapping("/estimate")
    public CommonResult<String> addEstimate(Integer id, String word, HttpServletRequest request) {
        if (service.estimate(id, word, request))
            return new CommonResult<>(200, "订单评论成功", "ok");
        return new CommonResult<>(424, "订单评论失败", "error");
    }

    @GetMapping("/estimate/delete")
    public CommonResult<String> deleteEstimate(Integer id, HttpServletRequest request) {
        if (service.deleteEstimate(id, request))
            return new CommonResult<>(200, "删除订单评论成功", "ok");
        return new CommonResult<>(425, "删除订单评论失败", "error");
    }

    @GetMapping("/pay")//付款
    public CommonResult<String> pay(Integer id, HttpServletRequest request) throws Exception {
        return new CommonResult<>(200, "请付款", service.pay(id, request));
    }

    @GetMapping("/pay/notify/{id}")//付款同步操作
    public CommonResult payNotify(@PathVariable Integer id, HttpServletRequest request) {
        return !service.payNotify(id, request)
                ? new CommonResult(426, "订单状态更新失败",false)
                : new CommonResult(200, "订单状态更新成功",true);
    }

    @GetMapping("/confirm")
    public CommonResult<String> confirm(Integer id, HttpServletRequest request) {

        return service.confirm(id, request)== true ?
                new CommonResult(200, "订单状态更新成功") :
                new CommonResult(427, "订单状态更新失败");
    }
}
