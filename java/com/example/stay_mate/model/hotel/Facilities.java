package com.example.stay_mate.model.hotel;

import com.example.stay_mate.model.partner.Partner;
import jakarta.persistence.*;


@Entity
@Table(name = "facilities")
public class Facilities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private double price;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    public Facilities(Integer id, String name, String description, double price, Hotel hotel, Partner partner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.hotel = hotel;
        this.partner = partner;
    }

    public Facilities() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }
}

