package com.diginamic.security.projetdemo.repo;

import com.diginamic.security.projetdemo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.username = :name")
    Optional<User> findUserWithName(String name);
    //reviens au meme que la premiere
    Optional<User> findByUsername(String name);

}
