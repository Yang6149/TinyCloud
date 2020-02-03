package com.example.mycloud.Controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller

public class ImageController {
    @RequestMapping(value = {"/img"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS},produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] execute() throws IOException {
        // img为图片的二进制流
        FileInputStream fis = new FileInputStream(new File("repo//图片1.png"));

        byte[] img = new byte[fis.available()];

        fis.read(img,0,fis.available());
        return img;
    }
}
