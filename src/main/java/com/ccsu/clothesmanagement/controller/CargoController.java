package com.ccsu.clothesmanagement.controller;

import com.ccsu.clothesmanagement.domain.Cargo;
import com.ccsu.clothesmanagement.domain.CargoAndWareHouse;
import com.ccsu.clothesmanagement.domain.WareHouse;
import com.ccsu.clothesmanagement.entity.Result;
import com.ccsu.clothesmanagement.service.CargoService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CargoController {
    @Autowired
    private CargoService cargoService;

    @RequestMapping("/cargo.html")
    public String go(){
        return "/cargo";
    }

    /**
     * 获取cargolist
     * @return cargolist集合
     */
    @GetMapping("/cargo")
    @ResponseBody
    public Result getCargo(){
        List<Cargo> cargos = cargoService.getCargo();
        return Result.success().add("cargos",cargos);
    }


    @PostMapping("/addcargo")
    @ResponseBody
    public Result addCargo(Cargo cargo){
        cargoService.AddCargo(cargo);
        return Result.success();
    }

    @PostMapping("/updatecargo")
    @ResponseBody
    public Result updateCargo(Cargo cargo){
        cargoService.UpdateCargo(cargo);
        return Result.success();
    }

    @PostMapping("/deletecargo")
    @ResponseBody
    public Result deleteCargo(Cargo cargo){
        cargoService.DeletaCargoById(cargo.getCargoId());
        return Result.success();
    }

    /**
     * 集合删除Cargo
     * @param ids
     * @return
     */
    @PostMapping("/delCargoList")
    @ResponseBody
    public Result delCargoByList(@RequestParam(value = "ids") List<Integer> ids){
        cargoService.delCargoByList(ids);
        return Result.success();
    }

    @PostMapping("/selectbycargo")
    @ResponseBody
    public Result getCargoByCargo(Integer pageNum, Integer pageSize,Cargo cargo){
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 8;
        }
        PageInfo<Cargo> pageInfo=cargoService.getCargoByCargo
                (pageNum,pageSize,cargo);
        return Result.success().add("cargos",pageInfo);
    }



    @PostMapping("/selectStock")
    @ResponseBody
    public Result getCargoStock(Integer pageNum, Integer pageSize,Cargo cargo){
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 8;
        }
        PageInfo<Cargo> pageInfo = cargoService.selectCargoAndWareHouseByid(pageNum,pageSize,cargo);
        return Result.success().add("cargoStock",pageInfo);
    }



    @RequestMapping("/showStock")
    public ModelAndView showStock(Integer cargoId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cargoStock");
        modelAndView.addObject("cargoId", cargoId);
        return modelAndView;
    }

    /**
     * 根据货品id和仓库id查询数量
     * @return
     */
    @PostMapping("/selectAccount")
    @ResponseBody
    public Result selectAccount(Integer cargoId, Integer warehouseId){
        CargoAndWareHouse cargoAndWareHouse = cargoService.selectAccount(cargoId, warehouseId);
        return Result.success().add("cargoAndWareHouse", cargoAndWareHouse);
    }
}
