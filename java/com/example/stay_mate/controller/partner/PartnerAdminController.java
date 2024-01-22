package com.example.stay_mate.controller.partner;

import com.example.stay_mate.model.partner.PartnerAdmin;
import com.example.stay_mate.model.restaurant.Restaurant;
import com.example.stay_mate.service.partner.PartnerAdminService;
import com.example.stay_mate.service.partner.PartnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/partner-admin")
public class PartnerAdminController {
    private final PartnerAdminService partnerAdminService;
    private final PartnerService partnerService;

    public PartnerAdminController(PartnerAdminService partnerAdminService, PartnerService partnerService) {
        this.partnerAdminService = partnerAdminService;
        this.partnerService = partnerService;
    }
    @GetMapping("/all")
    public String findAllPartnerAdmins(Model model) {
        model.addAttribute("all_partneradmins", partnerAdminService.findAllPartnerAdmins());
        return "partner-admin-list";
    }
    @GetMapping("/{id}")
    public String getCurrentPartner(Model model, @PathVariable("id") Integer partnerAdmin) {
        model.addAttribute("partner_admin", partnerAdminService.getPartnerAdminById(partnerAdmin));
        return "partner-admin";
    }

    @GetMapping("/new/{partner-id}")
    public String createPartnerAdmin(Model model, @PathVariable("partner-id") Integer partnerId) {
        model.addAttribute("partnerId", partnerId);
        model.addAttribute("new_partner_admin", new PartnerAdmin());
        return "new-partner-admin-form";
    }

    @PostMapping("/add/{partner-id}")
    public String createPartnerAdmin(@ModelAttribute("new_partner_admin") PartnerAdmin partnerAdmin, @PathVariable("partner-id") Integer partnerId) {
        partnerAdmin.setPartner(partnerService.getPartnerById(partnerId));
        partnerAdminService.savePartnerAdmin(partnerAdmin);
        return "redirect:/partner/current";
        // LIA: itt nem tudom mi lenne a megfelelő. Menjen a HOME oldalra, vagy a Partner oldalára?
    }

    @GetMapping("/{id}/update")
    public String updatePartnerAdmin(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("partner_admin", partnerAdminService.getPartnerAdminById(id));
        return "partner-admin-update";
    }

    @PostMapping("/{id}/update")
    public String updatePartnerAdmin(@ModelAttribute("partner_admin") PartnerAdmin updated, @PathVariable Integer id) {
        partnerAdminService.savePartnerAdmin(updated);
        return "redirect:/partner/current";
    }
    @PostMapping("/delete/{partner-admin-id}")
    public String deleteAdmin(@PathVariable("partner-admin-id") Integer partnerAdminId){
        partnerAdminService.deletePartnerAdminById(partnerAdminId);
        return "redirect:/partner/current";
    }
}
