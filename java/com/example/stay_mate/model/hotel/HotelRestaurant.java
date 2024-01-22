package com.example.stay_mate.model.hotel;

import com.example.stay_mate.model.partner.Partner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hotel_restaurant")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HotelRestaurant {
    @Id
    @JoinColumn(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JoinColumn(name = "name")
    private String name;
    @JoinColumn(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    @Column(length = 64)
    private String photo;

    @Override
    public String toString() {
        return name;
    }
}
