package com.example.stay_mate.repository.hotel;

import com.example.stay_mate.model.hotel.Hotel;
import com.example.stay_mate.model.hotel.HotelRestaurant;
import com.example.stay_mate.model.partner.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRestaurantRepository extends JpaRepository<HotelRestaurant, Integer> {
    List<HotelRestaurant> getHotelRestaurantByHotel(Hotel hotel);
    void deleteHotelRestaurantByPartner(Partner partner);
    void deleteHotelRestaurantByHotel(Hotel hotel);
    List<HotelRestaurant> getHotelRestaurantByPartner(Partner partner);
}
