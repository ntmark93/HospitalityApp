package com.example.stay_mate.controller.user;

import com.example.stay_mate.FileUploadUtil;
import com.example.stay_mate.model.reservation.Reservation;
import com.example.stay_mate.model.user.User;
import com.example.stay_mate.service.ReservationService;
import com.example.stay_mate.service.user.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final ReservationService reservationService;

    public UserController(PasswordEncoder passwordEncoder,
                          UserService userService,
                          ReservationService reservationService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @GetMapping("/all") // ez a funkció várhatóan csak nekünk kellhet
    public String getAllPartners(Model model) {
        model.addAttribute("all_users", userService.getAllUsers());
        return "all-users";
    }

    @GetMapping("/current")
    public String getCurrentUser(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("reservation", reservationService.getReservationByUser(user));
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/{id}/reservations")
    public String getReservations(Model model, @PathVariable("id") Integer id) {
        User user = userService.getUserById(id);
        List<Reservation> reservation = reservationService.getReservationByUser(user);
        List<Integer> totals = new ArrayList<>();
        for (Reservation actual : reservation) {
            if (actual != null && user.equals(actual.getUser()) && actual.getRoom() != null && actual.getRoom().getId() != null) {
                double reservationTotal = actual.getRoom().getPricePerDay() * reservationService.getTotalPrice(
                        actual.getStartDate(), actual.getEndDate());
                totals.add((int)reservationTotal);
            }
        }
        model.addAttribute("reservations", reservation);
        model.addAttribute("totals", totals);
        model.addAttribute("user", userService.getUserById(id));
        if (reservation.isEmpty()) {
            return "redirect:/reservation/" + user.getId() + "/all";
        }
        return "logged-in-room";
    }

    @GetMapping("/create")
    public String addUser(Model model) {
        model.addAttribute("new_user", new User());
        return "new-user-form";
    }

    //   @PostMapping("/create")
    //   public String addUser(@ModelAttribute("new_user") User user){
    //       userService.saveUser(user);
    //       return "redirect:/";
    //   }

    @PostMapping("{id}/delete")
    public String deleteUser(@PathVariable("id") Integer userId, User user) {
        reservationService.deleteReservationByUser(user);
        userService.deleteUser(user);
        return "redirect:/";
    }

    @GetMapping("/reg")
    public String getReg(Model model) {
        model.addAttribute("newUser", new User());
        return "user-registration";
    }



    @PostMapping("/reg")
    public String saveUser(
            @ModelAttribute("newUser") User user,
            @RequestParam("image") MultipartFile multipartFile, Model model) throws IOException {
        try {
            if (!multipartFile.isEmpty()) {
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                user.setPhoto(fileName);
                String upload = "/images/" + user.getId();
                FileUploadUtil.saveFile(upload, fileName, multipartFile);
            } else {
                if (user.getPhoto().isEmpty()) {
                    user.setPhoto(null);
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    userService.saveUser(user);
                }
            }

            String email = user.getEmail();
            if (userService.isEmailAlreadyTaken(email)) {
                model.addAttribute("emailTakenMessage", "This email is already taken!");
                return "user-registration";
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/login";
    }



    @GetMapping("/{id}/update")
    public String updatePartner(@PathVariable("id") Integer userId, Model model) {
        model.addAttribute("user", userService.getUserById(userId));
        return "user-update";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@ModelAttribute("user") User user,
                             @PathVariable("id") Integer id,
                             @RequestParam("usImage") MultipartFile multipartFile) throws IOException {
        try {
            if (!multipartFile.isEmpty()) {
                String updatedFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                user.setPhoto(updatedFileName);
                String upload = "/images/" + user.getId();
                FileUploadUtil.saveFile(upload, updatedFileName, multipartFile);
            } else {
                if (user.getPhoto().isEmpty()) {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    userService.saveUser(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "update-logout";
    }
}
