package com.ccsu.clothesmanagement;

import com.ccsu.clothesmanagement.domain.Order;
import com.ccsu.clothesmanagement.service.OrderService;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class OrderTest {

    @Autowired
    private OrderService orderService;

    @Test
    void testAddOrder(){
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setResponsiblePerson("张三");
        order.setWarehouseId(3);
        order.setSourceCompany("第五分公司");
        for (int i = 1; i <= 20; i++) {
            order.setOrderNumber(getNumber());
            orderService.addOutOrder(order);
        }
    }

    String getNumber(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String strDate = sdf.format(date);
        return "CK" + strDate;
    }

    @Test
    void testSelect(){
        PageInfo<Order> orderPageInfo = orderService.selectInOrderAndWareHouse(14, 8, new Order());
        System.out.println(orderPageInfo);
    }
}
