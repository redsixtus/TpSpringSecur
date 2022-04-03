package com.diginamic.security.projetdemo.service;

import com.diginamic.security.projetdemo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.diginamic.security.projetdemo.repo.UserRepo;

import java.util.Objects;

@Service
public class UserService implements UserDetailsService {
    public final UserRepo userrepo;
    @Autowired
    public UserService(UserRepo userrepo) {
        super();
        this.userrepo = userrepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Objects.requireNonNull(username);
        User user= userrepo.findByUsername(username).
        orElseThrow(()->new UsernameNotFoundException("User non trouvé :"));
        return user;
    }

    public void deleteUserById(Integer id)  {
        userrepo.deleteById(id);
    }
    public void creatUser(User user){
        userrepo.save( user );
    }
    public void updateUser(User user){
        userrepo.save( user );
    }

}
