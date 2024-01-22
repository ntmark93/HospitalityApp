package com.example.stay_mate.controller.menubook;

import com.example.stay_mate.FileUploadUtil;
import com.example.stay_mate.model.menubook.MenuBook;
import com.example.stay_mate.service.bar.BarService;
import com.example.stay_mate.service.hotel.HotelBarService;
import com.example.stay_mate.service.hotel.HotelRestaurantService;
import com.example.stay_mate.service.hotel.HotelService;
import com.example.stay_mate.service.menubook.MenuBookService;
import com.example.stay_mate.service.partner.PartnerService;
import com.example.stay_mate.service.restaurant.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/menu")
public class MenuBookController {
    private final MenuBookService menuBookService;
    private final PartnerService partnerService;
    private final RestaurantService restaurantService;
    private final BarService barService;
    private final HotelService hotelService;
    private final HotelRestaurantService hotelRestaurantService;
    private final HotelBarService hotelBarService;

    public MenuBookController(MenuBookService menuBookService, PartnerService partnerService,
                              RestaurantService restaurantService, BarService barService,
                              HotelService hotelService, HotelRestaurantService hotelRestaurantService,
                              HotelBarService hotelBarService) {
        this.menuBookService = menuBookService;
        this.partnerService = partnerService;
        this.restaurantService = restaurantService;
        this.barService = barService;
        this.hotelService = hotelService;
        this.hotelRestaurantService = hotelRestaurantService;
        this.hotelBarService = hotelBarService;
    }

    @GetMapping("/all")
    public String getAllMenuBook(Model model) {
        model.addAttribute("all_menu", menuBookService.getAllMenuBook());
        return "all-menu";
    }

    @GetMapping("/restaurant/{restaurant-menu-id}")
    public String getRestaurantMenuBookById(Model model, @PathVariable("restaurant-menu-id") Integer restaurantMenuId) {
        model.addAttribute("restaurant_menu_book", menuBookService.getMenuBookById(restaurantMenuId));
        return "restaurant-menu";
    }

    @GetMapping("/bar/{bar-menu-id}")
    public String getBarMenuBookById(Model model, @PathVariable("bar-menu-id") Integer barMenuId) {
        model.addAttribute("bar_menu_book", menuBookService.getMenuBookById(barMenuId));
        return "bar-menu";
    }

    @GetMapping("/hotel-restaurant/{hotel_restaurant-menu-id}")
    public String getHotelRestaurantMenuBookById(Model model, @PathVariable("hotel_restaurant-menu-id") Integer hotelRestaurantMenuId) {
        model.addAttribute("hotel_restaurant_menu_book", menuBookService.getMenuBookById(hotelRestaurantMenuId));
        return "hotel_restaurant-menu";
    }

    @GetMapping("/hotel-bar/{hotel_bar-id}")
    public String getHotelBarMenuBookById(Model model, @PathVariable("hotel_bar-id") Integer hotelBarMenuId) {
        model.addAttribute("hotel_bar_menu_book", menuBookService.getMenuBookById(hotelBarMenuId));
        return "hotel_bar-menu";
    }

    @GetMapping("/create-restaurant/{restaurant-id}/{partner-id}")
    public String createRestaurantMenu(Model model,
                                       @PathVariable("restaurant-id") Integer restaurantId,
                                       @PathVariable("partner-id") Integer partnerId) {
        model.addAttribute("restaurantId", restaurantId);
        model.addAttribute("partnerId", partnerId);
        model.addAttribute("restaurant_new_menu", new MenuBook());
        return "new-restaurant_menu-form";
    }

    @PostMapping("/create-restaurant/{restaurant-id}/{partner-id}")
    public String createRestaurantMenu(@ModelAttribute("restaurant_new_menu") MenuBook newMenuBook,
                                       @PathVariable("restaurant-id") Integer restaurantId,
                                       @PathVariable("partner-id") Integer partnerId,
                                       @RequestParam("rImage") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            newMenuBook.setPhoto(fileName);
            newMenuBook.setRestaurant(restaurantService.getRestaurantById(restaurantId));
            newMenuBook.setPartner(partnerService.getPartnerById(partnerId));
            String upload = "/images/" + newMenuBook.getId();
            FileUploadUtil.saveFile(upload, fileName, multipartFile);
        } else {
            if (newMenuBook.getPhoto().isEmpty()) {
                newMenuBook.setPhoto(null);
                newMenuBook.setRestaurant(restaurantService.getRestaurantById(restaurantId));
                newMenuBook.setPartner(partnerService.getPartnerById(partnerId));
                menuBookService.saveMenuBook(newMenuBook);
            }
        }
        menuBookService.saveMenuBook(newMenuBook);
        return "redirect:/restaurants/" + restaurantId;
    }

    @GetMapping("/create-bar/{bar-id}/{partner-id}")
    public String createBarMenu(Model model,
                                @PathVariable("bar-id") Integer barId,
                                @PathVariable("partner-id") Integer partnerId) {
        model.addAttribute("barId", barId);
        model.addAttribute("partnerId", partnerId);
        model.addAttribute("bar_new_menu", new MenuBook());
        return "new-bar_menu-form";
    }

    @PostMapping("/create-bar/{bar-id}/{partner-id}")
    public String createBarMenu(@ModelAttribute("bar_new_menu") MenuBook newMenuBook,
                                @PathVariable("bar-id") Integer barId,
                                @PathVariable("partner-id") Integer partnerId,
                                @RequestParam("bImage") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            newMenuBook.setPhoto(fileName);
            newMenuBook.setBar(barService.getBarById(barId));
            newMenuBook.setPartner(partnerService.getPartnerById(partnerId));
            String upload = "/images/" + newMenuBook.getId();
            FileUploadUtil.saveFile(upload, fileName, multipartFile);
        } else {
            if (newMenuBook.getPhoto().isEmpty()) {
                newMenuBook.setPhoto(null);
                newMenuBook.setBar(barService.getBarById(barId));
                newMenuBook.setPartner(partnerService.getPartnerById(partnerId));
                menuBookService.saveMenuBook(newMenuBook);
            }
        }
        menuBookService.saveMenuBook(newMenuBook);
        return "redirect:/bars/" + barId;
    }

    @GetMapping("/create-hotel-restaurant/{hotel-restaurant-id}/{hotel-id}/{partner-id}")
    public String createHotelRestaurantMenu(Model model,
                                            @PathVariable("hotel-restaurant-id") Integer hotelRestaurantId,
                                            @PathVariable("hotel-id") Integer hotelId,
                                            @PathVariable("partner-id") Integer partnerId) {
        model.addAttribute("hotelRestaurantId", hotelRestaurantId);
        model.addAttribute("hotelId", hotelId);
        model.addAttribute("partnerId", partnerId);
        model.addAttribute("hotel_restaurant_new_menu", new MenuBook());
        return "new-hotel-restaurant_menu-form";
    }

    @PostMapping("/create-hotel-restaurant/{hotel-restaurant-id}/{hotel-id}/{partner-id}")
    public String createHotelRestaurantMenu(@ModelAttribute("hotel_restaurant-new_menu") MenuBook newMenuBook,
                                            @PathVariable("hotel-restaurant-id") Integer hotelRestaurantId,
                                            @PathVariable("hotel-id") Integer hotelId,
                                            @PathVariable("partner-id") Integer partnerId,
                                            @RequestParam("hrImage") MultipartFile multipartFile) throws IOException {
        try {
            if (!multipartFile.isEmpty()) {
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                newMenuBook.setPhoto(fileName);
                newMenuBook.setHotelRestaurant(hotelRestaurantService.getHotelRestaurantById(hotelRestaurantId));
                newMenuBook.setHotel(hotelService.getHotelById(hotelId));
                newMenuBook.setPartner(partnerService.getPartnerById(partnerId));
                String upload = "/images/" + newMenuBook.getId();
                FileUploadUtil.saveFile(upload, fileName, multipartFile);
            } else {
                if (newMenuBook.getPhoto().isEmpty()) {
                    newMenuBook.setPhoto(null);
                    newMenuBook.setHotelRestaurant(hotelRestaurantService.getHotelRestaurantById(hotelRestaurantId));
                    newMenuBook.setHotel(hotelService.getHotelById(hotelId));
                    newMenuBook.setPartner(partnerService.getPartnerById(partnerId));
                    menuBookService.saveMenuBook(newMenuBook);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        menuBookService.saveMenuBook(newMenuBook);
        return "redirect:/hotel-restaurant/" + hotelRestaurantId;
    }

    @GetMapping("/create-hotel-bar/{hotel-bar-id}/{hotel-id}/{partner-id}")
    public String createHotelBarMenu(Model model,
                                     @PathVariable("hotel-bar-id") Integer hotelBarId,
                                     @PathVariable("hotel-id") Integer hotelId,
                                     @PathVariable("partner-id") Integer partnerId) {
        model.addAttribute("hotelBarId", hotelBarId);
        model.addAttribute("hotelId", hotelId);
        model.addAttribute("partnerId", partnerId);
        model.addAttribute("hotel_bar_new_menu", new MenuBook());
        return "new-hotel-bar_menu-form";
    }

    @PostMapping("/create-hotel-bar/{hotel-bar-id}/{hotel-id}/{partner-id}")
    public String createHotelBarMenu(@ModelAttribute("hotel_bar_new_menu") MenuBook newMenuBook,
                                     @PathVariable("hotel-bar-id") Integer hotelBarId,
                                     @PathVariable("hotel-id") Integer hotelId,
                                     @PathVariable("partner-id") Integer partnerId,
                                     @RequestParam("hbImage") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            newMenuBook.setPhoto(fileName);
            newMenuBook.setHotelBar(hotelBarService.getHotelBarById(hotelBarId));
            newMenuBook.setHotel(hotelService.getHotelById(hotelId));
            newMenuBook.setPartner(partnerService.getPartnerById(partnerId));
            String upload = "/images/" + newMenuBook.getId();
            FileUploadUtil.saveFile(upload, fileName, multipartFile);
        } else {
            if (newMenuBook.getPhoto().isEmpty()) {
                newMenuBook.setPhoto(null);
                newMenuBook.setHotelBar(hotelBarService.getHotelBarById(hotelBarId));
                newMenuBook.setHotel(hotelService.getHotelById(hotelId));
                newMenuBook.setPartner(partnerService.getPartnerById(partnerId));
                menuBookService.saveMenuBook(newMenuBook);
            }
        }
        menuBookService.saveMenuBook(newMenuBook);
        return "redirect:/hotel-bar/" + hotelBarId;
    }

    @GetMapping("/update-restaurant/{restaurant-id}/{restaurant-menu_id}")
    public String updateRestaurantMenu(Model model,
                                       @PathVariable("restaurant-menu_id") Integer menuId,
                                       @PathVariable("restaurant-id") Integer restaurantId) {
        model.addAttribute("restaurantId", restaurantId);
        model.addAttribute("restaurant_update_menu", menuBookService.getMenuBookById(menuId));
        return "update-restaurant-menu_book";
    }

    @PostMapping("/update-restaurant/{restaurant-id}")
    public String updateRestaurantMenu(@ModelAttribute("restaurant_update_menu") MenuBook updateMenuBook,
                                       @PathVariable("restaurant-id") Integer restaurantId,
                                       @RequestParam("image_M_U_R") MultipartFile multipartFile) throws IOException {
        try {
            if (!multipartFile.isEmpty()) {
                String updatedFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                updateMenuBook.setPhoto(updatedFileName);
                String upload = "/images/" + updateMenuBook.getId();
                FileUploadUtil.saveFile(upload, updatedFileName, multipartFile);
            } else {
                if (updateMenuBook.getPhoto().isEmpty()) {
                    updateMenuBook.setPhoto(null);
                    menuBookService.saveMenuBook(updateMenuBook);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        menuBookService.saveMenuBook(updateMenuBook);
        return "redirect:/restaurants/" + restaurantId;
    }

    @GetMapping("/update-bar/{bar-id}/{bar-menu_id}")
    public String updateBarMenu(Model model,
                                @PathVariable("bar-menu_id") Integer menuId,
                                @PathVariable("bar-id") Integer barId) {
        model.addAttribute("barId", barId);
        model.addAttribute("bar_update_menu", menuBookService.getMenuBookById(menuId));
        return "update-bar-menu_book";
    }

    @PostMapping("/update-bar/{bar-id}")
    public String updateBarMenu(@ModelAttribute("bar-update_menu") MenuBook updateMenuBook,
                                @PathVariable("bar-id") Integer barId,
                                @RequestParam("image_M_U_R") MultipartFile multipartFile) throws IOException {
        try {
            if (!multipartFile.isEmpty()) {
                String updatedFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                updateMenuBook.setPhoto(updatedFileName);
                String upload = "/images/" + updateMenuBook.getId();
                FileUploadUtil.saveFile(upload, updatedFileName, multipartFile);
            } else {
                if (updateMenuBook.getPhoto().isEmpty()) {
                    updateMenuBook.setPhoto(null);
                    menuBookService.saveMenuBook(updateMenuBook);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        menuBookService.saveMenuBook(updateMenuBook);
        menuBookService.saveMenuBook(updateMenuBook);
        return "redirect:/bars/" + barId;
    }

    @GetMapping("/update-hotel-restaurant/{hotel_restaurant-id}/{hotel_restaurant-menu_id}")
    public String updateHotelRestaurantMenu(Model model,
                                            @PathVariable("hotel_restaurant-menu_id") Integer menuId,
                                            @PathVariable("hotel_restaurant-id") Integer hotelRestaurantId) {
        model.addAttribute("hotelRestaurantId", hotelRestaurantId);
        model.addAttribute("hotel_restaurant_update_menu", menuBookService.getMenuBookById(menuId));
        return "update-hotel_restaurant-menu_book";
    }


    @PostMapping("/update-hotel-restaurant/{hotel_restaurant-id}")
    public String updateHotelRestaurantMenu(@ModelAttribute("hotel_restaurant_update_menu") MenuBook updateMenuBook,
                                            @PathVariable("hotel_restaurant-id") Integer hotelRestaurantId,
                                            @RequestParam("image_M_U_R") MultipartFile multipartFile) throws IOException {
        try {
            if (!multipartFile.isEmpty()) {
                String updatedFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                updateMenuBook.setPhoto(updatedFileName);
                String upload = "/images/" + updateMenuBook.getId();
                FileUploadUtil.saveFile(upload, updatedFileName, multipartFile);
            } else {
                if (updateMenuBook.getPhoto().isEmpty()) {
                    updateMenuBook.setPhoto(null);
                    menuBookService.saveMenuBook(updateMenuBook);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        menuBookService.saveMenuBook(updateMenuBook);
        return "redirect:/hotel-restaurant/" + hotelRestaurantId;
    }

    @GetMapping("/update-hotel-bar/{hotel_bar-id}/{hotel_bar-menu_id}")
    public String updateHotelBarMenu(Model model,
                                     @PathVariable("hotel_bar-menu_id") Integer menuId,
                                     @PathVariable("hotel_bar-id") Integer hotelBarId) {
        model.addAttribute("hotelBarId", hotelBarId);
        model.addAttribute("hotel_bar_update_menu", menuBookService.getMenuBookById(menuId));
        return "update-hotel_bar-menu_book";
    }


    @PostMapping("/update-hotel-bar/{hotel_bar-id}")
    public String updateHotelBarMenu(@ModelAttribute("hotel_bar_update_menu") MenuBook updateMenuBook,
                                     @PathVariable("hotel_bar-id") Integer hotelBarId,
                                     @RequestParam("image_M_U_R") MultipartFile multipartFile) throws IOException {
        try {
            if (!multipartFile.isEmpty()) {
                String updatedFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                updateMenuBook.setPhoto(updatedFileName);
                String upload = "/images/" + updateMenuBook.getId();
                FileUploadUtil.saveFile(upload, updatedFileName, multipartFile);
            } else {
                if (updateMenuBook.getPhoto().isEmpty()) {
                    updateMenuBook.setPhoto(null);
                    menuBookService.saveMenuBook(updateMenuBook);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        menuBookService.saveMenuBook(updateMenuBook);
        return "redirect:/hotel-bar/" + hotelBarId;
    }

    @PostMapping("/{id}/delete")
    public String deleteRestaurantMenu(@PathVariable("id") Integer id) {
        menuBookService.deleteMenuBookById(id);
        return "redirect:/partner/current";
    }

}
