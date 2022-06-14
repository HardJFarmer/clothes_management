package com.ccsu.clothesmanagement.domain;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Cargo {
    private Integer cargoId;
    private String cargoNumber;
    private String cargoName;
    private Integer cargoStatus;
    private String cargoColor;
    private String  cargoSize;
    private Integer account;
    private List<WareHouse> wareHouseList;

    public Cargo() {
    }

    public Cargo(String cargoNumber, String cargoName, Integer cargoStatus) {
        this.cargoNumber = cargoNumber;
        this.cargoName = cargoName;
        this.cargoStatus = cargoStatus;
    }

    public Cargo(Integer cargoId, String cargoNumber, String cargoName, Integer cargoStatus) {
        this.cargoId = cargoId;
        this.cargoNumber = cargoNumber;
        this.cargoName = cargoName;
        this.cargoStatus = cargoStatus;
    }

    public Cargo(Integer cargoId, String cargoNumber, String cargoName, Integer cargoStatus, String cargoColor, String cargoSize, Integer account) {
        this.cargoId = cargoId;
        this.cargoNumber = cargoNumber;
        this.cargoName = cargoName;
        this.cargoStatus = cargoStatus;
        this.cargoColor = cargoColor;
        this.cargoSize = cargoSize;
    }

    public Cargo(Integer cargoId, String cargoNumber, String cargoName, Integer cargoStatus, String cargoColor, String cargoSize, Integer account, List<WareHouse> wareHouseList) {
        this.cargoId = cargoId;
        this.cargoNumber = cargoNumber;
        this.cargoName = cargoName;
        this.cargoStatus = cargoStatus;
        this.cargoColor = cargoColor;
        this.cargoSize = cargoSize;
        this.account = account;
        this.wareHouseList = wareHouseList;
    }
}
