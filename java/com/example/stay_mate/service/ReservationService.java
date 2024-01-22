package com.example.stay_mate.service;

import com.example.stay_mate.model.bar.Bar;
import com.example.stay_mate.model.hotel.Hotel;
import com.example.stay_mate.model.hotel.HotelBar;
import com.example.stay_mate.model.hotel.HotelRestaurant;
import com.example.stay_mate.model.partner.Partner;
import com.example.stay_mate.model.reservation.Reservation;
import com.example.stay_mate.model.restaurant.Restaurant;
import com.example.stay_mate.model.user.User;
import com.example.stay_mate.repository.reservation.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void saveReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    // TODO ehhez kell majd a kontrollermetódust kitalálni illetve majd az étteremhez és a bárhoz
    public Double getPriceOfReservation(Integer id) {
        return reservationRepository.getTotalPriceOfReservation(id);
    }
    public List<Reservation> getReservationByUser(User user){
        return reservationRepository.getReservationByUser(user);
    }


    public double getTotalPrice (LocalDateTime startDate, LocalDateTime endDate){
        return ChronoUnit.DAYS.between(startDate,endDate);
    }


    public List<Reservation> getAllReservation() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Integer id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public List<Reservation> getAllReservationByPartner(Partner partner) {
        return reservationRepository.getReservationByPartner(partner);
    }

    public List<Reservation> getAllReservationByRestaurant(Restaurant restaurant) {
        return reservationRepository.getReservationByRestaurant(restaurant);
    }

    public List<Reservation> getReservationByBar(Bar bar) {
        return reservationRepository.getReservationByBar(bar);
    }

    public List<Reservation> getReservationByHotel(Hotel hotel) {
        return reservationRepository.getReservationByHotel(hotel);
    }

    public List<Reservation> getReservationByHotelRestaurant(HotelRestaurant hotelRestaurant) {
        return reservationRepository.getReservationByHotelRestaurant(hotelRestaurant);
    }

    public List<Reservation> getReservationByHotelBar(HotelBar hotelBar) {
        return reservationRepository.getReservationByHotelBar(hotelBar);
    }

    public List<Reservation> getReservationToExactDate(LocalDateTime start, LocalDateTime end) {
        return reservationRepository.findAllByStartDateIsBetweenOrEndDateIsBetween(start, end, start, end);
    }

    @Transactional
    public void deleteReservationById(Integer id) {
        reservationRepository.deleteById(id);
    }

    @Transactional
    public void deleteReservationByPartner(Partner partner) {
        reservationRepository.deleteReservationByPartner(partner);
    }

    @Transactional
    public void deleteReservationByRestaurant(Restaurant restaurant) {
        reservationRepository.deleteReservationByRestaurant(restaurant);
    }

    @Transactional
    public void deleteReservationByBar(Bar bar) {
        reservationRepository.deleteReservationByBar(bar);
    }

    @Transactional
    public void deleteReservationByHotel(Hotel hotel) {
        reservationRepository.deleteReservationByHotel(hotel);
    }

    @Transactional
    public void deleteReservationByHotelRestaurant(HotelRestaurant hotelRestaurant) {
        reservationRepository.deleteReservationByHotelRestaurant(hotelRestaurant);
    }

    @Transactional
    public void deleteReservationByHotelBar(HotelBar hotelBar) {
        reservationRepository.deleteReservationByHotelBar(hotelBar);
    }
    @Transactional
    public void deleteReservationByUser(User user) {
        reservationRepository.deleteReservationByUser(user);
    }
}
