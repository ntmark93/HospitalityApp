package com.example.stay_mate.service.hotel;

import com.example.stay_mate.model.hotel.Hotel;
import com.example.stay_mate.model.hotel.HotelRestaurant;
import com.example.stay_mate.model.partner.Partner;
import com.example.stay_mate.repository.hotel.HotelRestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HotelRestaurantService {
    private final HotelRestaurantRepository hotelRestaurantRepository;

    public HotelRestaurantService(HotelRestaurantRepository hotelRestaurantRepository) {
        this.hotelRestaurantRepository = hotelRestaurantRepository;
    }

    public List<HotelRestaurant> getAllHotelRestaurants() {
        return hotelRestaurantRepository.findAll();
    }

    public void saveHotelRestaurant(HotelRestaurant hotelRestaurant) {
        hotelRestaurantRepository.save(hotelRestaurant);
    }

    public HotelRestaurant getHotelRestaurantById(Integer id) {
        return hotelRestaurantRepository.findById(id).orElse(null);
    }

    public List<HotelRestaurant> getHotelRestaurantByHotel(Hotel hotel) {
        return hotelRestaurantRepository.getHotelRestaurantByHotel(hotel);
    }

    @Transactional
    public void deleteHotelRestaurantById(Integer id) {
        hotelRestaurantRepository.deleteById(id);
    }

    @Transactional
    public void deleteHotelRestaurantByPartner(Partner partner) {
        hotelRestaurantRepository.deleteHotelRestaurantByPartner(partner);
    }
    @Transactional
    public void deleteHotelRestaurantByHotel(Hotel hotel){
        hotelRestaurantRepository.deleteHotelRestaurantByHotel(hotel);
    }

    public List<HotelRestaurant> getHotelRestaurantByPartner(Partner partner) {
        return hotelRestaurantRepository.getHotelRestaurantByPartner(partner);
    }
}
