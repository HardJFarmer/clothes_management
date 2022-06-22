package com.ccsu.clothesmanagement.controller;

import com.ccsu.clothesmanagement.domain.Order;
import com.ccsu.clothesmanagement.domain.User;
import com.ccsu.clothesmanagement.entity.Result;
import com.ccsu.clothesmanagement.service.OrderService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/outstock.html")
    public String goOutStockPage(){
        return "outstock";
    }

    @RequestMapping("/instock.html")
    public String goInStockPage(){
        return "instock";
    }

    @PostMapping("/addoutorder")
    @ResponseBody
    public Result addOutOrder(Order order){
        orderService.addOutOrder(order);
        return Result.success();
    }

    @PostMapping("/deleteoutorder")
    @ResponseBody
    public Result deleteOutOrder(Order order){
        orderService.deleteOutOrder(order);
        return Result.success();
    }

    @PostMapping("/updateoutorder")
    @ResponseBody
    public Result updateOutOrder(Order order){
        orderService.updateOutOrder(order);
        return Result.success();
    }

    @PostMapping("/outorderandwarehouse")
    @ResponseBody
    public Result selectOutOrderAndWareHouse(Integer pageNum, Integer pageSize, Order order){
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 8;
        }
        PageInfo<Order> pageInfo=orderService.selectOutOrderAndWareHouse(pageNum,pageSize,order);
        return Result.success().add("orders",pageInfo);
    }

    /**
     * 上面是出库操作，下面是入库
     * @param order
     * @return
     */

    @PostMapping("/addinorder")
    @ResponseBody
    public Result addInOrder(Order order){
        orderService.addInOrder(order);
        return Result.success();
    }

    @PostMapping("/deleteinorder")
    @ResponseBody
    public Result deleteInOrder(Order order){
        orderService.deleteInOrder(order);
        return Result.success();
    }

    @PostMapping("/updateinorder")
    @ResponseBody
    public Result updateInOrder(Order order){
        orderService.updateInOrder(order);
        return Result.success();
    }

    /**
     * 条件查询入库单
     * @param pageNum 页数
     * @param pageSize 页面大小
     * @param order 用以封装查询条件的实体
     * @return 分页信息
     */
    @PostMapping("/inorder")
    @ResponseBody
    public Result selectInOrderAndWareHouse(Integer pageNum, Integer pageSize, Order order){
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 8;
        }
        PageInfo<Order> pageInfo = orderService.selectInOrderAndWareHouse(pageNum,pageSize,order);
        return Result.success().add("orders",pageInfo);
    }

    @RequestMapping("/showoutitem")
    public ModelAndView showStock(Integer orderId, Integer warehouseId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("outorderitem");
        modelAndView.addObject("orderId", orderId);
        modelAndView.addObject("warehouseId", warehouseId);
        return modelAndView;
    }

    @RequestMapping("/showinitem")
    public ModelAndView showInStock(Integer orderId, Integer warehouseId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("inorderitem");
        modelAndView.addObject("orderId", orderId);
        modelAndView.addObject("warehouseId", warehouseId);
        return modelAndView;
    }

    /**
     * 集合删除Order
     * @param ids
     * @return
     */
    @PostMapping("/delOrderList")
    @ResponseBody
    public Result delOrderList(@RequestParam(value = "ids") List<Integer> ids){
        orderService.delOrderByList(ids);
        return Result.success();
    }
}
