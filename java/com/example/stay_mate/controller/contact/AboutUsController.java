package com.example.stay_mate.controller.contact;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutUsController {


    @GetMapping("/aboutus")
    public String aboutPage() {
        return "aboutus";
    }
}
