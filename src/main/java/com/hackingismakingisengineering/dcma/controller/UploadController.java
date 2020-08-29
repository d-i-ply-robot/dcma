package com.hackingismakingisengineering.dcma.controller;

import com.hackingismakingisengineering.dcma.data.ProgramRepository;
import com.hackingismakingisengineering.dcma.model.Program;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.reader.ProjectReader;
import net.sf.mpxj.reader.UniversalProjectReader;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    private ProgramRepository programRepository;

    //public static String uploadDirectory = "C:/Users/AltaRig/IdeaProjects/dcma/src/main/resources/files" ;
    //public static String uploadDirectory = "C:/Users/Cameron/Documents/Java/dcma/src/main/resources/files" ;
    public static String uploadDirectory = System.getProperty("user.home") ;



    @RequestMapping("/upload")
    private String upLoadPage(ModelMap modelMap){
        return "upload";

    }

    @RequestMapping("/uploadstatus")
    private String upLoadStatus(ModelMap modelMap, @RequestParam("files") MultipartFile[] files, @RequestParam("projectname") String projectName){

        Path fileNameAndPath = Paths.get(uploadDirectory, files[0].getOriginalFilename());
        try {

            Files.write(fileNameAndPath, files[0].getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        modelMap.addAttribute("msg", "successfully uploaded: " + files[0].getOriginalFilename());

        ProjectFile projectFile =null;

        ProjectReader reader = new UniversalProjectReader();
        try {

            String tempPath = fileNameAndPath.toString();
            tempPath.replace("\\", "/");


            projectFile = reader.read(tempPath);
        } catch (MPXJException e) {
            e.printStackTrace();
        }


        Program programToBeTested = new Program(projectFile, "cam", projectName, 1);

        programRepository.addProgram(programToBeTested);
        return "uploadstatus";

    }

}