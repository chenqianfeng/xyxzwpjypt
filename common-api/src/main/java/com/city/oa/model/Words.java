package com.city.oa.model;

import lombok.Data;
//出售物品留言板类
@Data
public class Words {
    private Integer id;
    private String words;
    private String wordsTime;
    private Integer commodityId;
    private Integer shopperId;
}
