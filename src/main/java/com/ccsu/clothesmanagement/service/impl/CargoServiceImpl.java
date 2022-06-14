package com.ccsu.clothesmanagement.service.impl;

import com.ccsu.clothesmanagement.domain.Cargo;
import com.ccsu.clothesmanagement.domain.User;
import com.ccsu.clothesmanagement.entity.Result;
import com.ccsu.clothesmanagement.mapper.CargoMapper;
import com.ccsu.clothesmanagement.service.CargoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CargoServiceImpl implements CargoService {
    @Autowired
    private CargoMapper cargoMapper;

    @Override
    public PageInfo<Cargo> getAllCargo(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Cargo> pageInfo = new PageInfo<>(cargoMapper.getAllCargo());
        return pageInfo;
    }

    @Override
    public int AddCargo(Cargo cargo) {
        //添加后默认状态为1
        cargo.setCargoStatus(1);
        cargo.setCargoNumber(UUID.randomUUID().toString().substring(0, 8));
        int row = cargoMapper.AddCargo(cargo);
        return row;
    }

    @Override
    public int DeletaCargoById(Integer cargoId) {
        int row = cargoMapper.DeletaCargoById(cargoId);
        return row;
    }

    @Override
    public int DeletaCargoByNumber(String cargoNumber) {
        int row = cargoMapper.DeletaCargoByNumber(cargoNumber);
        return row;
    }

    @Override
    public int UpdateCargo(Cargo cargo) {
        int row = cargoMapper.UpdateCargo(cargo);
        return row;
    }

    @Override
    public PageInfo<Cargo> getCargoByCargo(int pageNum, int pageSize, Cargo cargo) {
        PageHelper.startPage(pageNum, pageSize);
        cargo.setCargoStatus(1);
        PageInfo<Cargo> pageInfo = new PageInfo<>(cargoMapper.getCargoByCargo(cargo), 5);
        return pageInfo;
    }

    @Override
    public PageInfo<Cargo> selectCargoAndWareHouseByid(int pageNum, int pageSize, Cargo cargo) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Cargo> pageInfo = new PageInfo<>(cargoMapper.selectCargoAndWareHouseByid(cargo), 5);
        return pageInfo;
    }

    @Override
    public int delCargoByList(List<Integer> idList) {
        int row = cargoMapper.delCargoByList(idList);
        return row;
    }
}

