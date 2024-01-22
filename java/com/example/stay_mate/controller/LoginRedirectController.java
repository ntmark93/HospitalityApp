package com.example.stay_mate.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class LoginRedirectController {
    @RequestMapping("/login-redirect")
    public String redirect(@AuthenticationPrincipal UserDetails userDetails) throws Exception{
        for (GrantedAuthority authority: userDetails.getAuthorities()){
            if ("ROLE_USER".equals(authority.getAuthority())){
                return "redirect:/user/current";
            } else if ("ROLE_PARTNER".equals(authority.getAuthority())) {
                return "redirect:/partner/current";
            }
        }

        throw new Exception("Invalid role");
    }
}
