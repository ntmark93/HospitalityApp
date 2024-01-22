package com.example.stay_mate.repository.menubook;

import com.example.stay_mate.model.bar.Bar;
import com.example.stay_mate.model.hotel.Hotel;
import com.example.stay_mate.model.hotel.HotelBar;
import com.example.stay_mate.model.hotel.HotelRestaurant;
import com.example.stay_mate.model.menubook.MenuBook;
import com.example.stay_mate.model.partner.Partner;
import com.example.stay_mate.model.reservation.Reservation;
import com.example.stay_mate.model.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuBookRepository extends JpaRepository<MenuBook, Integer> {
    List<MenuBook> getMenuBookById(Integer id);
    List<MenuBook> getMenuBookByPartner(Partner partner);
    List<MenuBook> getMenuBookByRestaurant(Restaurant restaurant);
    List<MenuBook> getMenuBookByBar(Bar bar);
    List<MenuBook> getMenuBookByHotel(Hotel hotel);
    List<MenuBook> getMenuBookByHotelRestaurant(HotelRestaurant hotelRestaurant);
    List<MenuBook> getMenuBookByHotelBar(HotelBar hotelBar);
    List<MenuBook> getMenuBookByReservation(Reservation reservation); // ez még nincs használatban amíg nincs kész a reservation
    void deleteMenuBookById(Integer id);
    void deleteMenuBookByPartner(Partner partner);
    void deleteMenuBookByRestaurant(Restaurant restaurant);
    void deleteMenuBookByBar(Bar bar);
    void deleteMenuBookByHotel(Hotel hotel);
    void deleteMenuBookByHotelRestaurant(HotelRestaurant hotelRestaurant);
    void deleteMenuBookByHotelBar(HotelBar hotelBar);
    void deleteMenuBookByReservation(Reservation reservation);  // ez még nincs használatban amíg nincs kész a reservation
}
