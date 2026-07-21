package lk.slt.ncrpts.dao;


import lk.slt.ncrpts.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDao extends JpaRepository<User,Integer> {
    User findByUsername(String username);
}
