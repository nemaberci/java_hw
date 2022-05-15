package hu.nemaberci.budgetneptun.controller;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller("/")
public class MainController {

    @GetMapping("")
    private String index() {
        return "core/index";
    }

    @GetMapping("/signup")
    private String signup() {
        return "core/signup";
    }

    @PostMapping("/signup")
    private String afterSignup() {
        return "redirect:/";
    }

}
