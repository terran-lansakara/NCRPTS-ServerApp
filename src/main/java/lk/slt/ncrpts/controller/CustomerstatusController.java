package lk.slt.ncrpts.controller;

import lk.slt.ncrpts.dao.CustomerstatusDao;
import lk.slt.ncrpts.entity.Customerstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/customerstatuses")
public class CustomerstatusController {

    @Autowired
    private CustomerstatusDao customerstatusDao;

    @GetMapping(path = "/list", produces = "application/json")
    public List<Customerstatus> get() {

        List<Customerstatus> customerstatuses = this.customerstatusDao.findAll();

        customerstatuses = customerstatuses.stream().map(
                customerstatus -> { Customerstatus d = new Customerstatus();
                    d.setId(customerstatus.getId());
                    d.setName(customerstatus.getName());
                    return d;
                }
        ).collect(Collectors.toList());

        return customerstatuses;

    }



}
