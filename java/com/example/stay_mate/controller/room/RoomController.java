package com.example.stay_mate.controller.room;

import com.example.stay_mate.FileUploadUtil;
import com.example.stay_mate.model.room.Room;
import com.example.stay_mate.service.room.RoomService;
import com.example.stay_mate.service.hotel.HotelService;
import com.example.stay_mate.service.partner.PartnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/room")
public class  RoomController {
    private final RoomService roomService;
    private final HotelService hotelService;
    private final PartnerService partnerService;

    public RoomController(RoomService roomService,
                          HotelService hotelService,
                          PartnerService partnerService) {
        this.roomService = roomService;
        this.hotelService = hotelService;
        this.partnerService = partnerService;
    }

    @GetMapping("/all")
    public String getAllRooms(Model model) {
        model.addAttribute("all_rooms", roomService.getAllRooms());
        return "room-list";
    }

    @GetMapping("/{id}")
    public String getCurrentRoom(Model model, @PathVariable("id") Integer roomId) {
        model.addAttribute("room", roomService.getRoomById(roomId));
        return "room";
    }

    @GetMapping("/create/{hotel-id}/{partner-id}")
    public String createRoom(Model model,
                             @PathVariable("hotel-id") Integer hotelId,
                             @PathVariable("partner-id") Integer partnerId) {
        model.addAttribute("partnerId", partnerId);
        model.addAttribute("hotelId", hotelId);
        model.addAttribute("new_room", new Room());
        return "new-room-form";
    }

    @PostMapping("/create/{hotel-id}/{partner-id}")
    public String createRoom(@ModelAttribute("new_room") Room newRoom,
                             @PathVariable("hotel-id") Integer hotelId,
                             @PathVariable("partner-id") Integer partnerId,
                             @RequestParam("roomImage")MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            newRoom.setPhoto(fileName);
            newRoom.setHotel(hotelService.getHotelById(hotelId));
            newRoom.setPartner(partnerService.getPartnerById(partnerId));
            String upload = "/images/" + newRoom.getId();
            FileUploadUtil.saveFile(upload,fileName,multipartFile);
        }
        else {
            if (newRoom.getPhoto().isEmpty()){
                newRoom.setPhoto(null);
                newRoom.setHotel(hotelService.getHotelById(hotelId));
                newRoom.setPartner(partnerService.getPartnerById(partnerId));
                roomService.saveRoom(newRoom);
            }
        }
        roomService.saveRoom(newRoom);
        return "redirect:/hotels/" + hotelId + "/" + partnerId;
    }

    @GetMapping("/{id}/update/{hotel-id}/{partner-id}")
    public String updateRoom(Model model,
                             @PathVariable("id") Integer roomId,
                             @PathVariable("hotel-id") Integer hotelId,
                             @PathVariable("partner-id") Integer partnerId) {
        model.addAttribute("hotel", hotelService.getHotelById(hotelId));
        model.addAttribute("partner", partnerService.getPartnerById(partnerId));
        model.addAttribute("updated_room",
                roomService.getRoomById(roomId));
        return "update-room-form";
    }

    @PostMapping("/{id}/update/{hotel-id}/{partner-id}")
    public String updateRoom(@ModelAttribute("updated_room") Room updatedRoom,
                             @PathVariable("id") Integer roomId,
                             @PathVariable("hotel-id") Integer hotelId,
                             @PathVariable("partner-id") Integer partnerId,
                             @RequestParam("roomImage")MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            updatedRoom.setPhoto(fileName);
            updatedRoom.setHotel(hotelService.getHotelById(hotelId));
            updatedRoom.setPartner(partnerService.getPartnerById(partnerId));
            String upload = "/images/" + updatedRoom.getId();
            FileUploadUtil.saveFile(upload,fileName,multipartFile);
        }
        else {
            if (updatedRoom.getPhoto().isEmpty()){
                updatedRoom.setPhoto(null);
                updatedRoom.setHotel(hotelService.getHotelById(hotelId));
                updatedRoom.setPartner(partnerService.getPartnerById(partnerId));
                roomService.saveRoom(updatedRoom);
            }
        }
        roomService.saveRoom(updatedRoom);
        return "redirect:/room/" + roomId;
    }
    @PostMapping("/{id}/delete")
    public String deleteRoom(@PathVariable("id")Integer roomId){
        roomService.deleteRoomById(roomId);
        return "redirect:/partner/current";
    }
}
