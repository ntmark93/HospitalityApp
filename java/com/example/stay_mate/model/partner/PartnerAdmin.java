package com.example.stay_mate.model.partner;

import com.example.stay_mate.model.partner.Partner;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "partner_admin")
public class PartnerAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @JoinColumn(name = "name")
    String name;
    @JoinColumn(name = "pos_rank")
    String posRank;
    @JoinColumn(name = "tel_number")
    String telNumber;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "partner_id")
    Partner partner;

    public PartnerAdmin(Integer id, String name, String posRank, String telNumber, Partner partner) {
        this.id = id;
        this.name = name;
        this.posRank = posRank;
        this.telNumber = telNumber;
        this.partner = partner;
    }

    public PartnerAdmin() {
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

    public String getPosRank() {
        return posRank;
    }

    public void setPosRank(String rank) {
        this.posRank = rank;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telephoneNumber) {
        this.telNumber = telephoneNumber;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    @Override
    public String toString() {
        return "PartnerAdmin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rank='" + posRank + '\'' +
                ", telephoneNumber='" + telNumber + '\'' +
                ", partner=" + partner +
                '}';
    }
}
