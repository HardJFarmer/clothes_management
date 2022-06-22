package com.ccsu.clothesmanagement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoAndWareHouse {
    private Integer id;
    private Integer cargoId;
    private Integer warehouseId;
    private int account;


    public CargoAndWareHouse(Integer cargoId, Integer warehouseId, Integer account) {
        this.cargoId = cargoId;
        this.warehouseId = warehouseId;
        this.account = account;
    }
}
