package com.diginamic.security.projetdemo.controllers;

import com.diginamic.security.projetdemo.entities.User;
import com.diginamic.security.projetdemo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin//Anotation obligatoire pour les app web ou @bean pour les cross origin
@RequestMapping("api/users")
public class UserControllerRest {
    @Autowired
    UserRepo userrepo;

    @GetMapping
    public List<User> getall() {
        return userrepo.findAll();
    }
    @GetMapping("/{id}")
    public User getone(@PathVariable("id") Integer id){
        return userrepo.findById(id).get();
    }
}
