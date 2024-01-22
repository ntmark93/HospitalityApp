package com.example.stay_mate.controller.hotel;

import com.example.stay_mate.FileUploadUtil;
import com.example.stay_mate.model.hotel.HotelBar;
import com.example.stay_mate.service.hotel.HotelBarService;
import com.example.stay_mate.service.hotel.HotelService;
import com.example.stay_mate.service.menubook.MenuBookService;
import com.example.stay_mate.service.partner.PartnerService;
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
@RequestMapping("/hotel-bar")
public class HotelBarController {
    private final HotelBarService hotelBarService;
    private final HotelService hotelService;
    private final PartnerService partnerService;
    private final MenuBookService menuBookService;

    public HotelBarController(HotelBarService hotelBarService, HotelService hotelService,
                              PartnerService partnerService, MenuBookService menuBookService) {
        this.hotelBarService = hotelBarService;
        this.hotelService = hotelService;
        this.partnerService = partnerService;
        this.menuBookService = menuBookService;
    }

    @GetMapping("/all")
    public String getAllHotelBars(Model model) {
        model.addAttribute("all_hotel_bar", hotelBarService.getAllHotelBars());
        return "hotel-bar-list";
    }

    @GetMapping("/{id}")
    public String getCurrentHotelBar(Model model, @PathVariable("id") Integer hotelBarId) {
        model.addAttribute("menu_book",
                menuBookService.getMenuBookByHotelBar(hotelBarService.getHotelBarById(hotelBarId)));
        model.addAttribute("hotel_bar", hotelBarService.getHotelBarById(hotelBarId));
        model.addAttribute("image", "/uploads/" + hotelBarId + "-hotelBar-" + hotelBarService.getHotelBarById(hotelBarId).getName());
        return "hotel-bar";
    }

    @GetMapping("/create/{hotel-id}/{partner-id}")
    public String addHotelBar(Model model, @PathVariable("hotel-id") Integer hotelId, @PathVariable("partner-id") Integer partnerId) {
        model.addAttribute("partnerId", partnerId);
        model.addAttribute("hotelId", hotelId);
        model.addAttribute("new_hotel_bar", new HotelBar());
        return "new-hotel-bar-form";
    }

    @PostMapping("/create/{hotel-id}/{partner-id}")
    public String addHotelBar(@ModelAttribute("new_hotel_bar") HotelBar hotelBar,
                              @PathVariable("hotel-id") Integer hotelId,
                              @PathVariable("partner-id") Integer partnerId,
                              @RequestParam("hbImage") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            hotelBar.setPhoto(fileName);
            hotelBar.setHotel(hotelService.getHotelById(hotelId));
            hotelBar.setPartner(partnerService.getPartnerById(partnerId));
            String upload = "/images/" + hotelBar.getId();
            FileUploadUtil.saveFile(upload,fileName,multipartFile);
        }else {
            if (hotelBar.getPhoto().isEmpty()){
                hotelBar.setPhoto(null);
                hotelBar.setHotel(hotelService.getHotelById(hotelId));
                hotelBar.setPartner(partnerService.getPartnerById(partnerId));
                hotelBarService.saveHotelBar(hotelBar);
            }
        }
        hotelBarService.saveHotelBar(hotelBar);
        return "redirect:/hotels/" + hotelId + '/' + partnerId;
    }

    @GetMapping("/{id}/update/{hotel-id}/{partner-id}")
    public String updateHotelBar(@PathVariable("id") Integer id, Model model,
                                 @PathVariable("partner-id")Integer partnerId,
                                 @PathVariable("hotel-id")Integer hotelId) {
        model.addAttribute("partner",partnerService.getPartnerById(partnerId));
        model.addAttribute("hotel", hotelService.getHotelById(hotelId));
        model.addAttribute("updated_hotel_bar", hotelBarService.getHotelBarById(id));
        return "hotel_bar_update";
    }

    @PostMapping("/{id}/update/{hotel-id}/{partner-id}")
    public String updateHotelBar(@ModelAttribute("updated_hotel_bar") HotelBar hotelBar,
                                 @PathVariable("id") Integer hotelBarId,
                                 @PathVariable("hotel-id") Integer hotelId,
                                 @PathVariable("partner-id") Integer partnerId,
                                 @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            hotelBar.setPhoto(fileName);
            hotelBar.setHotel(hotelService.getHotelById(hotelBar.getHotel().getId()));
            hotelBar.setPartner(partnerService.getPartnerById(hotelBar.getPartner().getId()));
            String upload = "/images/" + hotelBar.getId();
            FileUploadUtil.saveFile(upload,fileName,multipartFile);
        }else {
            if (hotelBar.getPhoto().isEmpty()){
                hotelBar.setPhoto(null);
                hotelBar.setHotel(hotelService.getHotelById(hotelBar.getHotel().getId()));
                hotelBar.setPartner(partnerService.getPartnerById(hotelBar.getPartner().getId()));
                hotelBarService.saveHotelBar(hotelBar);
            }
        }
        hotelBarService.saveHotelBar(hotelBar);
        return "redirect:/hotel-bar/" + hotelBarId;
    }

    @PostMapping("/{id}/delete")
    public String deleteHotelBar(@PathVariable("id") Integer hotelBarId, HotelBar hotelBar) {
        menuBookService.deleteMenuBookByHotelBar(hotelBarService.getHotelBarById(hotelBarId));
        hotelBarService.deleteHotelBarById(hotelBarId);
        return "redirect:/partner/current";
    }
}

