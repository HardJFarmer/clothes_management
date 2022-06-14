package com.ccsu.clothesmanagement.service;

import com.ccsu.clothesmanagement.domain.WareHouse;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WareHouseService {
    /**
     * 根据条件查询仓库信息
     * @return
     */
    PageInfo<WareHouse> selectWareHouseByCondition(int pageNum,int pageSize,WareHouse wareHouse);

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
    PageInfo<WareHouse> selectCargoAndWareHouseByStepOne(int pageNum,int pageSize,WareHouse wareHouse);

    int delCargoByList(List<Integer> ids);
}
