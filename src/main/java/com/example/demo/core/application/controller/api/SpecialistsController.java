package com.example.demo.core.application.controller.api;

import com.example.demo.core.database.entity.Specialization;
import com.example.demo.core.database.entity.UserEntity;
import com.example.demo.core.domain.service.HospitalService;
import com.example.demo.core.domain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
public class SpecialistsController {
    private final HospitalService hospitalService;
    private final UserService userService;

    public SpecialistsController(HospitalService hospitalService, UserService userService) {
        this.hospitalService = hospitalService;
        this.userService = userService;
    }

    @RequestMapping(value = "/doctors", method = RequestMethod.GET)
    public String getDoctors(Model model) {
        List<UserEntity> userEntities = userService.getAllDoctors();
        Set<Specialization> specializations = userService.getSpezialization(userEntities);
        Set<String> towns = userService.getTowns(userEntities);
        Set<String> rgions = userService.getRgions(userEntities);

        model.addAttribute("specializations", specializations);
        model.addAttribute("doctors", userEntities);
        model.addAttribute("towns", towns);
        model.addAttribute("rgions", rgions);
        return "searchSpecialists";
    }

    @ResponseBody
    @GetMapping("/search-doctors")
    public ResponseEntity<Set<UserEntity>> searchHospital(@RequestParam(name = "specialization", required = false) List<String> specialization,
                                                           @RequestParam(name = "town", required = false) String town,
                                                           @RequestParam(name = "region", required = false) String region
         ){
        System.out.println("==================================");
        System.out.println(specialization);
        System.out.println(town);
        System.out.println(region);

        Set<UserEntity> userEntities = userService.findDoctors(specialization,town,region);
        System.out.println("=================================");
        return  ResponseEntity.ok().body(userEntities);
    }


}
