package com.hackingismakingisengineering.dcma.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;

@Configuration
@PropertySource("app.properties")
public class DataConfig {

    @Autowired
    private Environment env;


    @Bean
    public LocalSessionFactoryBean sessionFactoryBean(){


        Resource resource = new ClassPathResource("hibernate.cfg.xml");
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setConfigLocation(resource);

        sessionFactoryBean.setPackagesToScan(env.getProperty("dcma.entity.package"));

        sessionFactoryBean.setDataSource(dataSource());

        return sessionFactoryBean;

    }

    @Bean
    public DataSource dataSource() { //Make sure this is public
        BasicDataSource dataSource = new BasicDataSource();

        // set driver class
        dataSource.setDriverClassName(env.getProperty("dcma.db.driver"));

        // set URL
        dataSource.setUrl(env.getProperty("dcma.db.url"));

        // set Username and password
        dataSource.setUsername(env.getProperty("dcma.db.username"));
        dataSource.setPassword(env.getProperty("dcma.db.password"));

        return dataSource;

    }


}
