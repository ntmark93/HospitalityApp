package com.example.stay_mate.service.hotel;

import com.example.stay_mate.model.hotel.Facilities;
import com.example.stay_mate.model.hotel.Hotel;
import com.example.stay_mate.model.partner.Partner;
import com.example.stay_mate.repository.hotel.FacilitiesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FacilitiesService {
    public final FacilitiesRepository facilitiesRepository;

    public FacilitiesService(FacilitiesRepository facilitiesRepository) {
        this.facilitiesRepository = facilitiesRepository;
    }

    public List<Facilities> getAllFacilities() {
        return facilitiesRepository.findAll();
    }

    public Facilities getFacilitiesById(Integer id) {
        return facilitiesRepository.getReferenceById(id);
    }

    public void saveFacilities(Facilities facilities) {
        facilitiesRepository.save(facilities);
    }

    public Object findAllFacilities() {
        return facilitiesRepository.findAll();
    }

    @Transactional
    public void deleteFacilitiesById(Integer id) {
        facilitiesRepository.deleteById(id);
    }

    @Transactional
    public void deleteFacilitiesByHotel(Hotel hotel) {
        facilitiesRepository.deleteFacilitiesByHotel(hotel);}
    @Transactional
    public void deleteFacilitiesByPartner(Partner partner){
        facilitiesRepository.deleteFacilitiesByPartner(partner);
    }

    public List<Facilities> getFacilitiesByHotel(Hotel hotel) {
        return facilitiesRepository.getFacilitiesByHotel(hotel);
    }
    public List<Facilities> getFacilitiesByPartner(Partner partner){
        return facilitiesRepository.getFacilitiesByPartner(partner);
    }
}
