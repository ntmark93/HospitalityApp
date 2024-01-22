package com.example.stay_mate.service.hotel;

import com.example.stay_mate.model.hotel.Hotel;
import com.example.stay_mate.model.hotel.HotelBar;
import com.example.stay_mate.model.partner.Partner;
import com.example.stay_mate.repository.hotel.HotelBarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HotelBarService {
    public final HotelBarRepository hotelBarRepository;

    public HotelBarService(HotelBarRepository hotelBarRepository) {
        this.hotelBarRepository = hotelBarRepository;
    }

    public List<HotelBar> getAllHotelBars() {
        return hotelBarRepository.findAll();
    }

    public void saveHotelBar(HotelBar hotelBar) {
        hotelBarRepository.save(hotelBar);
    }

    public HotelBar getHotelBarById(Integer id) {
        return hotelBarRepository.findById(id).orElse(null);
    }

    public List<HotelBar> getHotelBarByHotel(Hotel hotel) {
        return hotelBarRepository.getHotelBarByHotel(hotel);
    }

    @Transactional
    public void deleteHotelBarById(Integer id) {
        hotelBarRepository.deleteById(id);
    }
    @Transactional
    public void deleteHotelBarByHotel(Hotel hotel){
        hotelBarRepository.deleteHotelBarByHotel(hotel);
    }

    @Transactional
    public void deleteBarByPartner(Partner partner) {
        hotelBarRepository.deleteHotelBarByPartner(partner);
    }

    public List<HotelBar> getHotelBarByPartner(Partner partner) {
        return hotelBarRepository.getHotelBarByPartner(partner);
    }
}
