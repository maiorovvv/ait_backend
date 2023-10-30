package de.ait.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserControllerWeb {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/task")
    public String task() {
        return "task";
    }
}
