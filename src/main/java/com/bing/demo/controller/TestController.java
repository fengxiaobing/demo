package com.bing.demo.controller;

import com.bing.demo.entity.User;
import com.bing.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/searchUser/{username}")
    public @ResponseBody
    List<User> searchUser(@PathVariable("username") String username) {
        List<User> result = this.userRepository.findByUsernameContaining(username);

//        User user = new User("asas","ihi");
//        userRepository.saveAndFlush(user);
        return result;
    }


    @RequestMapping(value = "/commitUser",method = RequestMethod.POST)
    public @ResponseBody  String commitUser(@RequestParam(value = "username", required = true) String name,
                              @RequestParam(value = "userpwd", required = true) String pwd) {
//        this.userRepository.saveAndFlush(user);

        User user = new User(name,pwd);
        userRepository.saveAndFlush(user);
        System.out.print("dcscsc");
        return "ok";
    }





}
