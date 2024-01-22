package com.example.stay_mate.model.room;

import com.example.stay_mate.model.hotel.Hotel;
import com.example.stay_mate.model.partner.Partner;
import com.example.stay_mate.model.reservation.Reservation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "room")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String roomNumber;
    private Integer roomCapacity;
    private Double pricePerDay;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;
    @OneToMany(mappedBy = "room")
    @JsonBackReference
    private List<Reservation> reservations;
    @Column(length = 64)
    private String photo;

    @Override
    public String toString() {
        return
                "Room number=" + roomNumber +
                ", Hotel=" + hotel;
    }
}
