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
import java.util.Map;

@Controller
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/searchUser")
    public @ResponseBody
   String searchUser() {
        List<User> result = this.userRepository.findAll();

//        User user = new User("asas","ihi");
//        userRepository.saveAndFlush(user);
        Result myResult = new Result();
        myResult.setData(result);


        return new Gson().toJson(myResult);
    }


    @RequestMapping(value = "/commitUser",method = RequestMethod.POST)
    public @ResponseBody
    String commitUser(@RequestBody Map<String,Object> params) {



//        String loanOrderNbr = params.get("loanOrderNbr").toString();
        List<User> userList = this.userRepository.findAll();

        Result result = new Result();
        result.setData(userList);


        return new Gson().toJson(result);
    }





}
