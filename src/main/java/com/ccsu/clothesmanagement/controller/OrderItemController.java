package com.ccsu.clothesmanagement.controller;


import com.ccsu.clothesmanagement.domain.OrderItem;
import com.ccsu.clothesmanagement.entity.Result;
import com.ccsu.clothesmanagement.service.OrderItemService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;


    @PostMapping("/addoutorderitem")
    @ResponseBody
    public Result addOutOrderItem(OrderItem orderItem,int warehouseId) {
        int judge=orderItemService.addOutOrderItem(orderItem,warehouseId);
        if(judge==0){
            return Result.fail();
        }else{
            return Result.success();
        }
    }

    @PostMapping("/deleteoutorderitem")
    @ResponseBody
    public Result deleteOutOrderItem(OrderItem orderItem,Integer warehouseId){
        orderItemService.deleteOutOrderItem(orderItem,warehouseId);
        return Result.success();
    }

    @PostMapping("/updateoutorderitem")
    @ResponseBody
    public Result updateOutOrderItem(OrderItem orderItem,int warehouseId){
        int judge = orderItemService.updateOutOrderItem(orderItem, warehouseId);
        if(judge==0){
            return Result.fail();
        }else{
            return Result.success();
        }
    }

    @PostMapping("/addinorderitem")
    @ResponseBody
    public Result addInOrderItem(OrderItem orderItem, int warehouseId) {
        orderItemService.addInOrderItem(orderItem,warehouseId);
        return Result.success();
    }

    @PostMapping("/deleteinorderitem")
    @ResponseBody
    public Result deleteInOrderItem(OrderItem orderItem,int warehouseId){
        orderItemService.deleteInOrderItem(orderItem,warehouseId);
        return Result.success();
    }

    @PostMapping("/updateinorderitem")
    @ResponseBody
    public Result updateInOrderItem(OrderItem orderItem,int warehouseId){
        int i = orderItemService.updateInOrderItem(orderItem, warehouseId);
        if (i == 0){
            //库存不足,返回失败
            return Result.fail();
        }else
        {
            //库存足够，返回成功
            return Result.success();
        }
    }

    @PostMapping("/selectorderitem")
    @ResponseBody
    public Result selectOrderItemAndCargoAndAccount(Integer pageNum, Integer pageSize, OrderItem orderItem){
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 8;
        }
        PageInfo<OrderItem> pageInfo=orderItemService.selectOrderItemAndCargoAndAccount(pageNum,pageSize,orderItem);
        return Result.success().add("orderitems",pageInfo);
    }

}
