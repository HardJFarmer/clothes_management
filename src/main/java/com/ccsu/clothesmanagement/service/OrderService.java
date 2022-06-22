package com.ccsu.clothesmanagement.service;

import com.ccsu.clothesmanagement.domain.Order;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface OrderService {
    /**
     * 增加新的出库订单
     */
    int addOutOrder(Order order);

    /**
     * 删除一个出库订单
     */
    int deleteOutOrder(Order order);

    /**
     * 修改出库订单信息
     */
    int updateOutOrder(Order order);

    /**
     * 通过仓库id号查询出库的仓库与货品关系信息
     */
    PageInfo<Order> selectOutOrderAndWareHouse(int pageNum, int pageSize, Order order);

    /**
     * 增加新的入库订单
     */
    int addInOrder(Order order);

    /**
     * 删除一个入库订单
     */
    int deleteInOrder(Order order);

    /**
     * 修改入库订单信息
     */
    int updateInOrder(Order order);

    /**
     * 查询入库的仓库与货品关系信息
     */
    PageInfo<Order> selectInOrderAndWareHouse(int pageNum, int pageSize, Order order);

    void delOrderByList(List<Integer> ids);
}
