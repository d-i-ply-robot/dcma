package com.hackingismakingisengineering.dcma.controller;

import com.hackingismakingisengineering.dcma.data.ProgramRepository;
import com.hackingismakingisengineering.dcma.model.DCMAReport;
import com.hackingismakingisengineering.dcma.model.Program;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.reader.ProjectReader;
import net.sf.mpxj.reader.UniversalProjectReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
        DCMAReport dcmaReport;


        try {
            project = reader.read("sample.mpp");
            program = new Program(project);
            dcmaReport = new DCMAReport(program);




        } catch (Exception e) {
            e.printStackTrace();
        }

        return "home";
    }


}
