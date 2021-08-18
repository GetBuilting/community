package com.luo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GetStartController {

    @GetMapping("/getStart")
    public String getStart(@RequestParam(name = "name",defaultValue = "word",required = false)String name, Model model){
        model.addAttribute("name",name);
        return "hello";

    }
}
