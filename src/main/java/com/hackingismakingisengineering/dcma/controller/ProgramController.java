package com.hackingismakingisengineering.dcma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProgramController {

    @RequestMapping(value = "/")
    public String listPrograms(){
        return "home";
    }

}
