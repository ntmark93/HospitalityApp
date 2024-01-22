package com.example.stay_mate.controller.hotel;

import com.example.stay_mate.FileUploadUtil;
import com.example.stay_mate.model.hotel.HotelRestaurant;
import com.example.stay_mate.service.hotel.HotelRestaurantService;
import com.example.stay_mate.service.hotel.HotelService;
import com.example.stay_mate.service.menubook.MenuBookService;
import com.example.stay_mate.service.partner.PartnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/hotel-restaurant")
public class HotelRestaurantController {
    private final HotelRestaurantService hotelRestaurantService;
    private final HotelService hotelService;
    private final PartnerService partnerService;
    private final MenuBookService menuBookService;

    public HotelRestaurantController(HotelRestaurantService hotelRestaurantService, HotelService hotelService,
                                     PartnerService partnerService, MenuBookService menuBookService) {
        this.hotelRestaurantService = hotelRestaurantService;
        this.hotelService = hotelService;
        this.partnerService = partnerService;
        this.menuBookService = menuBookService;
    }

    @GetMapping("/all")
    public String getAllHotelRestaurants(Model model) {
        model.addAttribute("all_hotel_restaurant", hotelRestaurantService.getAllHotelRestaurants());
        return "hotel-restaurant-list";
    }

    @GetMapping("/{id}")
    public String getCurrentHotelRestaurant(Model model, @PathVariable("id") Integer hotelRestaurantId) {
        model.addAttribute("menu_book", menuBookService.getMenuBookByHotelRestaurant
                (hotelRestaurantService.getHotelRestaurantById(hotelRestaurantId)));
        model.addAttribute("hotel_restaurant",
                hotelRestaurantService.getHotelRestaurantById(hotelRestaurantId));
        return "hotel-restaurant";
    }

    @GetMapping("/create/{hotel-id}/{partner-id}")
    public String addHotelRestaurant(Model model,
                                     @PathVariable("hotel-id") Integer hotelId,
                                     @PathVariable("partner-id") Integer partnerId) {
        model.addAttribute("partnerId", partnerId);
        model.addAttribute("hotelId", hotelId);
        model.addAttribute("new_hotel_restaurant", new HotelRestaurant());
        return "new-hotel-restaurant-form";
    }

    @PostMapping("/create/{hotel-id}/{partner-id}")
    public String addHotelRestaurant(@ModelAttribute("new_hotel_restaurant") HotelRestaurant hotelRestaurant,
                                     @PathVariable("hotel-id") Integer hotelId,
                                     @PathVariable("partner-id") Integer partnerId,
                                     @RequestParam("hrImage")MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            hotelRestaurant.setPhoto(fileName);
            hotelRestaurant.setHotel(hotelService.getHotelById(hotelId));
            hotelRestaurant.setPartner(partnerService.getPartnerById(partnerId));
            String upload = "/images/" + hotelRestaurant.getId();
            FileUploadUtil.saveFile(upload,fileName,multipartFile);
        }
        else {
            if (hotelRestaurant.getPhoto().isEmpty()){
                hotelRestaurant.setPhoto(null);
                hotelRestaurant.setHotel(hotelService.getHotelById(hotelId));
                hotelRestaurant.setPartner(partnerService.getPartnerById(partnerId));
                hotelRestaurantService.saveHotelRestaurant(hotelRestaurant);
            }

        }
        hotelRestaurantService.saveHotelRestaurant(hotelRestaurant);
        return "redirect:/hotels/" + hotelId + "/" + partnerId;
    }

    @GetMapping("{id}/update/{hotel-id}/{partner-id}")
    public String updateHotelRestaurant(@PathVariable("id") Integer hotelRestaurantId, Model model,
                                        @PathVariable("hotel-id")Integer hotelId,
                                        @PathVariable("partner-id")Integer partnerId) {
        model.addAttribute("hotel", hotelService.getHotelById(hotelId));
        model.addAttribute("partner", partnerService.getPartnerById(partnerId));
        model.addAttribute("updated_hotel_restaurant",
                hotelRestaurantService.getHotelRestaurantById(hotelRestaurantId));
        return "hotel-restaurant-update";
    }

    @PostMapping("{id}/update/{hotel-id}/{partner-id}")
    public String updateHotelRestaurant(@ModelAttribute("updated_hotel_restaurant") HotelRestaurant updatedHotelRestaurant,
                                        @PathVariable("id") Integer hotelRestaurantId,
                                        @PathVariable("hotel-id")Integer hotelId,
                                        @PathVariable("partner-id")Integer partnerId,
                                        @RequestParam("image")MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            updatedHotelRestaurant.setPhoto(fileName);
            updatedHotelRestaurant.setHotel(hotelService.getHotelById(updatedHotelRestaurant.getHotel().getId()));
            updatedHotelRestaurant.setPartner(partnerService.getPartnerById(updatedHotelRestaurant.getPartner().getId()));
            String upload = "/images/" + updatedHotelRestaurant.getId();
            FileUploadUtil.saveFile(upload,fileName,multipartFile);
        }
        else {
            if (updatedHotelRestaurant.getPhoto().isEmpty()){
                updatedHotelRestaurant.setPhoto(null);
                updatedHotelRestaurant.setHotel(hotelService.getHotelById(hotelId));
                updatedHotelRestaurant.setPartner(partnerService.getPartnerById(partnerId));
                hotelRestaurantService.saveHotelRestaurant(updatedHotelRestaurant);
            }

        }
        hotelRestaurantService.saveHotelRestaurant(updatedHotelRestaurant);
        return "redirect:/hotel-restaurant/" + hotelRestaurantId;
    }

    @PostMapping("/{id}/delete")
    public String deleteHotelRestaurant(@PathVariable("id") Integer hotelRestaurantId) {
        menuBookService.deleteMenuBookByHotelRestaurant
                (hotelRestaurantService.getHotelRestaurantById(hotelRestaurantId));
        hotelRestaurantService.deleteHotelRestaurantById(hotelRestaurantId);
        return "redirect:/partner/current";
    }
}
