package com.example.mycloud.Controller;

import com.example.mycloud.Util.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.Stack;

@Controller
public class Hello {
    Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    Stack<File> stack = new Stack<>();
    @GetMapping("/index")
    public String index(HttpSession session, Model model){
        if (session.getAttribute("curPath")==null){
            session.setAttribute("curPath",new File("repo"));
        }
        File curPath= (File) session.getAttribute("curPath");
        List<List<File>>list = PathUtil.getChildDirectoryAndFile(curPath);
        model.addAttribute("directorys",list.get(0));
        model.addAttribute("files",list.get(1));
        model.addAttribute("deep",stack.size());
        return "success";
    }

    @GetMapping("/index/childDir/{name}")
    public String childDir(@PathVariable("name") String name, HttpSession session){
        logger.info(name);
        File f = PathUtil.getOneDirect((File)session.getAttribute("curPath"),name);
        stack.add(f);
        session.setAttribute("curPath",f);
        logger.info("进入下一子目录 {}",f.getName());
        return "redirect:/index";
    }


    @GetMapping("/lastPath")
    public String lastPath(HttpSession session){
        stack.pop();
        File f = (File)session.getAttribute("curPath");
        session.setAttribute("curPath",f.getParentFile());
        logger.info("从{}进入父目录 {}",f.getName(),f.getParentFile().getName());
        return "redirect:/index";
    }
}
