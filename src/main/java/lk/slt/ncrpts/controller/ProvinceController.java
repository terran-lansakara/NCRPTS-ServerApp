package lk.slt.ncrpts.controller;

import lk.slt.ncrpts.dao.ProvinceDao;
import lk.slt.ncrpts.entity.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/provinces")
public class ProvinceController {

    @Autowired
    private ProvinceDao provinceDao;

    @GetMapping(path ="/list", produces = "application/json")
    public List<Province> get() {

        List<Province> provinces = this.provinceDao.findAll();

        provinces = provinces.stream().map(
                province -> { Province d = new Province();
                    d.setId(province.getId());
                    d.setName(province.getName());
                    return d; }
        ).collect(Collectors.toList());

        return provinces;

    }
}
