package com.ccsu.clothesmanagement.service;

import com.ccsu.clothesmanagement.domain.OrderItem;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface OrderItemService {

    int addOutOrderItem(OrderItem orderItem,int warehouseId);

    int deleteOutOrderItem(OrderItem orderItem,int warehouseId);

    int updateOutOrderItem(OrderItem orderItem,int warehouseId);

    int addInOrderItem(OrderItem orderItem,int warehouseId);

    int deleteInOrderItem(OrderItem orderItem,int warehouseId);

    int updateInOrderItem(OrderItem orderItem,int warehouseId);

    PageInfo<OrderItem> selectOrderItemAndCargoAndAccount(int pageNum,int pageSize,OrderItem orderItem);
}
