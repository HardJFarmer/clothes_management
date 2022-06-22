package com.ccsu.clothesmanagement.service;

import com.ccsu.clothesmanagement.domain.Cargo;
import com.ccsu.clothesmanagement.domain.CargoAndWareHouse;
import com.ccsu.clothesmanagement.domain.User;
import com.ccsu.clothesmanagement.domain.WareHouse;
import com.ccsu.clothesmanagement.entity.Result;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CargoService {

    int AddCargo(Cargo cargo);

    int DeletaCargoById(Integer cargoId);

    int DeletaCargoByNumber(String cargoNumber);

    int UpdateCargo(Cargo cargo);

    PageInfo<Cargo> getCargoByCargo(int pageNum, int pageSize, Cargo cargo);

    PageInfo<Cargo> selectCargoAndWareHouseByid(int pageNum, int pageSize, Cargo cargo);

    int delCargoByList(List<Integer> idList);

    CargoAndWareHouse selectAccount(Integer cargoId, Integer warehouseId);

    List<Cargo> getCargo();
}
