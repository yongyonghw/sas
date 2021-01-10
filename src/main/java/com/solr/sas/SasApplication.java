package com.solr.sas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({WebConfig.class,SpringFoxConfig.class})
@SpringBootApplication
public class SasApplication {

    public static void main(String[] args) {
        
        SpringApplication.run(SasApplication.class, args);
    }

}
