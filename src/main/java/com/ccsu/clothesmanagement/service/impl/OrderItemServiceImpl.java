package com.ccsu.clothesmanagement.service.impl;

import com.ccsu.clothesmanagement.domain.CargoAndWareHouse;
import com.ccsu.clothesmanagement.domain.OrderItem;
import com.ccsu.clothesmanagement.mapper.CargoMapper;
import com.ccsu.clothesmanagement.mapper.OrderItemMapper;
import com.ccsu.clothesmanagement.service.OrderItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private CargoMapper cargoMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addOutOrderItem(OrderItem orderItem, int warehouseId) {
        int cargoId = orderItem.getCargoId();
        CargoAndWareHouse cargoAndWareHouse = cargoMapper.selectAccountByCargiIdAndWareHouseId(cargoId, warehouseId);
        int account = cargoAndWareHouse.getAccount();
        orderItem.setJudgeStatus(0);
        OrderItem orderItemByCarid = isOrderItemByCarid(orderItem);
        if (account >= orderItem.getChangeAccount()) {
            account = account - orderItem.getChangeAccount();
            cargoMapper.updateAccountByCargiIdAndWareHouseId(cargoId, warehouseId, account);
            if (orderItemByCarid == null) {
                int row = orderItemMapper.addOrderItem(orderItem);
                return row;
            } else {
                orderItem.setCargoId(orderItemByCarid.getCargoId());
                orderItem.setOrderitemId(orderItemByCarid.getOrderitemId());
                orderItem.setChangeAccount(orderItem.getChangeAccount() + orderItemByCarid.getChangeAccount());
                int row = orderItemMapper.updateOrderItem(orderItem);
                return row;
            }
        } else {
            return 0;
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteOutOrderItem(OrderItem orderItem, int warehouseId) {
        int cargoId = orderItem.getCargoId();
        CargoAndWareHouse cargoAndWareHouse = cargoMapper.selectAccountByCargiIdAndWareHouseId(cargoId, warehouseId);
        System.out.println(cargoAndWareHouse);
        int account = cargoAndWareHouse.getAccount();
        orderItem.setJudgeStatus(0);
        account = account + orderItem.getChangeAccount();
        int row = orderItemMapper.deleteOrderItem(orderItem);
        cargoMapper.updateAccountByCargiIdAndWareHouseId(cargoId, warehouseId, account);
        return row;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateOutOrderItem(OrderItem orderItem, int warehouseId) {
        int oldchangeaccount = orderItemMapper.selectChangeByOrderItemId(orderItem.getOrderitemId());
        int newchangeaccount = orderItem.getChangeAccount();
        int cargoId = orderItem.getCargoId();
        CargoAndWareHouse cargoAndWareHouse = cargoMapper.selectAccountByCargiIdAndWareHouseId(cargoId, warehouseId);
        int account = cargoAndWareHouse.getAccount();
        orderItem.setJudgeStatus(0);
        account = account + oldchangeaccount - newchangeaccount;
        if (account >= 0) {
            int row = orderItemMapper.updateOrderItem(orderItem);
            cargoMapper.updateAccountByCargiIdAndWareHouseId(cargoId, warehouseId, account);
            return row;
        } else {
            return 0;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addInOrderItem(OrderItem orderItem, int warehouseId) {
        //获取当前订单详细id
        int cargoId = orderItem.getCargoId();
        //查询当前仓库当前货品的库存
        CargoAndWareHouse cargoAndWareHouse = cargoMapper.selectAccountByCargiIdAndWareHouseId(cargoId, warehouseId);
        //初始化
        int account = 0;
        //获取当前货品在该仓库的库存
        if (cargoAndWareHouse != null) {
            account = cargoAndWareHouse.getAccount();
        }
        //获取当前之前库存数量 + 订单详细入库数量
        account = account + orderItem.getChangeAccount();
        //设置为入库状态
        orderItem.setJudgeStatus(1);
        //判断当入库单是否存在该货品的入库纪录
        OrderItem orderItemByCarid = isOrderItemByCarid(orderItem);
        //数据库更新条数
        int row;
        //如果不存在，直接insert数据
        if (orderItemByCarid == null) {
            //插入订单详细表
            row = orderItemMapper.addOrderItem(orderItem);
        } else {
            //存在则update数量,新添加数量+已经存在数量
            orderItem.setChangeAccount(orderItem.getChangeAccount() + orderItemByCarid.getChangeAccount());
            orderItem.setOrderitemId(orderItemByCarid.getOrderitemId());
            //update数量
            row = orderItemMapper.updateOrderItem(orderItem);
        }
        //根据仓库id和货品id查询当前库存纪录是否存在
        if(isExistStock(cargoId, warehouseId)){
            //插入一条库存纪录
            cargoMapper.addCargoStock(new CargoAndWareHouse(cargoId, warehouseId, account));
        }else {
            //更新库存
            cargoMapper.updateAccountByCargiIdAndWareHouseId(cargoId, warehouseId, account);
        }
        return row;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteInOrderItem(OrderItem orderItem, int warehouseId) {
        //获得当前详细的货品id
        int cargoId = orderItem.getCargoId();
        //查询库存
        CargoAndWareHouse cargoAndWareHouse = cargoMapper.selectAccountByCargiIdAndWareHouseId(cargoId, warehouseId);
        int account = cargoAndWareHouse.getAccount();
        //减去当前删除详细数据的数量
        account = account - orderItem.getChangeAccount();
        // 删除订单详细
        int row = orderItemMapper.deleteOrderItem(orderItem);
        //更新库存
        cargoMapper.updateAccountByCargiIdAndWareHouseId(cargoId, warehouseId, account);
        return row;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateInOrderItem(OrderItem orderItem, int warehouseId) {
        //没有修改前的订单明细数量
        int oldchangeaccount = orderItemMapper.selectChangeByOrderItemId(orderItem.getOrderitemId());
        //修改后的订单明细数量
        int newchangeaccount = orderItem.getChangeAccount();
        //货品id
        int cargoId = orderItem.getCargoId();
        //查询库存
        CargoAndWareHouse cargoAndWareHouse = cargoMapper.selectAccountByCargiIdAndWareHouseId(cargoId, warehouseId);
        //获得当前库存数
        int account = cargoAndWareHouse.getAccount();
        //判断修改后库存量
        account = account - oldchangeaccount + newchangeaccount;
        if(account >= 0){
            //库存足够修改明细
            int row = orderItemMapper.updateOrderItem(orderItem);
            //更新库存
            cargoMapper.updateAccountByCargiIdAndWareHouseId(cargoId, warehouseId, account);
            return row;
        }
        return 0;
    }

    @Override
    public PageInfo<OrderItem> selectOrderItemAndCargoAndAccount(int pageNum, int pageSize, OrderItem orderItem) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<OrderItem> pageInfo = new PageInfo<>(orderItemMapper.selectOrderItemAndCargoAndAccount(orderItem),5);
        return pageInfo;
    }

    /**
     * 判断该货品的明细是否重复，重复返回明细ld号，不重复返回0
     */
    private OrderItem isOrderItemByCarid(OrderItem orderItem) {
        OrderItem orderItem1 = new OrderItem(orderItem.getCargoId(), orderItem.getOrderId());
        List<OrderItem> orderItems = orderItemMapper.selectOrderItemAndCargoAndAccount(orderItem1);
        if (orderItems.size() == 0) {
            return null;
        }
        OrderItem orderItem2 = orderItems.get(0);
        return orderItem2;
    }

    private boolean isExistStock(int cargoId, int warehouseId) {
        CargoAndWareHouse cargoAndWareHouse = cargoMapper.selectAccountByCargiIdAndWareHouseId(cargoId, warehouseId);
        if (cargoAndWareHouse != null){
            //存在
            return false;
        }else {
            //不存在
            return true;
        }
    }

}
