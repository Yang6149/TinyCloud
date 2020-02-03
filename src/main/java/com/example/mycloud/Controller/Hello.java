package com.example.mycloud.Controller;

import com.example.mycloud.Util.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

@Controller
public class Hello {
    Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    @GetMapping("/index")
    public String index(HttpSession session, Model model){
        if (session.getAttribute("curPath")==null){
            File init = new File("repo");
            session.setAttribute("curPath",init );
            Stack stack = new Stack<>();
            session.setAttribute("stack",stack);
            stack.add(init);

        }
        logger.info("当前线程id为:{}",Thread.currentThread().getId());
        logger.info(session.getAttribute("stack").toString());
        File curPath= (File) session.getAttribute("curPath");
        List<List<File>>list = PathUtil.getChildDirectoryAndFile(curPath);
        model.addAttribute("directorys",list.get(0));
        model.addAttribute("files",list.get(1));
        model.addAttribute("stack",session.getAttribute("stack"));
        return "success";
    }

    @GetMapping("/index/childDir/{name}")
    public String childDir(@PathVariable("name") String name, HttpSession session){
        logger.info(name);
        File f = PathUtil.getOneDirect((File)session.getAttribute("curPath"),name);
        ((Stack)session.getAttribute("stack")).add(f);
        session.setAttribute("curPath",f);
        logger.info("进入下一子目录 {}",f.getName());
        return "redirect:/index";
    }


    @GetMapping("/lastPath")
    public String lastPath(HttpSession session){
        ((Stack)session.getAttribute("stack")).pop();
        File f = (File)session.getAttribute("curPath");
        session.setAttribute("curPath",f.getParentFile());
        logger.info("从{}进入父目录 {}",f.getName(),f.getParentFile().getName());
        return "redirect:/index";
    }

    @GetMapping("/last/{num}")
    public String last(@PathVariable("num")String num,Model model,HttpSession session){
        int size = ((Stack)session.getAttribute("stack")).size();
        int count =size-Integer.parseInt(num);
        logger.info("进入第{}个界面",count);
        while (count-->1){
            ((Stack)session.getAttribute("stack")).pop();
        }
        session.setAttribute("curPath",((Stack)session.getAttribute("stack")).peek());
        return "redirect:/index";
    }

    @PostMapping("/create")
    public String mkdir(String dir,HttpSession session) throws IOException {
        File cur = (File) session.getAttribute("curPath");
        File newfile = new File(cur,dir);
        if (!newfile.exists()){
            newfile.mkdir();
        }
        return "redirect:index";
    }

//    @PostMapping("/delete")
//    public String deleteFile(){
//        return
//    }

}
