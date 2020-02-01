package com.example.mycloud.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Hello {
    Logger logger = LoggerFactory.getLogger(Hello.class);
    @GetMapping("/")
    public String hello(){
        logger.info("hello");
        return "success";
    }
}
