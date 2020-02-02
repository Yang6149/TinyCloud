package com.example.mycloud.Controller;

import com.example.mycloud.Util.PathUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Controller
public class FileUploadController {
    Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    @PostMapping("upload")
    public String upload(@RequestParam("file") MultipartFile file,HttpSession session) throws IOException {
        String filePath = file.getOriginalFilename();
        File cur = (File) session.getAttribute("curPath");
        File filename = new File(cur,filePath);

        logger.info("保存路径为{}",filename.getAbsolutePath());
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filename));
        outputStream.write(file.getBytes());
        outputStream.flush();
        outputStream.close();
        return "redirect:index";
    }
    @Test
    public void test(){
        File ff = new File("C:\\Users\\hasaki\\Desktop\\bc");

        for (File file : ff.listFiles()) {
            if (file.isDirectory()){
                System.out.println("this is directory " + file.getName());
            }else {
                System.out.println("`````` this  is a file "+ file.getName());
            }
        }
    }
}
