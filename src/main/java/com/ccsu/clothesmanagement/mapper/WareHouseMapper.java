package com.ccsu.clothesmanagement.mapper;

import com.ccsu.clothesmanagement.domain.WareHouse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WareHouseMapper {
    /**
     * 根据条件查询仓库信息
     * @return
     */
    List<WareHouse> selectWareHouseByCondition(WareHouse wareHouse);

    /**
     * 增加新的仓库
     */
    int addWareHouse(WareHouse wareHouse);

    /**
     * 删除一个仓库
     */
    int deleteWareHouse(WareHouse wareHouse);

    /**
     * 修改仓库信息
     */
    int updateWarHouse(WareHouse wareHouse);

    /**
     * 通过仓库id号查询仓库与货品关系信息
     */
    List<WareHouse> selectCargoAndWareHouseByStepOne(@Param("warehouseId") Integer warehouseId);


    int delWarehouseByList(List<Integer> ids);
}
