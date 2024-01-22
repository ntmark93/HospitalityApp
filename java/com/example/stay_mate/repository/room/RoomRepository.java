package com.example.stay_mate.repository.room;

import com.example.stay_mate.model.hotel.Hotel;
import com.example.stay_mate.model.partner.Partner;
import com.example.stay_mate.model.reservation.Reservation;
import com.example.stay_mate.model.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    void deleteRoomByPartner(Partner partner);
    void deleteRoomByHotel(Hotel hotel);

    List<Room> getRoomByPartner(Partner partner);
    List<Room> getRoomByHotel(Hotel hotel);
    List<Room> findAllByIdNotInAndRoomCapacityGreaterThanEqual(Collection<Integer> startDate, Integer numberOfGuests);
}
