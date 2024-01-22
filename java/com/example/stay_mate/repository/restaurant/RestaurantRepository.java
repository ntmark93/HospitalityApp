package com.example.stay_mate.repository.restaurant;

import com.example.stay_mate.model.partner.Partner;
import com.example.stay_mate.model.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    List<Restaurant> getRestaurantByPartner(Partner partner);

    void deleteRestaurantByPartner(Partner partner);
}
