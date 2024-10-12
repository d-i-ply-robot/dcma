package com.hackingismakingisengineering.dcma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.hackingismakingisengineering.dcma.model.Program;

import com.hackingismakingisengineering.dcma.data.ProgramRepository;

@ComponentScan
@EnableAutoConfiguration
public class Application {

    @Autowired
    private ProgramRepository programRepository;
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    /*  TODO: Pre-load application
    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            // Pre-populate the database with some data
            programRepository.save(new Program("Program 1", "Description 1"));
            programRepository.save(new Program("Program 2", "Description 2"));
            programRepository.save(new Program("Program 3", "Description 3"));
        };
    }
        */
}

//TODO
// confirm JDK path
