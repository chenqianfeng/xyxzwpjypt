package com.city.oa.server.impl;

import com.city.oa.dao.ILabelDao;
import com.city.oa.model.Label;
import com.city.oa.server.ILabelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LabelService implements ILabelService {

    @Resource
    ILabelDao dao;

    @Override
    public int addLabel(String key) {
        return dao.addLabel(key);
    }

    @Override
    public void deleteLabelById(Integer id) {
        dao.deleteLabelById(id);
    }

    @Override
    public List<Label> selectLabelByPage(Integer row, Integer page) {
        return dao.selectLabelByPage(row*(page-1),row);
    }

    @Override
    public Label selectLabelById(Integer id) {
        return dao.selectLabelById(id);
    }

    @Override
    public Label selectLabelByKey(String key) {
        return dao.selectLabelByKey(key);
    }

    @Override
    public void addLabelForCommodity(Integer cId, Integer[] lId) {
        dao.addLabelForCommodity(cId,lId);
    }

    @Override
    public void modifyLabelForCommodity(Integer cId, Integer[] lId) {
        dao.deleteLabelForCommodity(cId);
        dao.addLabelForCommodity(cId,lId);
    }

    @Override
    public List<Label> selectLabelByCommodityId(Integer id) {
        return dao.selectLabelByCommodityId(id);
    }
}
