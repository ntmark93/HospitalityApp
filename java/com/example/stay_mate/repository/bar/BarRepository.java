package com.example.stay_mate.repository.bar;

import com.example.stay_mate.model.bar.Bar;
import com.example.stay_mate.model.partner.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarRepository extends JpaRepository<Bar, Integer> {
    List<Bar> getBarByPartner(Partner partner);
    void deleteBarByPartner(Partner partner);
}
