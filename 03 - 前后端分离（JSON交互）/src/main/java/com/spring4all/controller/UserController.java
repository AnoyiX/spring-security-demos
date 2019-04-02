package com.spring4all.controller;

import com.spring4all.bean.WebResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    @GetMapping("/user")
    public WebResponse user(@AuthenticationPrincipal Principal principal){
        return WebResponse.success(principal.getName());
    }

    @GetMapping("/admin")
    public WebResponse admin(@AuthenticationPrincipal Principal principal){
        return WebResponse.success(principal.getName());
    }

}
