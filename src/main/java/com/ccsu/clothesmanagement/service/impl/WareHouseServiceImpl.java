package com.ccsu.clothesmanagement.service.impl;

import com.ccsu.clothesmanagement.domain.WareHouse;
import com.ccsu.clothesmanagement.mapper.WareHouseMapper;
import com.ccsu.clothesmanagement.service.WareHouseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WareHouseServiceImpl implements WareHouseService {

    @Autowired
    private WareHouseMapper wareHouseMapper;

    /**
     * 根据条件查询仓库信息
     * @return
     */
    @Override
    public PageInfo<WareHouse> selectWareHouseByCondition(int pageNum,int pageSize,WareHouse wareHouse){
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<WareHouse> pageInfo = new PageInfo<>(wareHouseMapper.selectWareHouseByCondition(wareHouse));
        return pageInfo;
    }

    /**
     * 增加新的仓库
     */
    @Override
    public int addWareHouse(WareHouse wareHouse){
        int row=wareHouseMapper.addWareHouse(wareHouse);
        return row;
    }

    /**
     * 删除一个仓库
     */
    @Override
    public int deleteWareHouse(WareHouse wareHouse){
        int row=wareHouseMapper.deleteWareHouse(wareHouse);
        return row;
    }

    /**
     * 修改仓库信息
     */
    @Override
    public int updateWarHouse(WareHouse wareHouse){
        int row=wareHouseMapper.updateWarHouse(wareHouse);
        return row;
    }

    /**
     * 通过仓库id号查询仓库与货品关系信息
     */
    @Override
    public PageInfo<WareHouse> selectCargoAndWareHouseByStepOne(int pageNum,int pageSize,WareHouse wareHouse){
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<WareHouse> pageInfo = new PageInfo<>(wareHouseMapper.selectCargoAndWareHouseByStepOne(wareHouse.getWarehouseId()));
        return pageInfo;
    }

    @Override
    public int delCargoByList(List<Integer> ids) {
        int row = wareHouseMapper.delWarehouseByList(ids);
        return row;
    }
}
