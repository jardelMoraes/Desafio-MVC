package com.desafio.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.desafio.mvc.services.DBService;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DBService dbServise;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;
    @Bean
    public boolean instantiatingDataBase() {
        if(strategy.equals("create-drop"))
        this.dbServise.instantiateDatabase();
        return false;
    }
    
}
