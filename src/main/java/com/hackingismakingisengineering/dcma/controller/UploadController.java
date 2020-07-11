package com.hackingismakingisengineering.dcma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
public class UploadController {


    public static String uploadDirectory = "C:/Users/AltaRig/IdeaProjects/dcma/src/main/resources/files" ;



    @RequestMapping("/upload")
    private String upLoadPage(ModelMap modelMap){
        return "upload";

    }

    @RequestMapping("/uploadstatus")
    private String upLoadStatus(ModelMap modelMap, @RequestParam("files") MultipartFile[] files){

        Path fileNameAndPath = Paths.get(uploadDirectory, files[0].getOriginalFilename());
        try {
            Files.write(fileNameAndPath, files[0].getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        modelMap.addAttribute("msg", "successfully uploaded: " + files[0].getOriginalFilename());

        return "uploadstatus";

    }

}
