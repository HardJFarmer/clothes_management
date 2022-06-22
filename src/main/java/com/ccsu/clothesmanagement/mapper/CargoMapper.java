package com.ccsu.clothesmanagement.mapper;

import com.ccsu.clothesmanagement.domain.Cargo;
import com.ccsu.clothesmanagement.domain.CargoAndWareHouse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CargoMapper {
    /**
     *查询所有货品
     */
    List<Cargo> getAllCargo();

    /**
     *增加货品
     */
    int AddCargo(Cargo cargo);

    /**
     *根据货品id号删除货品
     */
    int DeletaCargoById(@Param("cargoId") Integer cargoId);

    /**
     *根据货品号删除货品
     */
    int DeletaCargoByNumber(@Param("cargoNumber") String cargoNumber);

    /**
     *根据货品id号修改货品信息
     */
    int UpdateCargo(Cargo cargo);

    /**
     *根据货品id号，货品号，货品名，货品状态多条件查询货品
     */
    List<Cargo> getCargoByCargo(Cargo cargo);

    /**
     *通过货品id号查询货品信息
     */
    Cargo selectCargoAndWareHouseByStepTwo(@Param("cargoId") Integer cargoId);

    /**
     * 通过货品id查询货品及其对应仓库信息
     */
    List<Cargo> selectCargoAndWareHouseByid(Cargo cargo);

    /**
     * 批量逻辑删除
     * @param idList id集合
     * @return
     */
    int delCargoByList(List<Integer> idList);

    /**
     * 通过货品id和仓库id号查询对应的货物库存
     */
    CargoAndWareHouse selectAccountByCargiIdAndWareHouseId(@Param("cargoId")int cargoId, @Param("warehouseId")int warehouseId);

    /**
     * 通过货品id和仓库id号修改对应的货物库存
     */
    int updateAccountByCargiIdAndWareHouseId(@Param("cargoId")int cargoId,@Param("warehouseId")int warehouseId,@Param("account")int account);

    int addCargoStock(CargoAndWareHouse cargoAndWareHouse);
}
