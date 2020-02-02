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

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@Controller
public class FileUploadController {

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
