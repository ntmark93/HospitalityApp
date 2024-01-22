package com.example.stay_mate.repository.hotel;

import com.example.stay_mate.model.hotel.Hotel;
import com.example.stay_mate.model.partner.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Integer> {
    List<Hotel> getHotelByPartner(Partner partner);
    void deleteHotelByPartner(Partner partner);
}
