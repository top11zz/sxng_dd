package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class testController {

    @RequestMapping("/jump")
    public String jump(){
        return "test.html";
    }

    @RequestMapping("/exportHtml")
    public String exportHtml(){
        return "export.html";
    }
}
