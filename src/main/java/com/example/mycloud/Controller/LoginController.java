package com.example.mycloud.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@RequestParam String password, HttpSession session , RedirectAttributes attributes){
        boolean isTrue=password.equals("614989");
        if (isTrue){
            session.setAttribute("password","614989");
            return "redirect:/";
        }else {
            attributes.addFlashAttribute("message","password is wrong");
            return "redirect:/login";
        }
    }
}
