package lk.slt.ncrpts.security;

import lk.slt.ncrpts.dao.UserDao;
import lk.slt.ncrpts.entity.Privilege;
import lk.slt.ncrpts.entity.User;
import lk.slt.ncrpts.entity.Userrole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    final UserDao userdao;

    @Autowired
    public UserService(UserDao userdao) {
        this.userdao = userdao;
    }

    public User getByUsername(String username){

        User user = new User();

        if ("AdminSLT".equals(username)){

            user.setUsername(username);

        }else {
            user = userdao.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
        }

        return user;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        if (username.equals("AdminSLT")) {
//            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//            authorities.add(new SimpleGrantedAuthority("gender-list-get"));
//            authorities.add(new SimpleGrantedAuthority("designation-list-get"));
//            authorities.add(new SimpleGrantedAuthority("employeestatus-list-get"));
//            authorities.add(new SimpleGrantedAuthority("employee-select"));
//            authorities.add(new SimpleGrantedAuthority("operation-select"));
//            authorities.add(new SimpleGrantedAuthority("operation-insert"));
//            authorities.add(new SimpleGrantedAuthority("operation-update"));
//            authorities.add(new SimpleGrantedAuthority("operation-delete"));
//            authorities.add(new SimpleGrantedAuthority("privilege-select"));
//            authorities.add(new SimpleGrantedAuthority("privilege-insert"));
//            authorities.add(new SimpleGrantedAuthority("privilege-update"));
//            authorities.add(new SimpleGrantedAuthority("privilege-delete"));
//
//            return org.springframework.security.core.userdetails.User
//                    .withUsername("AdminSLT")
//                    .password(new BCryptPasswordEncoder().encode("Admin12345"))
//                    .authorities(authorities)
//                    .accountExpired(false)
//                    .accountLocked(false)
//                    .credentialsExpired(false)
//                    .disabled(false)
//                    .build();
//        }
//        else {

            User user = userdao.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }

            Set<SimpleGrantedAuthority> authorities = new HashSet<>();

            List<Userrole> userroles = (List<Userrole>) user.getUserroles();

            for(Userrole u : userroles){
                List<Privilege> privileges = (List<Privilege>) u.getRole().getPrivileges();
                for (Privilege p:privileges){
                    authorities.add(new SimpleGrantedAuthority(p.getAuthority()));
                }
            }

            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .authorities(authorities)
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
//        }
    }
}
