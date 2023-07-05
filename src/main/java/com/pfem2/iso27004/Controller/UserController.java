package com.pfem2.iso27004.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfem2.iso27004.Entity.Collector;
import com.pfem2.iso27004.Entity.User;
import com.pfem2.iso27004.Service.UserService;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userservice;

    @Autowired
    public UserController(UserService userservice) {
        this.userservice = userservice;
    }

    @GetMapping
    public List<User> getUsers() {
        return userservice.getUsers();
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        // System.out.println(user.getRole());
        return userservice.addUser(user);
    }

    @GetMapping(path = "collector")
    public List<Collector> getCollectors() {

        return this.userservice.getCollectors();

    }

    @PostMapping(path = "collector")
    public Collector setCollector(@RequestBody Collector c) {

        return this.userservice.setCollector(c);
    }

    @PutMapping(path = "collector")
    public Collector updateCollector(@RequestBody Collector c) {
        List<Collector> l = this.userservice.getCollectors().stream().filter(cl -> !(cl.getId()).equals(c.getId()))
                .toList();
        System.out.println(l);
        return this.userservice.setCollector(c);
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userservice.deleteUser(userId);
    }

}
