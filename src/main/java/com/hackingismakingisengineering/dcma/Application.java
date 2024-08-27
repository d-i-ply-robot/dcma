package com.hackingismakingisengineering.dcma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//import com.hackingismakingisengineering.dcma.data.ProgramRepository;

@ComponentScan
@EnableAutoConfiguration
public class Application {

    @Autowired
    //private ProgramRepository programRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

//TODO
// confirm JDK path
