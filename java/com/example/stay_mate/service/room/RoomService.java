package com.example.stay_mate.service.room;

import com.example.stay_mate.model.hotel.Hotel;
import com.example.stay_mate.model.partner.Partner;
import com.example.stay_mate.model.reservation.Reservation;
import com.example.stay_mate.model.room.Room;
import com.example.stay_mate.repository.room.RoomRepository;
import com.example.stay_mate.service.ReservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoomService {
    public final RoomRepository roomRepository;
    public final ReservationService reservationService;

    public RoomService(RoomRepository roomRepository, ReservationService reservationService) {
        this.roomRepository = roomRepository;
        this.reservationService = reservationService;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    public Room getRoomById(Integer id) {
        return roomRepository.findById(id).orElse(null);
    }

    public List<Room> getRoomByPartner(Partner partner) {
        return roomRepository.getRoomByPartner(partner);
    }

    public List<Room> getRoomByHotel(Hotel hotel) {
        return roomRepository.getRoomByHotel(hotel);
    }

    public List<Room> getAllAvailableRooms(LocalDateTime startDate, LocalDateTime endDate, Integer numberOfGuests) {
        List<Reservation> notAvailable = reservationService.getReservationToExactDate(startDate, endDate);
        Set<Integer> wrongRoom = new HashSet<>();
        for (Reservation actual : notAvailable) {
            wrongRoom.add(actual.getRoom().getId());
        }
        return roomRepository.findAllByIdNotInAndRoomCapacityGreaterThanEqual(wrongRoom, numberOfGuests);
    }

    @Transactional
    public void deleteRoomById(Integer id) {
        roomRepository.deleteById(id);
    }

    @Transactional
    public void deleteRoomByPartner(Partner partner) {
        roomRepository.deleteRoomByPartner(partner);
    }

    @Transactional
    public void deleteRoomByHotel(Hotel hotel) {
        roomRepository.deleteRoomByHotel(hotel);
    }
}
