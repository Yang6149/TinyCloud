package com.example.mycloud.Controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller

public class ImageUploadController {

    Logger logger = LoggerFactory.getLogger(ImageUploadController.class);
    @GetMapping("/ImageUpload")
    public String Upload(){
        return "myImage";
    }

    @PostMapping("/ImageUpload")
    public void Upload(@RequestParam("file")MultipartFile file) throws IOException {
        //这里不设置的话，默认保存在项目的根目录


    }




}