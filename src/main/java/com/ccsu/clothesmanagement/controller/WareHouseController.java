package com.ccsu.clothesmanagement.controller;

import com.ccsu.clothesmanagement.domain.WareHouse;
import com.ccsu.clothesmanagement.entity.Result;
import com.ccsu.clothesmanagement.service.WareHouseService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class WareHouseController {
    @Autowired
    WareHouseService wareHouseService;

    @RequestMapping("/wareHouse.html")
    public String goPage() {
        return "/wareHouse";
    }

    @PostMapping("/warehouse")
    @ResponseBody
    public Result selectWareHouseByCondition(Integer pageNum, Integer pageSize, WareHouse wareHouse) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 8;
        }
        PageInfo<WareHouse> pageInfo = wareHouseService.selectWareHouseByCondition(pageNum, pageSize, wareHouse);
        return Result.success().add("warehouses", pageInfo);
    }

    /**
     * 不分页查询当前所有仓库
     *
     * @return
     */
    @GetMapping("/warehouse")
    @ResponseBody
    public Result selectWareHouses() {
        List<WareHouse> wareHouses = wareHouseService.selectWareHouses();
        return Result.success().add("warehouses", wareHouses);
    }

    @PostMapping("/addwarehouse")
    @ResponseBody
    public Result addWareHouse(WareHouse wareHouse) {
        wareHouseService.addWareHouse(wareHouse);
        return Result.success();
    }

    @PostMapping("/updatewarehouse")
    @ResponseBody
    public Result updateWareHouse(WareHouse wareHouse) {
        wareHouseService.updateWarHouse(wareHouse);
        return Result.success();
    }

    @PostMapping("/deletewarehouse")
    @ResponseBody
    public Result deleteWareHouse(WareHouse wareHouse) {
        wareHouseService.deleteWareHouse(wareHouse);
        return Result.success();
    }

    @PostMapping("/selectcargoandwarehouse")
    @ResponseBody
    public Result selectCargoAndWareHouseByStepOne(Integer pageNum, Integer pageSize, WareHouse wareHouse) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 8;
        }
        PageInfo<WareHouse> pageInfo = wareHouseService.selectCargoAndWareHouseByStepOne(pageNum, pageSize, wareHouse);
        return Result.success().add("warehouses", pageInfo);
    }

    /**
     * 查询当前仓库里货品的库存，方便构建添加出库信息的数量的下拉栏
     * @param wareHouse
     * @return 返回仓库对应的货品集合
     */
    @GetMapping("/selectcargoandwarehouse")
    @ResponseBody
    public Result selectWarehouseCargo(WareHouse wareHouse) {
        List<WareHouse> wareHouses = wareHouseService.selectWarehouseCargo(wareHouse);
        return Result.success().add("warehouses", wareHouses);
    }

    /**
     * 集合删除warehouse
     *
     * @param ids
     * @return
     */
    @PostMapping("/delWarehouseList")
    @ResponseBody
    public Result delCargoByList(@RequestParam(value = "ids") List<Integer> ids) {
        wareHouseService.delCargoByList(ids);
        return Result.success();
    }

    @RequestMapping("/showWarehouseStock")
    public ModelAndView showStock(Integer warehouseId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("warehouseStock");
        modelAndView.addObject("warehouseId", warehouseId);
        return modelAndView;
    }

}
