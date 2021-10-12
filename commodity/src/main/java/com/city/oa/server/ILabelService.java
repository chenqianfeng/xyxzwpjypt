package com.city.oa.server;

import com.city.oa.model.Label;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ILabelService {
    int addLabel(String key);
    void deleteLabelById(Integer id);
    List<Label> selectLabelByPage(Integer row, Integer page);
    Label selectLabelById(Integer id);
    Label selectLabelByKey(String key);
    //与商品关联的操作
    void addLabelForCommodity(Integer cId,Integer[] lId);
    void modifyLabelForCommodity(Integer cId,Integer[] lId);
    List<Label> selectLabelByCommodityId(Integer Id);
}
