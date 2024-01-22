package com.example.stay_mate.controller.reservation;

import com.example.stay_mate.model.reservation.Reservation;
import com.example.stay_mate.service.ReservationService;
import com.example.stay_mate.service.bar.BarService;
import com.example.stay_mate.service.hotel.HotelBarService;
import com.example.stay_mate.service.hotel.HotelRestaurantService;
import com.example.stay_mate.service.hotel.HotelService;
import com.example.stay_mate.service.restaurant.RestaurantService;
import com.example.stay_mate.service.room.RoomService;
import com.example.stay_mate.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    private final UserService userService;
    private final RestaurantService restaurantService;
    private final BarService barService;
    private final HotelService hotelService;
    private final HotelRestaurantService hotelRestaurantService;
    private final HotelBarService hotelBarService;
    private final RoomService roomService;

    public ReservationController(ReservationService reservationService, UserService userService,
                                 RestaurantService restaurantService, BarService barService,
                                 HotelService hotelService, HotelRestaurantService hotelRestaurantService,
                                 HotelBarService hotelBarService, RoomService roomService) {
        this.reservationService = reservationService;
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.barService = barService;
        this.hotelService = hotelService;
        this.hotelRestaurantService = hotelRestaurantService;
        this.hotelBarService = hotelBarService;
        this.roomService = roomService;
    }

//    @GetMapping("/all")
//    public String getAllReservations() {
//        reservationService.getAllReservation();
//        return "all_reservations";
//    }
    @GetMapping("/{user-id}")
    public String getAllRoom(Model model,
                             @PathVariable("user-id") Integer userId) {
        model.addAttribute("room", roomService.getAllRooms());
        model.addAttribute("userId", userService.getUserById(userId));
        return null;
    }
    @GetMapping("/{user-id}/all")
    public String getRoom(Model model,
                             @PathVariable("user-id") Integer userId) {
        model.addAttribute("room", roomService.getAllRooms());
        model.addAttribute("restaurant", restaurantService.getAllRestaurants());
        model.addAttribute("bar", barService.getAllBar());
        model.addAttribute("hotelRestaurant", hotelRestaurantService.getAllHotelRestaurants());
        model.addAttribute("hotelBar", hotelBarService.getAllHotelBars());
        model.addAttribute("userId", userService.getUserById(userId));
        return "offer";
    }
    @GetMapping("/{user-id}/create-room/{room-id}")
    public String createRoomReservation(Model model,
                                        @PathVariable("user-id") Integer userId,
                                        @PathVariable("room-id") Integer roomId) {
        model.addAttribute("userId", userService.getUserById(userId));
        model.addAttribute("roomId", roomService.getRoomById(roomId));
        model.addAttribute("new_reservation", new Reservation());
        return "new-room-reservation";
    }

    @PostMapping("/{user-id}/create-room/{room-id}")
    public String createRoomReservation(@ModelAttribute("new_reservation") @Validated Reservation newReservation,
                                        @PathVariable("user-id") Integer userId,
                                        @PathVariable("room-id") Integer roomId) {
        newReservation.setUser(userService.getUserById(userId));
        newReservation.setRoom(roomService.getRoomById(roomId));
        reservationService.saveReservation(newReservation);
        return "redirect:/reservation/thank-you";
    }
    // TODO kitalálni hogyan legyen a navigáció, aztán az árat visszaadni majd a szűrést dátumra és utána főre

    @GetMapping("/{user-id}/create-restaurant/{restaurant-id}")
    public String createRestaurantReservation(Model model,
                                        @PathVariable("user-id") Integer userId,
                                        @PathVariable("restaurant-id") Integer restaurantId) {
        model.addAttribute("userId", userService.getUserById(userId));
        model.addAttribute("restaurantId", restaurantService.getRestaurantById(restaurantId));
        model.addAttribute("new_reservation", new Reservation());
        return "new-restaurant-reservation";
    }

    @PostMapping("/{user-id}/create-restaurant/{restaurant-id}")
    public String createRestaurantReservation(@ModelAttribute("new_reservation") @Validated Reservation newReservation,
                                        @PathVariable("user-id") Integer userId,
                                        @PathVariable("restaurant-id") Integer restaurantId) {
        newReservation.setUser(userService.getUserById(userId));
        newReservation.setRestaurant(restaurantService.getRestaurantById(restaurantId));
        reservationService.saveReservation(newReservation);
        return "redirect:/reservation/thank-you";
    }
    @GetMapping("/{user-id}/create-bar/{bar-id}")
    public String createBarReservation(Model model,
                                              @PathVariable("user-id") Integer userId,
                                              @PathVariable("bar-id") Integer barId) {
        model.addAttribute("userId", userService.getUserById(userId));
        model.addAttribute("barId", barService.getBarById(barId));
        model.addAttribute("new_reservation", new Reservation());
        return "new-bar-reservation";
    }

    @PostMapping("/{user-id}/create-bar/{bar-id}")
    public String createBarReservation(@ModelAttribute("new_reservation") @Validated Reservation newReservation,
                                              @PathVariable("user-id") Integer userId,
                                              @PathVariable("bar-id") Integer barId) {
        newReservation.setUser(userService.getUserById(userId));
        newReservation.setBar(barService.getBarById(barId));
        reservationService.saveReservation(newReservation);
        return "redirect:/reservation/thank-you";
    }
    @GetMapping("/{user-id}/create-hotel-restaurant/{hotel-restaurant-id}")
    public String createHotelRestaurantReservation(Model model,
                                              @PathVariable("user-id") Integer userId,
                                              @PathVariable("hotel-restaurant-id") Integer hotelRestaurantId) {
        model.addAttribute("userId", userService.getUserById(userId));
        model.addAttribute("hotelRestaurantId", hotelRestaurantService.getHotelRestaurantById(hotelRestaurantId));
        model.addAttribute("new_reservation", new Reservation());
        return "new-hotel-restaurant-reservation";
    }

    @PostMapping("/{user-id}/create-hotel-restaurant/{hotel-restaurant-id}")
    public String createHotelRestaurantReservation(@ModelAttribute("new_reservation") @Validated Reservation newReservation,
                                              @PathVariable("user-id") Integer userId,
                                              @PathVariable("hotel-restaurant-id") Integer hotelRestaurantId) {
        newReservation.setUser(userService.getUserById(userId));
        newReservation.setHotelRestaurant(hotelRestaurantService.getHotelRestaurantById(hotelRestaurantId));
        reservationService.saveReservation(newReservation);
        return "redirect:/reservation/thank-you";
    }

    @GetMapping("/{user-id}/create-hotel-bar/{hotel-bar-id}")
    public String createHotelBarReservation(Model model,
                                                   @PathVariable("user-id") Integer userId,
                                                   @PathVariable("hotel-bar-id") Integer hotelBarId) {
        model.addAttribute("userId", userService.getUserById(userId));
        model.addAttribute("hotelBarId", hotelBarService.getHotelBarById(hotelBarId));
        model.addAttribute("new_reservation", new Reservation());
        return "new-hotel-bar-reservation";
    }

    @PostMapping("/{user-id}/create-hotel-bar/{hotel-bar-id}")
    public String createHotelBarReservation(@ModelAttribute("new_reservation") @Validated Reservation newReservation,
                                                   @PathVariable("user-id") Integer userId,
                                                   @PathVariable("hotel-bar-id") Integer hotelBarId) {
        newReservation.setUser(userService.getUserById(userId));
        newReservation.setHotelBar(hotelBarService.getHotelBarById(hotelBarId));
        reservationService.saveReservation(newReservation);
        return "redirect:/reservation/thank-you";
    }
    @GetMapping("/thank-you")
    public String showThankYouPage() {
        return "thankyou";
    }
// ez user nelkul tolti be a home pagen
    @GetMapping("/all")
    public String getRoom(Model model) {
        model.addAttribute("room", roomService.getAllRooms());
        model.addAttribute("restaurant", restaurantService.getAllRestaurants());
        model.addAttribute("bar", barService.getAllBar());
        model.addAttribute("hotelRestaurant", hotelRestaurantService.getAllHotelRestaurants());
        model.addAttribute("hotelBar", hotelBarService.getAllHotelBars());
        return "all-reservation-type";
    }
}
