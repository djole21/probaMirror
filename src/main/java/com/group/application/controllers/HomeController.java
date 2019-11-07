package com.group.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public String redirectEverythingOtherThanTest(){
        System.out.println("usao");
        return "redirect:/app/";
    }
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String home(){
        return "index";
    }

}
