package com.hackingismakingisengineering.dcma.controller;

import com.hackingismakingisengineering.dcma.model.DCMAReport;
import com.hackingismakingisengineering.dcma.model.Program;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.reader.ProjectReader;
import net.sf.mpxj.reader.UniversalProjectReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
;


@Controller
public class ProgramController {

    @RequestMapping(value = "/")
    public String listPrograms(){
        return "home";
    }


    @RequestMapping(value = "/program")
    public String programDetails(){

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
