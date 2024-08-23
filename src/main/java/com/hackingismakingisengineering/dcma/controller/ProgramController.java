package com.hackingismakingisengineering.dcma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hackingismakingisengineering.dcma.data.ProgramRepository;
import com.hackingismakingisengineering.dcma.model.Program;


@Controller
public class ProgramController {


    @Autowired
    private ProgramRepository programRepository;

    @RequestMapping(value = "/programs")
    public String listPrograms(ModelMap modelMap){

        List<Program> programList= programRepository.getAllPrograms();

        modelMap.put("programs", programList);

        return "programs";
    }

    @RequestMapping(value = "/programs/{title}")
    public String programDetails(@PathVariable String title, ModelMap modelMap){

        /*
        UniversalProjectReader reader = new UniversalProjectReader();
        ProjectFile project = null;


        try {
            project = reader.read("sample.mpp");
        } catch (MPXJException e) {
            e.printStackTrace();
        }

        Program program = new Program(project, "CAmmy", "sample");
        */

        Program program = programRepository.findByTitle(title);
        modelMap.put("program", program);

        return "program-details";
    }



    @RequestMapping(value = "/")
    public String reportDetails(){


        return "home";
    }


}
