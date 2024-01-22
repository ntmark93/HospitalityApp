package com.example.stay_mate.repository.hotel;

import com.example.stay_mate.model.hotel.Hotel;
import com.example.stay_mate.model.hotel.HotelBar;
import com.example.stay_mate.model.partner.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelBarRepository extends JpaRepository<HotelBar, Integer> {
    void deleteHotelBarByPartner(Partner partner);
    void deleteHotelBarByHotel(Hotel hotel);

    List<HotelBar> getHotelBarByPartner(Partner partner);

    List<HotelBar> getHotelBarByHotel(Hotel hotel);
}
