package com.example.stay_mate.model.partner;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "partner")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Partner implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JoinColumn(name = "partner_name")
    private String partnerName;
    @JoinColumn(name = "address")
    private String address;
    @JoinColumn(name = "tel_num")
    private String telNum;
    @JoinColumn(name = "email")
    private String email;
    @JoinColumn(name = "password")
    private String password;
    @JoinColumn(name = "company_reg_num")
    private String companyRegNum;
    @JoinColumn(name = "tax_num")
    private String taxNum;
    @Column(length = 64)
    private String photo;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_PARTNER");
        return Collections.singleton(authority);
    }

    @Override
    public java.lang.String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "Partner{" +
                "id=" + id +
                ", partnerName='" + partnerName + '\'' +
                ", address='" + address + '\'' +
                ", telNum='" + telNum + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", companyRegNum=" + companyRegNum +
                ", taxNum=" + taxNum +
                '}';
    }
}
