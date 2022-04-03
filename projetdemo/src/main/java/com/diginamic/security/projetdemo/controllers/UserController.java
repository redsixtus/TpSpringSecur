package com.diginamic.security.projetdemo.controllers;

import com.diginamic.security.projetdemo.entities.User;
import com.diginamic.security.projetdemo.repo.UserRepo;
import com.diginamic.security.projetdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@Secured("ROLE_ADMIN")
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepo userrepo;

    //@RolesAllowed("ROLE_USER")incompatible avec secured
    @Secured("ROLE_ADMIN")
    @GetMapping("/addUser")
    public String modifie(Model model) {
     model.addAttribute("user",new User());
        return "edit";
    }
//    @Secured("ROLE_ADMIN")
//    @PostMapping("/addUser")
//    public String validmodifie(Model model) {
//        return "home";
//    }
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/deleteUser/{id}")
    public void deleteUserById(@PathVariable Integer id){
        this.userService.deleteUserById(id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/addUser")
    public String creatUser(@ModelAttribute("user") User user){
        this.userService.creatUser(user);
        return "redirect:/" ;
    }
    @Secured("ROLE_ADMIN")
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user){
        this.userService.creatUser(user);
        return "redirect:/" ;
    }


}
