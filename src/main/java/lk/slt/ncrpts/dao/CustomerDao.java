package lk.slt.ncrpts.dao;

import lk.slt.ncrpts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerDao extends JpaRepository <Customer, Integer> {

    Customer findByNic(String nic);
    Optional<Customer> findById(Integer id);

    @Query("select c from Customer c where c.id = :id")
    Customer findByMyId(@Param("id") Integer id);

    @Query("SELECT NEW Customer (c.id, c.name) FROM Customer c")
    List<Customer> findAllNameId();
}
