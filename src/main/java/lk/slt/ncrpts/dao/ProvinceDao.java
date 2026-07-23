package lk.slt.ncrpts.dao;

import lk.slt.ncrpts.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceDao extends JpaRepository <Province, Integer> {
}
