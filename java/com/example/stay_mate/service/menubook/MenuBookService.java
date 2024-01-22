package com.example.stay_mate.service.menubook;

import com.example.stay_mate.model.bar.Bar;
import com.example.stay_mate.model.hotel.Hotel;
import com.example.stay_mate.model.hotel.HotelBar;
import com.example.stay_mate.model.hotel.HotelRestaurant;
import com.example.stay_mate.model.menubook.MenuBook;
import com.example.stay_mate.model.partner.Partner;
import com.example.stay_mate.model.reservation.Reservation;
import com.example.stay_mate.model.restaurant.Restaurant;
import com.example.stay_mate.repository.menubook.MenuBookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuBookService {
    private final MenuBookRepository menuBookRepository;

    public MenuBookService(MenuBookRepository menuBookRepository) {
        this.menuBookRepository = menuBookRepository;
    }

    public List<MenuBook> getAllMenuBook(){
        return menuBookRepository.findAll();
    }
    public MenuBook getMenuBookById(Integer id){
        return menuBookRepository.findById(id).orElse(null);
    }
    public List<MenuBook> getMenuBookByPartner(Partner partner){
        return menuBookRepository.getMenuBookByPartner(partner);
    }
    public List<MenuBook> getMenuBookByRestaurant(Restaurant restaurant){
        return menuBookRepository.getMenuBookByRestaurant(restaurant);
    }
    public List<MenuBook> getMenuBookByBar(Bar bar){
        return menuBookRepository.getMenuBookByBar(bar);
    }
    public List<MenuBook> getMenuBookByHotel(Hotel hotel){
        return menuBookRepository.getMenuBookByHotel(hotel);
    }
    public List<MenuBook> getMenuBookByHotelRestaurant(HotelRestaurant hotelRestaurant){
        return menuBookRepository.getMenuBookByHotelRestaurant(hotelRestaurant);
    }
    public List<MenuBook> getMenuBookByHotelBar(HotelBar hotelBar){
        return menuBookRepository.getMenuBookByHotelBar(hotelBar);
    }
    public List<MenuBook> getAllMenuBookByReservation(Reservation reservation){
        return menuBookRepository.getMenuBookByReservation(reservation);
    }
    public MenuBook saveMenuBook(MenuBook menuBook){
        return menuBookRepository.save(menuBook);
    }

    @Transactional
    public void deleteMenuBookById(Integer id){
        menuBookRepository.deleteById(id);
    }

    @Transactional
    public void deleteMenuBookByPartner(Partner partner){
        menuBookRepository.deleteMenuBookByPartner(partner);
    }
    @Transactional
    public void deleteMenuBookByRestaurant(Restaurant restaurant){
        menuBookRepository.deleteMenuBookByRestaurant(restaurant);
    }
    @Transactional
    public void deleteMenuBookByBar(Bar bar){
        menuBookRepository.deleteMenuBookByBar(bar);
    }
    @Transactional
    public void deleteMenuBookByHotel(Hotel hotel){
        menuBookRepository.deleteMenuBookByHotel(hotel);
    }
    @Transactional
    public void deleteMenuBookByHotelRestaurant(HotelRestaurant hotelRestaurant){
        menuBookRepository.deleteMenuBookByHotelRestaurant(hotelRestaurant);
    }
    @Transactional
    public void deleteMenuBookByHotelBar(HotelBar hotelBar){
        menuBookRepository.deleteMenuBookByHotelBar(hotelBar);
    }
    @Transactional
    public void deleteMenuBookByReservation(Reservation reservation){
        menuBookRepository.deleteMenuBookByReservation(reservation);
    }
}
