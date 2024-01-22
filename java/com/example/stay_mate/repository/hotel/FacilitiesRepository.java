package com.example.stay_mate.repository.hotel;

import com.example.stay_mate.model.hotel.Facilities;
import com.example.stay_mate.model.hotel.Hotel;
import com.example.stay_mate.model.partner.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilitiesRepository extends JpaRepository<Facilities, Integer> {
    List<Facilities> getFacilitiesByHotel(Hotel hotel);

    List<Facilities> getFacilitiesByPartner(Partner partner);

    void deleteFacilitiesByHotel(Hotel hotel);

    void deleteFacilitiesByPartner(Partner partner);
}
