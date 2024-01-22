package com.example.stay_mate.controller.bar;

import com.example.stay_mate.FileUploadUtil;
import com.example.stay_mate.model.bar.Bar;
import com.example.stay_mate.service.bar.BarService;
import com.example.stay_mate.service.menubook.MenuBookService;
import com.example.stay_mate.service.partner.PartnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/bars")
public class BarController {
    private final BarService barService;
    private final MenuBookService menuBookService;
    private final PartnerService partnerService;

    public BarController(BarService barService, MenuBookService menuBookService, PartnerService partnerService) {
        this.barService = barService;
        this.menuBookService = menuBookService;
        this.partnerService = partnerService;
    }

    @GetMapping("/all")
    public String getAllBars(Model model) {
        List<Bar> all_bars = barService.getAllBar();
        model.addAttribute("all_bars", all_bars);
        return "bar-list";
    }

    @GetMapping("/{id}")
    public String getCurrentBar(Model model, @PathVariable("id") Integer barId) {
        model.addAttribute("menu_book", menuBookService.getMenuBookByBar
                (barService.getBarById(barId)));
        model.addAttribute("bar", barService.getBarById(barId));
        model.addAttribute("image", "/uploads/" + barId + "-bar-" + barService.getBarById(barId).getName());
        return "bar";
    }

    @GetMapping("/create/{partner-id}")
    public String addBar(Model model, @PathVariable("partner-id") Integer partnerId) {
        model.addAttribute("partnerId", partnerId);
        model.addAttribute("new_bar", new Bar());
        return "new-bar-form";
    }

    @PostMapping("/create/{partner-id}")
    public String addBar(@ModelAttribute("new_bar") Bar bar,
                         @PathVariable("partner-id") Integer partnerId,
                         @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            bar.setPhoto(fileName);
            bar.setPartner(partnerService.getPartnerById(partnerId));
            barService.saveBar(bar);
            String upload = "/images/" + bar.getId();
            FileUploadUtil.saveFile(upload, fileName, multipartFile);
        } else {
            if (bar.getPhoto().isEmpty()) {
                bar.setPhoto(null);
                bar.setPartner(partnerService.getPartnerById(partnerId));
                barService.saveBar(bar);
            }
        }
        barService.saveBar(bar);
        return "redirect:/partner/current";
    }

    @GetMapping("/{id}/update/{partner-id}")
    public String updateBar(@PathVariable("id") Integer id, Model model,
                            @PathVariable("partner-id")Integer partnerId) {
        model.addAttribute(partnerService.getPartnerById(partnerId));
        model.addAttribute("updated_bar", barService.getBarById(id));
        return "bar-update";
    }

    @PostMapping("/{id}/update/{partner-id}")
    public String updateBar(@PathVariable("id") Integer id,
                            @PathVariable("partner-id") Integer partnerId,
                            @ModelAttribute("updated_bar") Bar updatedBar,
                            @RequestParam("image") MultipartFile multipartFile) throws IOException {
        barService.getBarById(id);
            if (!multipartFile.isEmpty()) {
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                updatedBar.setPhoto(fileName);
                updatedBar.setPartner(partnerService.getPartnerById(partnerId));
                barService.saveBar(updatedBar);
                String upload = "/images/" + updatedBar.getId();
                FileUploadUtil.saveFile(upload, fileName, multipartFile);
            } else {
                if (updatedBar.getPhoto().isEmpty()) {
                    updatedBar.setPhoto(null);
                    updatedBar.setPartner(partnerService.getPartnerById(partnerId));
                    barService.saveBar(updatedBar);
                }
            }
        barService.saveBar(updatedBar);
        return "redirect:/partner/current";
    }


    @PostMapping("/{bar-id}/delete")
    public String deleteBar(@PathVariable("bar-id") Integer barId) {
        menuBookService.deleteMenuBookByBar(barService.getBarById(barId));
        barService.deleteBarById(barId);
        return "redirect:/partner/current";
    }

}
