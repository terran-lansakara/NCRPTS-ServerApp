package lk.slt.ncrpts.dao;

import lk.slt.ncrpts.entity.Customerstatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerstatusDao extends JpaRepository <Customerstatus, Integer> {
}
