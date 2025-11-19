package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
//    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
    @GetMapping("/hello")
    public String helloWorld(Model model) {
        model.addAttribute("message", "Hello World!");
        return model.getAttribute("message").toString();
    }
}