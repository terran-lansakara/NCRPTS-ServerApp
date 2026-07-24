package lk.slt.ncrpts.controller;

import lk.slt.ncrpts.dao.CustomerDao;
import lk.slt.ncrpts.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/customers")
public class CustomerController {
    
    @Autowired
    private CustomerDao customerDao;

    @GetMapping(produces = "application/json")
    @PreAuthorize("hasAuthority('customer-select')")
    public List<Customer> get(@RequestParam HashMap<String, String> params) {

        List<Customer> customers = this.customerDao.findAll();

        if(params.isEmpty())  return customers;


        String id = params.get("id");
        String name = params.get("name");
        String nic = params.get("nic");
        String genderid = params.get("genderid");
        String districtid = params.get("districtid");
        String provinceid = params.get("provinceid");

        Stream<Customer> cstream = customers.stream();

        if(id!=null) cstream = cstream.filter(c -> c.getId().toString().contains(id));
        if(nic!=null) cstream = cstream.filter(c -> c.getNic().contains(nic));
        if(name!=null) cstream = cstream.filter(c -> c.getName().toLowerCase().contains(name.toLowerCase()));
        if(genderid!=null) cstream = cstream.filter(c -> c.getGender().getId()==Integer.parseInt(genderid));
        if(districtid!=null) cstream = cstream.filter(c -> c.getDistrict().getId()==Integer.parseInt(districtid));
        if(provinceid!=null) cstream = cstream.filter(c -> c.getDistrict().getProvince().getId()==Integer.parseInt(provinceid));

        return cstream.collect(Collectors.toList());

    }

    @GetMapping(path ="/list",produces = "application/json")
    public List<Customer> get() {

        List<Customer> customers = this.customerDao.findAllNameId();

        customers = customers.stream().map(
                customer -> {
                    Customer c = new Customer(customer.getId(), customer.getName());
                    return  c;
                }
        ).collect(Collectors.toList());

        return customers;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('customer-insert')")
    public HashMap<String,String> add(@RequestBody Customer customer){

        HashMap<String,String> response = new HashMap<>();
        String errors="";

        if(customerDao.findByNic(customer.getNic())!=null)
            errors = errors+"<br> Existing NIC";

        System.out.println(customer.getDoregister());

        if(errors=="")
            customerDao.save(customer);
        else errors = "Server Validation Errors : <br> "+errors;

        response.put("id",String.valueOf(customer.getId()));
        response.put("url","/customers/"+customer.getId());
        response.put("errors",errors);

        return response;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('customer-update')")
    public HashMap<String,String> update(@RequestBody Customer customer){

        HashMap<String,String> response = new HashMap<>();
        String errors="";

        Customer c = customerDao.findByNic(customer.getNic());

        if(c!=null && customer.getId()!=c.getId())
            errors = errors+"<br> Existing NIC";

        if(errors=="") customerDao.save(customer);
        else errors = "Server Validation Errors : <br> "+errors;

        response.put("id",String.valueOf(customer.getId()));
        response.put("url","/customers/"+customer.getId());
        response.put("errors",errors);

        return response;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('customer-delete')")
    public HashMap<String,String> delete(@PathVariable Integer id){

        System.out.println(id);

        HashMap<String,String> response = new HashMap<>();
        String errors="";

        Customer c = customerDao.findByMyId(id);

        if(c==null)
            errors = errors+"<br> Customer Does Not Exist";

        if(errors=="") customerDao.delete(c);
        else errors = "Server Validation Errors : <br> "+errors;

        response.put("id",String.valueOf(id));
        response.put("url","/customers/"+id);
        response.put("errors",errors);

        return response;
    }

}
