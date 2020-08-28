package com.hackingismakingisengineering.dcma.controller;

import com.hackingismakingisengineering.dcma.model.User.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
public class LoginController {



    @RequestMapping(path = "/login", method = RequestMethod.GET)
    private String loginPage(ModelMap modelMap, HttpServletRequest request){

        modelMap.addAttribute("user", new User());

        try{
            Object flash = request.getSession().getAttribute("flash");
            modelMap.addAttribute("flash", flash);

            request.getSession().removeAttribute("flash");

        }catch (Exception e){

        }
        return "login";
    }

    @RequestMapping("/access_denied")
    private String accessDenied(){

        return "access_denied";
    }

}
