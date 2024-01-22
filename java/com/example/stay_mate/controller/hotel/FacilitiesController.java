package com.example.stay_mate.controller.hotel;

import com.example.stay_mate.model.hotel.Facilities;
import com.example.stay_mate.model.hotel.Hotel;
import com.example.stay_mate.service.hotel.FacilitiesService;
import com.example.stay_mate.service.hotel.HotelService;
import com.example.stay_mate.service.partner.PartnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/services")
public class FacilitiesController {

    private final FacilitiesService facilitiesService;
    private final HotelService hotelService;
    private final PartnerService partnerService;


    public FacilitiesController(FacilitiesService facilitiesService, HotelService hotelService, PartnerService partnerService) {
        this.facilitiesService = facilitiesService;
        this.hotelService = hotelService;
        this.partnerService = partnerService;
    }

    @GetMapping("/all")
    public String getAllFacilities(Model model) {
        model.addAttribute("all_services", facilitiesService.findAllFacilities());
        return "facilities-list";
    }

    @GetMapping("/{id}")
    public String getCurrentFacility(Model model, @PathVariable("id") Integer facilityId) {
        model.addAttribute("facility", facilitiesService.getFacilitiesById(facilityId));
        return "facility";
    }

    @GetMapping("/create/{hotel-id}/{partner-id}")
    public String addFacilities(Model model, @PathVariable("hotel-id") Integer hotelId,@PathVariable("partner-id") Integer partnerId) {
        model.addAttribute("partnerId", partnerId);
        model.addAttribute("hotelId", hotelId);
        model.addAttribute("new_facilities", new Facilities());
        return "new-facilities-form";
    }

    @PostMapping("/create/{hotel-id}/{partner-id}")
    public String addFacilities(@ModelAttribute("new_facilities") Facilities facilities, @PathVariable("hotel-id") Integer hotelId,
                                @PathVariable("partner-id") Integer partnerId) {
        facilities.setPartner(partnerService.getPartnerById(partnerId));
        facilities.setHotel(hotelService.getHotelById(hotelId));
        facilitiesService.saveFacilities(facilities);
        return "redirect:/hotels/" + hotelId + '/'+ partnerId;
    }

    @GetMapping("/update/{id}")
    public String updateFacilities(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("updated_facilities", facilitiesService.getFacilitiesById(id));
        return "facilities-update";
    }

    @PostMapping("/update/{id}")
    public String updateFacilities(@ModelAttribute("updated_facilities") Facilities updatedFacilities, @PathVariable("id") Integer id) {
        facilitiesService.saveFacilities(updatedFacilities);
        return "redirect:/services/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteFacilities(@PathVariable("id") Integer id, Hotel hotel) {
        facilitiesService.deleteFacilitiesById(id);
        return "redirect:/partner/current";
    }

}
