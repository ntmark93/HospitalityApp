package com.example.stay_mate.service.bar;

import com.example.stay_mate.model.bar.Bar;
import com.example.stay_mate.model.partner.Partner;
import com.example.stay_mate.repository.bar.BarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BarService {
    public final BarRepository barRepository;

    public BarService(BarRepository barRepository) {
        this.barRepository = barRepository;
    }

    public List<Bar> getAllBar() {
        return barRepository.findAll();
    }

    public void saveBar(Bar bar) {
        barRepository.save(bar);
    }

    public Bar getBarById(Integer id) {
        return barRepository.findById(id).orElse(null);
    }

    public void deleteBarById(Integer id) {
        barRepository.deleteById(id);
    }

    public void delete(Bar bar) {
        barRepository.delete(bar);
    }

    @Transactional
    public void deleteBarByPartner(Partner partner) {
        barRepository.deleteBarByPartner(partner);
    }

    public List<Bar> getBarByPartner(Partner partner) {
        return barRepository.getBarByPartner(partner);
    }
}
