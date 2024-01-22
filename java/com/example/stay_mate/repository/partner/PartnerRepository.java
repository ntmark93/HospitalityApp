package com.example.stay_mate.repository.partner;

import com.example.stay_mate.model.partner.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Integer> {
    Partner findPartnerById(Integer id);

    Optional<Partner> findByEmail(String email);
    void deletePartnerById(Integer id);
    Partner getPartnerByEmail(String email);

    boolean existsByEmail(String email);
}
