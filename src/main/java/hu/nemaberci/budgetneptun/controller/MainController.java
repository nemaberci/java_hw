package hu.nemaberci.budgetneptun.controller;

import hu.nemaberci.budgetneptun.service.UserService;
import java.util.Map;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("/")
public class MainController {

    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    private String index() {
        return "core/index";
    }

    @GetMapping("/signup")
    private String signup() {
        return "core/signup";
    }

    @PostMapping("/signup")
    private String afterSignup(@RequestParam Map<String, String> body) {
        userService.create(body.get("neptuncode"), body.get("password"));
        return "redirect:/";
    }

}
