package com.example.stay_mate.model.reservation;

import com.example.stay_mate.model.bar.Bar;
import com.example.stay_mate.model.hotel.Hotel;
import com.example.stay_mate.model.hotel.HotelBar;
import com.example.stay_mate.model.hotel.HotelRestaurant;
import com.example.stay_mate.model.partner.Partner;
import com.example.stay_mate.model.restaurant.Restaurant;
import com.example.stay_mate.model.room.Room;
import com.example.stay_mate.model.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private Integer id;
    @JoinColumn(name = "start_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime startDate;
    @JoinColumn(name = "end_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime endDate;
    @JoinColumn(name = "user_number")
    private Integer userNumber;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    @ManyToOne
    @JoinColumn(name = "hotel_restaurant_id")
    private HotelRestaurant hotelRestaurant;
    @ManyToOne
    @JoinColumn(name = "hotel_bar_id")
    private HotelBar hotelBar;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "room_id")
    private Room room;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @ManyToOne
    @JoinColumn(name = "bar_id")
    private Bar bar;

    @Override
    public String toString() {
        return
                "Start of reservation: " + startDate + ", End of reservation" + endDate +
                ", Number of guests: " + userNumber +
                ", Hotel name: " + hotel +
                ", Hotel restaurant name: " + hotelRestaurant +
                ", Hotel bar name: " + hotelBar +
                ", Room number: " + room +
                ", Restaurant name: " + restaurant +
                ", Bar name: " + bar;
    }
}
