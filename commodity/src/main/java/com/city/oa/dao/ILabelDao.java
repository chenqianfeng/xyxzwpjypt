package com.city.oa.dao;

import com.city.oa.model.Label;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ILabelDao {

    //标签自身的操作
    int addLabel(String key);
    void deleteLabelById(Integer id);
    List<Label> selectLabelByPage(@Param("start") Integer start, @Param("row") Integer row);
    Label selectLabelById(Integer id);
    Label selectLabelByKey(String key);

    //与商品关联的操作
    void addLabelForCommodity(@Param("cId") Integer cId,@Param("lId") Integer[] lId);
    void deleteLabelForCommodity(@Param("cId") Integer cId);
    List<Label> selectLabelByCommodityId(Integer Id);

}
