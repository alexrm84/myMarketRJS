package alexrm84.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String showIndex(Model model){
//        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
