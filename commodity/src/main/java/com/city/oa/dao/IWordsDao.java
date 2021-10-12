package com.city.oa.dao;

import com.city.oa.model.Words;
import org.apache.ibatis.annotations.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//被商品服务调用
@Mapper
public interface IWordsDao {
    int addWordsForCommodity(Words words);
    Boolean deleteWordsForCommodity(Integer id, HttpServletRequest request);
    Words selectWordsById(Integer Id);
    List<Words> selectWordsByCommodityId(Integer Id);
    List<Words> selectWordsByShopperId(Integer Id);
}
