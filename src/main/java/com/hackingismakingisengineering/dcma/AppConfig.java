package com.hackingismakingisengineering.dcma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class AppConfig {

    // view page on browser at- http://localhost:8080/
    public static void main(String[] args) {

        SpringApplication.run(AppConfig.class, args);
    }

}
