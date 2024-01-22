package com.example.stay_mate.model.hotel;

import com.example.stay_mate.model.partner.Partner;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hotel")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JoinColumn(name = "name")
    private String name;
    @JoinColumn(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;
    @Column(length = 64)
    private String photo;

    @Override
    public String toString() {
        return name;
    }
}
