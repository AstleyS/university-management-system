package com.ums.ums_backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Hello from UMS Backend!";
    }
}