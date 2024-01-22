package com.example.stay_mate.repository.reservation;

import com.example.stay_mate.model.bar.Bar;
import com.example.stay_mate.model.hotel.Hotel;
import com.example.stay_mate.model.hotel.HotelBar;
import com.example.stay_mate.model.hotel.HotelRestaurant;
import com.example.stay_mate.model.partner.Partner;
import com.example.stay_mate.model.reservation.Reservation;
import com.example.stay_mate.model.restaurant.Restaurant;
import com.example.stay_mate.model.room.Room;
import com.example.stay_mate.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByStartDateIsBetweenOrEndDateIsBetween(LocalDateTime start, LocalDateTime end, LocalDateTime startD, LocalDateTime endD);
    List<Reservation> getReservationById(Integer id);
    List<Reservation> getReservationByUser(User user);

    Integer countDistinctByStartDateAndEndDateAndUserNumberAndRoomPricePerDay(LocalDateTime start, LocalDateTime end, Integer userNumber, Double pricePerNight);
    Integer countByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDateTime startDate, LocalDateTime endDate);
    @Query(value = "SELECT SUM (r.pricePerDay)FROM Room r WHERE r.reservations = :id")
    Double getTotalPriceOfReservation(@Param("id") Integer id);

    List<Reservation> getReservationByPartner(Partner partner);

    List<Reservation> getReservationByRestaurant(Restaurant restaurant);

    List<Reservation> getReservationByBar(Bar bar);

    List<Reservation> getReservationByHotel(Hotel hotel);

    List<Reservation> getReservationByHotelRestaurant(HotelRestaurant hotelRestaurant);

    List<Reservation> getReservationByHotelBar(HotelBar hotelBar);

    void deleteReservationById(Integer id);

    void deleteReservationByPartner(Partner partner);

    void deleteReservationByRestaurant(Restaurant restaurant);

    void deleteReservationByBar(Bar bar);

    void deleteReservationByHotel(Hotel hotel);

    void deleteReservationByHotelRestaurant(HotelRestaurant hotelRestaurant);

    void deleteReservationByHotelBar(HotelBar hotelBar);
    void deleteReservationByUser(User user);

}
