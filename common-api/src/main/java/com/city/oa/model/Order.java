package com.city.oa.model;

import lombok.Data;

//订单类
@Data
public class Order {
    private Integer id;
    private String createTime;
    private String payTime;
    private String successTime;
    private Integer status;//1:已创建 2：已取消 3：已付款 4：已收货 5：已评价
    private Double payMoney;
    private String estimate;
    private CommoditySale commoditySale;
    private Shopper shopper;
}
