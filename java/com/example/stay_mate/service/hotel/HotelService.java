package com.example.stay_mate.service.hotel;

import com.example.stay_mate.model.hotel.Hotel;
import com.example.stay_mate.model.partner.Partner;
import com.example.stay_mate.repository.hotel.HotelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HotelService {
    public final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }


    public List<Hotel> findAllHotel() {
        return hotelRepository.findAll();
    }

    public void saveHotel(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    public Hotel getHotelById(Integer id) {
        return hotelRepository.getReferenceById(id);
    }

    @Transactional // Ide be kellett rakni a @Transactional-t mert anélkül nem hibás
    public void deleteHotelById(Integer id) {
        hotelRepository.deleteById(id);
    }

    @Transactional
    public void deleteHotelByPartner(Partner partner) {
        hotelRepository.deleteHotelByPartner(partner);
    }

    public List<Hotel> getHotelByPartner(Partner partner) {
        return hotelRepository.getHotelByPartner(partner);
    }
}
