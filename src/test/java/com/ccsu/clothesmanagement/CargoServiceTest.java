package com.ccsu.clothesmanagement;

import com.ccsu.clothesmanagement.domain.Cargo;
import com.ccsu.clothesmanagement.domain.User;
import com.ccsu.clothesmanagement.service.CargoService;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CargoServiceTest {
    @Autowired
    private CargoService cargoService;

    // @Test
    // void testInsert() {
    //     for (int i = 10; i < 61; i++) {
    //         Cargo cargo = new Cargo();
    //         cargo.setCargoNumber("KJ000" + i);
    //         cargo.setCargoName("藤甲");
    //         cargo.setCargoColor("褐色");
    //         cargo.setCargoSize("L");
    //         cargoService.AddCargo(cargo);
    //     }
    // }

    @Test
    void testDelCargoByList(){
        List<Integer> idList = new ArrayList<>();
        for (int i = 10; i < 21; i++) {
            idList.add(i);
        }
        int i = cargoService.delCargoByList(idList);
        System.out.println(i);
    }
    @Test
    void testSelectCargoAndWareHouseByid(){
        Cargo cargo = new Cargo();
        cargo.setCargoId(1);
        PageInfo<Cargo> cargoPageInfo = cargoService.selectCargoAndWareHouseByid(1, 8, cargo);
        System.out.println(cargoPageInfo);
    }
}
