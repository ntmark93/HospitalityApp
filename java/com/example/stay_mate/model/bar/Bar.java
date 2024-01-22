package com.example.stay_mate.model.bar;

import com.example.stay_mate.model.partner.Partner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bar")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Bar {
    @JoinColumn(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JoinColumn(name = "name")
    private String name;
    @JoinColumn(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name="partner_id")
    private Partner partner;
    @Column(length = 64)
    private String photo;

    @Override
    public String toString() {
        return name;
    }
}
