package com.example.stay_mate.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private Integer id;
    @JoinColumn(name = "sur_name")
    private java.lang.String surName;
    @JoinColumn(name = "first_name")
    private java.lang.String firstName;
    @JoinColumn(name = "last_name")
    private java.lang.String lastName;
    @JoinColumn(name = "phone_number")
    private java.lang.String phoneNumber;
    @JoinColumn(name = "email")
    private java.lang.String email;
    @JoinColumn(name = "password")
    private java.lang.String password;
    @JoinColumn(name = "personal_id")
    private java.lang.String personalId;
    @JoinColumn(name = "birth_date")
    private LocalDate birthDate;
    @Column(length = 64)
    private String photo;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
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

}
