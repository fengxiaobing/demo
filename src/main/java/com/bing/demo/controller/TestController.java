package com.bing.demo.controller;

import com.bing.demo.entity.User;
import com.bing.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
