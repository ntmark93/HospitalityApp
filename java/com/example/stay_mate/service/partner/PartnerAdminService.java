package com.example.stay_mate.service.partner;

import com.example.stay_mate.model.partner.Partner;
import com.example.stay_mate.model.partner.PartnerAdmin;
import com.example.stay_mate.repository.partner.PartnerAdminRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PartnerAdminService {
    private final PartnerAdminRepository partnerAdminRepository;

    public PartnerAdminService(PartnerAdminRepository partnerAdminRepository) {
        this.partnerAdminRepository = partnerAdminRepository;
    }

    public void savePartnerAdmin(PartnerAdmin partnerAdmin) {
        partnerAdminRepository.save(partnerAdmin);
    }

    public List<PartnerAdmin> findAllPartnerAdmins() {
        return partnerAdminRepository.findAll();
    }

    public List<PartnerAdmin> getAllPartnerAdminByPartner(Partner partner) {
        return partnerAdminRepository.getPartnerAdminByPartner(partner);
    }

    public PartnerAdmin getPartnerAdminById(Integer id) {
        return partnerAdminRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deletePartnerAdminByPartner(Partner partner) {
        partnerAdminRepository.deletePartnerAdminByPartner(partner);
    }
    @Transactional
    public void deletePartnerAdminById(Integer partnerAdminId) {
        partnerAdminRepository.deletePartnerAdminById(partnerAdminId);
    }
}
