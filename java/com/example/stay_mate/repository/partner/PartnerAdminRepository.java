package com.example.stay_mate.repository.partner;

import com.example.stay_mate.model.partner.Partner;
import com.example.stay_mate.model.partner.PartnerAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerAdminRepository extends JpaRepository<PartnerAdmin, Integer> {
    List<PartnerAdmin> getPartnerAdminByPartner(Partner partner);
    void deletePartnerAdminByPartner(Partner partner);
    void deletePartnerAdminById(Integer id);

}
