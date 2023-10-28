package com.springbootwork.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
public class HelloController {

//    @RequestMapping(value="/", method = RequestMethod.GET) -> vast thing

    @Value("${welcome.message}")
    private String welcomeMessage;

    @GetMapping("/") //simple thing
    public String helloworld(){
        return welcomeMessage;
    }
}
