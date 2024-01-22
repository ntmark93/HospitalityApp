package com.example.stay_mate.controller.restaurant;

import com.example.stay_mate.FileUploadUtil;
import com.example.stay_mate.model.restaurant.Restaurant;
import com.example.stay_mate.service.menubook.MenuBookService;
import com.example.stay_mate.service.partner.PartnerService;
import com.example.stay_mate.service.restaurant.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final PartnerService partnerService;
    private final MenuBookService menuBookService;

    public RestaurantController(RestaurantService restaurantService,
                                PartnerService partnerService,
                                MenuBookService menuBookService) {
        this.restaurantService = restaurantService;
        this.partnerService = partnerService;
        this.menuBookService = menuBookService;
    }

    @GetMapping("/all")
    public String getAllRestaurants(Model model) {
        List<Restaurant> all_restaurant = restaurantService.getAllRestaurants();
        model.addAttribute("all_restaurant", all_restaurant);
        return "restaurant-list";
    }

    @GetMapping("/{id}")
    public String getCurrentRestaurant(Model model, @PathVariable("id") Integer restaurantId) {
        model.addAttribute("menu_book", menuBookService.getMenuBookByRestaurant
                (restaurantService.getRestaurantById(restaurantId)));
        model.addAttribute("restaurant", restaurantService.getRestaurantById(restaurantId));
        return "restaurant";
    }

    @GetMapping("/create/{partner-id}")
    public String addRestaurant(Model model, @PathVariable("partner-id") Integer partnerId) {
        model.addAttribute("partnerId", partnerId);
        model.addAttribute("new_restaurant", new Restaurant());
        return "new-restaurant-form";
    }

    @PostMapping("/create/{partner-id}")
    public String addRestaurant(@ModelAttribute("new_restaurant") Restaurant restaurant,
                                @PathVariable("partner-id") Integer partnerId,
                                @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            restaurant.setPhoto(fileName);
            restaurant.setPartner(partnerService.getPartnerById(partnerId));
            restaurantService.saveRestaurant(restaurant);
            String upload = "/images/" + restaurant.getId();
            FileUploadUtil.saveFile(upload, fileName, multipartFile);
        } else {
            if (restaurant.getPhoto().isEmpty()) {
                restaurant.setPhoto(null);
                restaurant.setPartner(partnerService.getPartnerById(partnerId));
                restaurantService.saveRestaurant(restaurant);
            }
        }
        restaurantService.saveRestaurant(restaurant);
        return "redirect:/partner/current";
    }

    @GetMapping("/{id}/update/{partner-id}")
    public String updateRestaurant(Model model,
                                   @PathVariable("id") Integer restaurantId,
                                   @PathVariable("partner-id")Integer partnerId) {
        model.addAttribute(partnerService.getPartnerById(partnerId));
        model.addAttribute("updated_restaurant", restaurantService.getRestaurantById(restaurantId));
        return "restaurant-update";
    }

    @PostMapping("/{id}/update/{partner-id}")
    public String updateRestaurant(@ModelAttribute("updated_restaurant") Restaurant updatedRestaurant,
                                   @PathVariable("id") Integer restaurantId,
                                   @PathVariable("partner-id")Integer partnerId,
                                   @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            updatedRestaurant.setPhoto(fileName);
            updatedRestaurant.setPartner(partnerService.getPartnerById(partnerId));
            restaurantService.saveRestaurant(updatedRestaurant);
            String upload = "/images/" + updatedRestaurant.getId();
            FileUploadUtil.saveFile(upload, fileName, multipartFile);
        } else {
            if (updatedRestaurant.getPhoto().isEmpty()) {
                updatedRestaurant.setPhoto(null);
                updatedRestaurant.setPartner(partnerService.getPartnerById(partnerId));
                restaurantService.saveRestaurant(updatedRestaurant);
            }
        }
        restaurantService.saveRestaurant(updatedRestaurant);
        return "redirect:/partner/current";
    }

    @PostMapping("/{id}/delete")
    public String deleteRestaurants(@PathVariable("id") Integer id) {
        restaurantService.deleteRestaurantById(id);
        return "redirect:/partner/current";
    }

}
