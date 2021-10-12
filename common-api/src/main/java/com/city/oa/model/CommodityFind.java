package com.city.oa.model;

import lombok.Data;

import java.util.List;

//求购商品类
@Data
public class CommodityFind {
    private Integer id;
    private String name;//求购商品名
    private Double price;//价格
    private String rules;//求购要求
    private String releaseDate;//发布日期
    private Integer status;//交易状态 0：取消求购  1：正在求购   2：求购成功

    private List<Label> label;
    private Shopper shopper;
}
