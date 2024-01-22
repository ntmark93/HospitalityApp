package com.example.stay_mate.service.partner;

import com.example.stay_mate.model.partner.Partner;
import com.example.stay_mate.repository.partner.PartnerRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PartnerService implements UserDetailsService {
    private final PartnerRepository partnerRepository;

    public PartnerService(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    // @PreAuthorize("hasRole('ROLE_PARTNER')")
    public void savePartner(Partner partner) {
        partnerRepository.save(partner);
    }

    public List<Partner> findAllPartner() {
        return partnerRepository.findAll();
    }

    public Partner getPartnerById(Integer id) {
        return partnerRepository.findPartnerById(id);
    }

    public Partner getPartnerByEmail(String email) {
        return partnerRepository.getPartnerByEmail(email);
    }

    public boolean isEmailAlreadyTaken(String email) {
        return partnerRepository.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return partnerRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                String.format("User not found:" + email)
                        )
                );
    }

    @Transactional
    public void deletePartnerById(Integer id) {
        partnerRepository.deletePartnerById(id);
    }

    public Partner findByEmail(String email) {
        return partnerRepository.findByEmail(email).orElse(null);
    }
}
