package com.ccsu.clothesmanagement.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class WareHouse {
    private Integer warehouseId;
    private String warehouseName;
    private Integer account;
    private Integer warehouseStatus;
    private List<Cargo> cargoList;
    private Integer cargoId;

    public WareHouse(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public WareHouse(Integer warehouseId,String warehouseName) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
    }

    public WareHouse(Integer warehouseId, String warehouseName, List<Cargo> cargoList) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.cargoList = cargoList;
    }


}
