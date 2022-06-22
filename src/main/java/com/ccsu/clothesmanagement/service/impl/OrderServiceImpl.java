package com.ccsu.clothesmanagement.service.impl;

import com.ccsu.clothesmanagement.domain.Order;
import com.ccsu.clothesmanagement.domain.WareHouse;
import com.ccsu.clothesmanagement.mapper.OrderMapper;
import com.ccsu.clothesmanagement.mapper.WareHouseMapper;
import com.ccsu.clothesmanagement.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public int addOutOrder(Order order){
        order.setJudgeStatus(0);
        order.setOrderNumber("CK" + getNumber());
        int row=orderMapper.addOrder(order);
        return row;
    }

    @Override
    public int deleteOutOrder(Order order){
        order.setJudgeStatus(0);
        int row=orderMapper.deleteOrder(order);
        return row;
    }

    @Override
    public int updateOutOrder(Order order){
        order.setJudgeStatus(0);
        int row=orderMapper.updateOrder(order);
        return row;
    }

    @Override
    public PageInfo<Order> selectOutOrderAndWareHouse(int pageNum,int pageSize,Order order){
        order.setJudgeStatus(0);
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Order> pageInfo = new PageInfo<>(orderMapper.selectOrderAndWareHouse(order),5);
        return pageInfo;
    }

    @Override
    public int addInOrder(Order order){
        order.setJudgeStatus(1);
        order.setOrderNumber("RK" + getNumber());
        int row=orderMapper.addOrder(order);
        return row;
    }

    @Override
    public int deleteInOrder(Order order){
        order.setJudgeStatus(1);
        int row=orderMapper.deleteOrder(order);
        return row;
    }

    @Override
    public int updateInOrder(Order order){
        order.setJudgeStatus(1);
        int row=orderMapper.updateOrder(order);
        return row;
    }

    /**
     * 查询入库的仓库与货品关系信息
     */
    @Override
    public PageInfo<Order> selectInOrderAndWareHouse(int pageNum,int pageSize,Order order){
        order.setJudgeStatus(1);
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Order> pageInfo = new PageInfo<>(orderMapper.selectOrderAndWareHouse(order),5);
        return pageInfo;
    }

    @Override
    public void delOrderByList(List<Integer> ids) {
        orderMapper.delOrderByList(ids);
    }

    /**
     *
     * @return
     */
    private String getNumber(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String strDate = sdf.format(date);
        return strDate;
    }
}
