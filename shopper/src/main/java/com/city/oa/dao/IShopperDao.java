package com.city.oa.dao;

import com.city.oa.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IShopperDao {
    int addShopper(Shopper shopper);
    void updateShopper(Shopper shopper);
    void updatePassword(@Param("id") Integer id, @Param("password")String password);
    void updateQuestion(@Param("id")Integer id,@Param("question") Integer question,@Param("answer") String answer);
    Shopper selectById(Integer id);
    Shopper selectByPhone(String phone);
    int haveQuestion(@Param("id") Integer id);
    int verifyAnswer(@Param("id") Integer id,@Param("answer") String answer);
    int verifyPassword(@Param("id") Integer id,@Param("password") String password);
    int verifyPasswordByPhone(@Param("phone") String phone, @Param("password") String password);
    List<CommoditySale> selectCollectByShopperId(@Param("id") Integer id,@Param("start") Integer start,@Param("row") Integer row);
    void addToCollect(@Param("shopperId") Integer shopperId,@Param("commodityId") Integer commodityId);
    void deleteFromCollect(@Param("shopperId") Integer shopperId,@Param("commodityId") Integer commodityId);
    List<Question> getListQuestion(@Param("start") Integer start,@Param("row") Integer row);
    Question getQuestionById(Integer id);

    void changeCredit(@Param("id")Integer shopperId, @Param("credit")Integer credit);
    void changePower(@Param("id")Integer shopperId, @Param("power")Integer power);


}
