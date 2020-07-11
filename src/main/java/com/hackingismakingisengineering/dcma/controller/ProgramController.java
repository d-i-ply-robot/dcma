package com.hackingismakingisengineering.dcma.controller;

import com.hackingismakingisengineering.dcma.data.ProgramRepository;
import com.hackingismakingisengineering.dcma.model.dcma.Report;
import com.hackingismakingisengineering.dcma.model.Program;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.reader.UniversalProjectReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
;import java.util.List;


@Controller
public class ProgramController {


    @Autowired
    private ProgramRepository programRepository;

    @RequestMapping(value = "/")
    public String listPrograms(ModelMap modelMap){

        List<Program> programList= programRepository.getAllPrograms();

        modelMap.put("programs", programList);

        return "home";
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



    @RequestMapping(value = "/home")
    public String reportDetails(){

        UniversalProjectReader reader = new UniversalProjectReader();
        ProjectFile project;

        Program program;
        Report report;


        try {
            project = reader.read("sample.mpp");
            program = new Program(project);
            //report = new Report(program);




        } catch (Exception e) {
            e.printStackTrace();
        }

        return "home";
    }


}
