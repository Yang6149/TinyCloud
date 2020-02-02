package com.example.mycloud.Controller;

import com.example.mycloud.Util.PathUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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



    @GetMapping("/download/{name}")
    //@RequestMapping("/download")
    public ResponseEntity download(@PathVariable("name")String name ,HttpSession session ) throws IOException {
        FileSystemResource file = new FileSystemResource(session.getAttribute("curPath")+File.separator+name);
        HttpHeaders headers = new HttpHeaders();
        //在响应头中添加这个，设置下载文件默认的名称
        headers.add("Content-Disposition","attachment");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))// 这个属性表示相应的内容是通过字节流的方式进行传输的
                .body(new InputStreamResource(file.getInputStream()));
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
