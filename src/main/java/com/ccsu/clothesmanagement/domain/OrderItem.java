package com.ccsu.clothesmanagement.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class OrderItem {
    private Integer orderitemId;
    private Integer cargoId;
    private Integer changeAccount;
    private Integer judgeStatus;
    private Integer orderitemStatus;
    private Integer orderId;
    private Integer account;
    private Order order;
    private Cargo cargo;

    public OrderItem(Integer cargoId, Integer orderId) {
        this.cargoId = cargoId;
        this.orderId = orderId;
    }
}
