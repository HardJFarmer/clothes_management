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
        //????????????????????????id
        int cargoId = orderItem.getCargoId();
        //???????????????????????????????????????
        CargoAndWareHouse cargoAndWareHouse = cargoMapper.selectAccountByCargiIdAndWareHouseId(cargoId, warehouseId);
        //?????????
        int account = 0;
        //???????????????????????????????????????
        if (cargoAndWareHouse != null) {
            account = cargoAndWareHouse.getAccount();
        }
        //?????????????????????????????? + ????????????????????????
        account = account + orderItem.getChangeAccount();
        //?????????????????????
        orderItem.setJudgeStatus(1);
        //??????????????????????????????????????????????????????
        OrderItem orderItemByCarid = isOrderItemByCarid(orderItem);
        //?????????????????????
        int row;
        //????????????????????????insert??????
        if (orderItemByCarid == null) {
            //?????????????????????
            row = orderItemMapper.addOrderItem(orderItem);
        } else {
            //?????????update??????,???????????????+??????????????????
            orderItem.setChangeAccount(orderItem.getChangeAccount() + orderItemByCarid.getChangeAccount());
            orderItem.setOrderitemId(orderItemByCarid.getOrderitemId());
            //update??????
            row = orderItemMapper.updateOrderItem(orderItem);
        }
        //????????????id?????????id????????????????????????????????????
        if(isExistStock(cargoId, warehouseId)){
            //????????????????????????
            cargoMapper.addCargoStock(new CargoAndWareHouse(cargoId, warehouseId, account));
        }else {
            //????????????
            cargoMapper.updateAccountByCargiIdAndWareHouseId(cargoId, warehouseId, account);
        }
        return row;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteInOrderItem(OrderItem orderItem, int warehouseId) {
        //???????????????????????????id
        int cargoId = orderItem.getCargoId();
        //????????????
        CargoAndWareHouse cargoAndWareHouse = cargoMapper.selectAccountByCargiIdAndWareHouseId(cargoId, warehouseId);
        int account = cargoAndWareHouse.getAccount();
        //???????????????????????????????????????
        account = account - orderItem.getChangeAccount();
        // ??????????????????
        int row = orderItemMapper.deleteOrderItem(orderItem);
        //????????????
        cargoMapper.updateAccountByCargiIdAndWareHouseId(cargoId, warehouseId, account);
        return row;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateInOrderItem(OrderItem orderItem, int warehouseId) {
        //????????????????????????????????????
        int oldchangeaccount = orderItemMapper.selectChangeByOrderItemId(orderItem.getOrderitemId());
        //??????????????????????????????
        int newchangeaccount = orderItem.getChangeAccount();
        //??????id
        int cargoId = orderItem.getCargoId();
        //????????????
        CargoAndWareHouse cargoAndWareHouse = cargoMapper.selectAccountByCargiIdAndWareHouseId(cargoId, warehouseId);
        //?????????????????????
        int account = cargoAndWareHouse.getAccount();
        //????????????????????????
        account = account - oldchangeaccount + newchangeaccount;
        if(account >= 0){
            //????????????????????????
            int row = orderItemMapper.updateOrderItem(orderItem);
            //????????????
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
     * ?????????????????????????????????????????????????????????ld?????????????????????0
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
            //??????
            return false;
        }else {
            //?????????
            return true;
        }
    }

}
