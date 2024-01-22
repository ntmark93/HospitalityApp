package com.example.stay_mate.model.menubook;

import com.example.stay_mate.model.bar.Bar;
import com.example.stay_mate.model.hotel.Hotel;
import com.example.stay_mate.model.hotel.HotelBar;
import com.example.stay_mate.model.hotel.HotelRestaurant;
import com.example.stay_mate.model.partner.Partner;
import com.example.stay_mate.model.reservation.Reservation;
import com.example.stay_mate.model.restaurant.Restaurant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "menu_book")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MenuBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private Integer id;
    @JoinColumn(name = "name")
    private String name;
    @JoinColumn(name = "description")
    private String description;
    @JoinColumn(name = "price")
    private double price;
    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @ManyToOne
    @JoinColumn(name = "bar_id")
    private Bar bar;
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
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
    @Column(length = 64)
    private String photo;
}
