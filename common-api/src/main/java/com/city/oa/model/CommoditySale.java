package com.city.oa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//出售商品类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommoditySale {

    private Integer id;//出售商品编号
    private String name;//出售商品名
    private Integer shabby;//出售新旧程度
    private Integer viewCount;//商品浏览次数
    private Integer status;//商品交易状态  0：待上架  1：已上架   2：下架    3：已售
    private Double price;//出售商品价格
    private String introduce;//出售商品描述
    private String releaseDate;//出售商品发布日期
    private Integer isRecommend;//是否是推荐商品 0：不推荐 1：推荐

    private Photo photo;//商品照片
    private List<Label> label;//商品标签
    private List<Words> words;//商品的留言
    private Shopper shopper;//商品出售人

    public CommoditySale(Integer id, String name, Integer shabby, Integer viewCount, Integer status, Double price, String introduce, String releaseDate, Integer isRecommend) {
        this.id = id;
        this.name = name;
        this.shabby = shabby;
        this.viewCount = viewCount;
        this.status = status;
        this.price = price;
        this.introduce = introduce;
        this.releaseDate = releaseDate;
        this.isRecommend = isRecommend;
    }
}
