package com.example.webservicepostbackbuilder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PostbackBuilderController {
    @RequestMapping("/")
    public String test(){
        return "PostbackBuilder";
    }
}
