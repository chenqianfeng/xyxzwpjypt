package com.city.oa.dao;

import com.city.oa.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IOrderDao {
    int addOrder(Order order);
    void deleteOrder(Integer id);
    int modifyOrderStatus(@Param("id") Integer id, @Param("status") Integer status);
    int modifyOrderSuccessTime(@Param("id")Integer id,@Param("time")String time);
    int modifyOrderPayTime(@Param("id")Integer id,@Param("time")String time);
    int modifyOrderEstimateTime(@Param("id")Integer id,@Param("time")String time);
    Order selectOrderById(Integer id);
    List<Order> selectOrderByShopper(@Param("shopperId")Integer shopperId);
    Integer selectCountByCondition(@Param("shopperId")Integer shopperId,@Param("status")Integer status);
    void estimateOrder(@Param("id") Integer id,@Param("words") String words);
    void deleteEstimate(Integer id);
}
