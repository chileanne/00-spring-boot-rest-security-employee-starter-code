package com.luv2code.springboot.cruddemo.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {


    //add support using jdbc for roles ....no more hardcoding
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){

        return new JdbcUserDetailsManager(dataSource);
    }




    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(configurer->
                configurer
                        .requestMatchers(HttpMethod.GET,"/api/employees").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET,"/api/employees/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST,"/api/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT,"/api/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET,"/api/employees").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET,"/api/employees/**").hasRole("ADMIN")

        );


        ///USE HTTP BASIC AUTHENTICATION
        http.httpBasic(Customizer.withDefaults());

        //disable cross site request forgery(CRF)
        //in general not needed for stateless REST API that use post,get,put,delete or Patch

        http.csrf(csrf-> csrf.disable());

        return  http.build();
    }


    //hard coding roles for security
    /*
     @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager (){

        UserDetails john = User.builder()
                .username("john")
                .password("{noop}johnny")
                .roles("EMPLOYEE")
                .build();


        UserDetails mary = User.builder()
                .username("mary")
                .password("{noop}johnny")
                .roles("EMPLOYEE", "MANAGER")
                .build();



        UserDetails susan = User.builder()
                .username("susan")
                .password("{noop}johnny")
                .roles("EMPLOYEE", "MANAGER", "ADMIN")
                .build();


        return  new InMemoryUserDetailsManager(john,mary,susan);





    }
    */

}
