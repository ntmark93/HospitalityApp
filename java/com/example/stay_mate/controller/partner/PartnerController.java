package com.example.stay_mate.controller.partner;

import com.example.stay_mate.FileUploadUtil;
import com.example.stay_mate.model.partner.Partner;
import com.example.stay_mate.service.bar.BarService;
import com.example.stay_mate.service.hotel.FacilitiesService;
import com.example.stay_mate.service.hotel.HotelBarService;
import com.example.stay_mate.service.hotel.HotelRestaurantService;
import com.example.stay_mate.service.hotel.HotelService;
import com.example.stay_mate.service.menubook.MenuBookService;
import com.example.stay_mate.service.partner.PartnerAdminService;
import com.example.stay_mate.service.partner.PartnerService;
import com.example.stay_mate.service.restaurant.RestaurantService;
import com.example.stay_mate.service.room.RoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping(value = "/partner")
public class PartnerController {
    private final PartnerService partnerService;
    private final PartnerAdminService partnerAdminService;
    private final HotelService hotelService;
    private final FacilitiesService facilitiesService;
    private final BarService barService;
    private final RestaurantService restaurantService;
    private final HotelBarService hotelBarService;
    private final HotelRestaurantService hotelRestaurantService;
    private final MenuBookService menuBookService;
    private final RoomService roomService;
    private final PasswordEncoder passwordEncoder;

    public PartnerController(PartnerService partnerService, PartnerAdminService partnerAdminService,
                             HotelService hotelService, FacilitiesService facilitiesService,
                             BarService barService, RestaurantService restaurantService,
                             HotelBarService hotelBarService, HotelRestaurantService hotelRestaurantService,
                             MenuBookService menuBookService, RoomService roomService, PasswordEncoder passwordEncoder) {
        this.partnerService = partnerService;
        this.partnerAdminService = partnerAdminService;
        this.hotelService = hotelService;
        this.facilitiesService = facilitiesService;
        this.barService = barService;
        this.restaurantService = restaurantService;
        this.hotelBarService = hotelBarService;
        this.hotelRestaurantService = hotelRestaurantService;
        this.menuBookService = menuBookService;
        this.roomService = roomService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/all")
    public String getAllPartners(Model model) {
        model.addAttribute("all_partners", partnerService.findAllPartner());
        return "partner-list";
    }

    @GetMapping("/current")
    public String getCurrentPartner(Model model, @AuthenticationPrincipal Partner partner) {
        model.addAttribute("room", roomService.getRoomByPartner(partner));
        model.addAttribute("menu_book", menuBookService.getMenuBookByPartner(partner));
        model.addAttribute("hotel_restaurant", hotelRestaurantService.getHotelRestaurantByPartner(partner));
        model.addAttribute("hotel_bar", hotelBarService.getHotelBarByPartner(partner));
        model.addAttribute("restaurant", restaurantService.getRestaurantByPartner(partner));
        model.addAttribute("bar", barService.getBarByPartner(partner));
        model.addAttribute("facilities", facilitiesService.getFacilitiesByPartner(partner));
        model.addAttribute("hotel", hotelService.getHotelByPartner(partner));
        model.addAttribute("partner_admin", partnerAdminService.getAllPartnerAdminByPartner(partner));
        model.addAttribute("partner", partner);
        return "partner";
    }

    @GetMapping("/create")
    public String addPartner(Model model) {
        model.addAttribute("new_partner", new Partner());
        return "new-partner-form";
    }

    //  @PostMapping("/create")
    //  public String addPartner(@ModelAttribute("partner") Partner partner) {
    //      partnerService.savePartner(partner);
    //      return "redirect:/";
    //  }

    @PostMapping("/{id}/delete")
    public String deletePartner(@PathVariable("id") Integer id, Partner partner) {
        roomService.deleteRoomByPartner(partner);
        menuBookService.deleteMenuBookByPartner(partner);
        hotelRestaurantService.deleteHotelRestaurantByPartner(partner);
        hotelBarService.deleteBarByPartner(partner);
        restaurantService.deleteRestaurantByPartner(partner);
        barService.deleteBarByPartner(partner);
        facilitiesService.deleteFacilitiesByPartner(partner);
        hotelService.deleteHotelByPartner(partner);
        partnerAdminService.deletePartnerAdminByPartner(partner);
        partnerService.deletePartnerById(id);
        return "deleted-account";
    }

    @GetMapping("/reg")
    public String getReg(Model model) {
        model.addAttribute("newPartner", new Partner());
        return "registration";
    }

    @PostMapping("/reg")
    @RolesAllowed(value = "ROLE_PARTNER")
    public String savePartner(
            @ModelAttribute("newPartner") Partner partner,
            @RequestParam("image") MultipartFile multipartFile,
            Model model) throws IOException {
        String email = partner.getEmail();
        if (partnerService.isEmailAlreadyTaken(email)) {
            model.addAttribute("emailTakenMessage", "This email is already taken!");
            return "registration";
        }
        try {
            if (!multipartFile.isEmpty()) {
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                partner.setPhoto(fileName);
                String upload = "/images/" + partner.getId();
                FileUploadUtil.saveFile(upload, fileName, multipartFile);
            } else {
                if (partner.getPhoto().isEmpty()) {
                    partner.setPhoto(null);
                    partner.setPassword(passwordEncoder.encode(partner.getPassword()));
                    partnerService.savePartner(partner);
                }
            }
            partner.setPassword(passwordEncoder.encode(partner.getPassword()));
            System.out.println(partner);
            partnerService.savePartner(partner);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/login";
    }

    @GetMapping("/{id}/update")
    // @PreAuthorize("hasRole('ADMIN')")
    public String updatePartner(@PathVariable("id") Integer id,
                                Model model) {
        model.addAttribute("partner", partnerService.getPartnerById(id));
        return "partner-update";
    }

    @PostMapping("/{id}/update")
    public String updatePartner(@ModelAttribute("partner") Partner partner,
                                @PathVariable("id") Integer id,
                                @RequestParam("uImage") MultipartFile multipartFile) throws IOException {
        try {
            if (!multipartFile.isEmpty()) {
                String updatedFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                partner.setPhoto(updatedFileName);
                String upload = "/images/" + partner.getId();
                FileUploadUtil.saveFile(upload, updatedFileName, multipartFile);
            } else {
                if (partner.getPhoto().isEmpty()) {
                    partner.setPassword(passwordEncoder.encode(partner.getPassword()));
                    partnerService.savePartner(partner);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        partner.setPassword(passwordEncoder.encode(partner.getPassword()));
        partnerService.savePartner(partner);
        return "update-logout";
    }

}
