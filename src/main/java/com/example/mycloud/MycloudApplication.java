package com.example.mycloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MycloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(MycloudApplication.class, args);
    }

}
