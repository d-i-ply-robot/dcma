package com.hackingismakingisengineering.dcma;

import com.hackingismakingisengineering.dcma.data.ProgramRepository;
import com.hackingismakingisengineering.dcma.model.Program;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.reader.ProjectReader;
import net.sf.mpxj.reader.UniversalProjectReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ComponentScan
@EnableAutoConfiguration
public class Application {

    // view page on browser at- http://localhost:8080/
    public static void main(String[] args) {




        SpringApplication.run(Application.class, args);


        //Auto junk
        /*
        public static String uploadDirectory = "C:/Users/Cameron/Documents/Java/dcma/src/main/resources/files" ;

        Path fileNameAndPath = Paths.get(uploadDirectory, "Mill Road - Programme.mpp");

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


         */
    }

}
