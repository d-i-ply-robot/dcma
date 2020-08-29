/*
package com.hackingismakingisengineering.dcma.config;

import com.hackingismakingisengineering.dcma.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        // TODO: Doesnt work with non DAO
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**");
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //User must have user role
        http
                .authorizeRequests()
                    .anyRequest().hasRole("USER") //Note that this passes User not Role_user.
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .successHandler(loginSuccessHandler())
                    .failureHandler(loginFailureHandler())
                    .and()
                .logout()
                    .permitAll()
                    .logoutSuccessUrl("/login")
                    .and()
                .csrf();


    }

    private AuthenticationFailureHandler loginFailureHandler() {

        return (    request, response, authentication) -> {
            request.getSession().setAttribute("flash", "incorrect username or password");
            response.sendRedirect("/login");

        };
    }

    private AuthenticationSuccessHandler loginSuccessHandler() {
        return (request, response, authentication) ->response.sendRedirect("/") ;

    }
}

 */
