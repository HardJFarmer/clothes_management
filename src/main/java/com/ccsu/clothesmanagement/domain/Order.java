package com.ccsu.clothesmanagement.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class Order {
    private Integer orderId;
    private String orderNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date orderDate;

    private String responsiblePerson;
    private String sourceCompany;
    private Integer warehouseId;
    private Integer judgeStatus;
    private Integer orderStatus;

    private String warehouseName;
    private WareHouse wareHouse;
    private List<OrderItem> orderItem;

}
