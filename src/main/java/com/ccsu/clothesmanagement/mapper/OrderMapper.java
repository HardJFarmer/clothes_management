package com.ccsu.clothesmanagement.mapper;

import com.ccsu.clothesmanagement.domain.Order;
import com.ccsu.clothesmanagement.domain.WareHouse;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    /**
     * 增加新的订单
     */
    int addOrder(Order order);

    /**
     * 删除一个订单
     */
    int deleteOrder(Order order);

    /**
     * 修改订单信息
     */
    int updateOrder(Order order);

    /**
     * 通过多条件查询仓库与订单关系信息
     */
    List<Order> selectOrderAndWareHouse(Order order);

    int delOrderByList(List<Integer> ids);
}
