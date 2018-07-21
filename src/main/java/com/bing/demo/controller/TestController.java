package com.bing.demo.controller;

import com.bing.demo.entity.Result;
import com.bing.demo.entity.User;
import com.bing.demo.repository.UserRepository;
import com.google.gson.Gson;
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
   String searchUser(@PathVariable("username") String username) {
        List<User> result = this.userRepository.findByUsernameContaining(username);

//        User user = new User("asas","ihi");
//        userRepository.saveAndFlush(user);
        Result myResult = new Result();
        myResult.setData(result);


        return new Gson().toJson(myResult);
    }


    @RequestMapping(value = "/commitUser",method = RequestMethod.POST)
    public @ResponseBody
    String commitUser(@RequestParam(value = "username", required = true) String name,
                      @RequestParam(value = "userpwd", required = true) String pwd) {
//        this.userRepository.saveAndFlush(user);

        User user = new User(name,pwd);
        userRepository.saveAndFlush(user);

        List<User> userList = this.userRepository.findAll();

        Result result = new Result();
        result.setData(userList);


        return new Gson().toJson(result);
    }





}
