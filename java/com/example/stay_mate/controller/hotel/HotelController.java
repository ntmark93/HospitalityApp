package com.example.stay_mate.controller.hotel;

import com.example.stay_mate.FileUploadUtil;
import com.example.stay_mate.model.hotel.Hotel;
import com.example.stay_mate.service.room.RoomService;
import com.example.stay_mate.service.hotel.FacilitiesService;
import com.example.stay_mate.service.hotel.HotelBarService;
import com.example.stay_mate.service.hotel.HotelRestaurantService;
import com.example.stay_mate.service.hotel.HotelService;
import com.example.stay_mate.service.menubook.MenuBookService;
import com.example.stay_mate.service.partner.PartnerService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
@RequestMapping("/hotels")
public class HotelController {
    private final HotelService hotelService;
    private final PartnerService partnerService;
    private final FacilitiesService facilitiesService;
    private final HotelBarService hotelBarService;
    private final HotelRestaurantService hotelRestaurantService;
    private final MenuBookService menuBookService;
    private final RoomService roomService;

    public HotelController(HotelService hotelService, PartnerService partnerService, FacilitiesService facilitiesService, HotelBarService hotelBarService, HotelRestaurantService hotelRestaurantService, MenuBookService menuBookService, RoomService roomService) {
        this.hotelService = hotelService;
        this.partnerService = partnerService;
        this.facilitiesService = facilitiesService;
        this.hotelBarService = hotelBarService;
        this.hotelRestaurantService = hotelRestaurantService;
        this.menuBookService = menuBookService;
        this.roomService = roomService;

    }


    @GetMapping("/all")
    public String getAllHotels(Model model) {
        model.addAttribute("hotels", hotelService.findAllHotel());
        return "hotel-list";
    }

    @GetMapping("/{id}/{partner-id}")
    public String getCurrentHotel(Model model, @PathVariable("id") Integer hoteLid, @PathVariable("partner-id") Integer partnerId) {
        model.addAttribute("room",roomService.getRoomByHotel(hotelService.getHotelById(hoteLid)));
        model.addAttribute("menu_book", menuBookService.getMenuBookByHotel(hotelService.getHotelById(hoteLid)));
        model.addAttribute("hotel_restaurant", hotelRestaurantService.getHotelRestaurantByHotel(hotelService.getHotelById(hoteLid)));
        model.addAttribute("hotel_bar", hotelBarService.getHotelBarByHotel(hotelService.getHotelById(hoteLid)));
        model.addAttribute("partner", partnerService.getPartnerById(partnerId));
        model.addAttribute("facilities", facilitiesService.getFacilitiesByHotel(hotelService.getHotelById(hoteLid)));
        model.addAttribute("hotel", hotelService.getHotelById(hoteLid));
        model.addAttribute("image", "/uploads/" + partnerId + "-" + "hotel" + "-" + hotelService.getHotelById(hoteLid).getName());

        return "hotel";
    }

    @GetMapping("/create/{partner-id}")
    public String addHotel(Model model, @PathVariable("partner-id") Integer partnerId) {
        model.addAttribute("partnerId", partnerId);
        model.addAttribute("new_hotel", new Hotel());
        return "new-hotel-form";
    }

    @PostMapping("/create/{partner-id}")
    public String addHotel(@ModelAttribute("new_hotel") Hotel hotel,
                           @PathVariable("partner-id") Integer partnerId,
                           @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            hotel.setPhoto(fileName);
            hotel.setPartner(partnerService.getPartnerById(partnerId));
            hotelService.saveHotel(hotel);
            String upload = "/images/" + hotel.getId();
            FileUploadUtil.saveFile(upload, fileName, multipartFile);

        }else {
            if (hotel.getPhoto().isEmpty()){
                hotel.setPhoto(null);
                hotel.setPartner(partnerService.getPartnerById(partnerId));
                hotelService.saveHotel(hotel);
            }
        }
        hotelService.saveHotel(hotel);
        return "redirect:/partner/current";

    }

    @GetMapping("/{id}/update")
    // @PreAuthorize("hasRole('ADMIN')")
    public String updateHotel(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("update_hotel", hotelService.getHotelById(id));
        return "hotel-update";
    }

    @PostMapping("/{id}/update")
    public String updateHotel(@ModelAttribute("update_hotel") Hotel updated, @PathVariable("id") Integer id) {
        hotelService.saveHotel(updated);
        return "redirect:/partner/current";
    }

    @PostMapping("/delete/{hotel-id}")
    public String deleteHotel(@PathVariable("hotel-id") Integer hotelId) {
        roomService.deleteRoomByHotel(hotelService.getHotelById(hotelId));
        menuBookService.deleteMenuBookByHotel(hotelService.getHotelById(hotelId));
        hotelRestaurantService.deleteHotelRestaurantByHotel(hotelService.getHotelById(hotelId));
        hotelBarService.deleteHotelBarByHotel(hotelService.getHotelById(hotelId));
        facilitiesService.deleteFacilitiesByHotel(hotelService.getHotelById(hotelId));
        hotelService.deleteHotelById(hotelId);
        return "redirect:/partner/current";
    }
}
