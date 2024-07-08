package com.spring.security.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping
    public String helloWorldPublic() {
        return "Hello, World! SECURED FOR ALL USERS!";
    }

    @PostMapping
    public String helloWorld() {
        return "Hello, World! SECURED FOR ONLY ADMINS!";
    }
}
