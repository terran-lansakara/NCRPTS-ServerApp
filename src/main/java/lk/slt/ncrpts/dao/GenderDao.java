package lk.slt.ncrpts.dao;

import lk.slt.ncrpts.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderDao extends JpaRepository<Gender,Integer> {

}

