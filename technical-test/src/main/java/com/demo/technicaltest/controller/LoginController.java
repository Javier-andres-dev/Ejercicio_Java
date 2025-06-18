package com.demo.technicaltest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/login-page")
    public String loginForm() {
        return "Debe crear una página de login en frontend o usar la default.";
    }
}
