package com.example.stay_mate.controller.page;

import com.example.stay_mate.model.partner.Partner;
import com.example.stay_mate.service.partner.PartnerAdminService;
import com.example.stay_mate.service.partner.PartnerService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    private final PartnerService partnerService;
    private final PartnerAdminService partnerAdminService;

    public PageController(PartnerService partnerService, PartnerAdminService partnerAdminService) {
        this.partnerService = partnerService;
        this.partnerAdminService = partnerAdminService;
    }
    /*
    @GetMapping({"", "/"})
    public String getHome(Model model) {
        model.addAttribute("partner", partnerService.findAllPartner());
        return "partner-list";
    }

     */


    @GetMapping("/")
    public String getHome(@AuthenticationPrincipal Partner partner) {
        if (partner != null) {
            return "redirect:/partner/current";
        }
        return "home";
    }
}
