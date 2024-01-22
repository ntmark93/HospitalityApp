package com.example.stay_mate.controller;

import com.example.stay_mate.model.partner.Partner;
import com.example.stay_mate.model.partner.PartnerAdmin;
import com.example.stay_mate.service.partner.PartnerAdminService;
import com.example.stay_mate.service.partner.PartnerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class Rest {
    public final PartnerService partnerService;
    public final PartnerAdminService partnerAdminService;

    public Rest(PartnerService partnerService, PartnerAdminService partnerAdminService) {
        this.partnerService = partnerService;
        this.partnerAdminService = partnerAdminService;
    }

    @GetMapping("/partners")
    public List<Partner> getAllPartners() {
        return partnerService.findAllPartner();
    }

    @GetMapping("/partner_admins")
    public List<PartnerAdmin> getAllPartnerAdmins() {
        return partnerAdminService.findAllPartnerAdmins();
    }

    @GetMapping("/partner/{id}/partner-admin")
    public List<PartnerAdmin> getAllPartnerAdminsByPartner(@PathVariable("id") Integer id) {
        return partnerAdminService.getAllPartnerAdminByPartner(partnerService.getPartnerById(id));
    }

}
