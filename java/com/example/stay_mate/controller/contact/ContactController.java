package com.example.stay_mate.controller.contact;

import com.example.stay_mate.service.contact.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {
    private final EmailService emailService;

    public ContactController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/contact")
    public String showContactForm(Model model) {
        return "contact";
    }


    @PostMapping("/contact")
    public String submitForm(@RequestParam("name") String name,
                             @RequestParam("email") String email,
                             @RequestParam("subject") String subject,
                             @RequestParam("message") String message) {
        // E-mail elküldése
        String emailText = "Név: " + name + "\nE-mail cím: " + email + "\nTárgy: " + subject + "\nÜzenet:\n" + message;
        emailService.sendEmail("23staymate23@gmail.com", "Kapcsolatfelvétel", emailText);

        return "message";
    }
}
