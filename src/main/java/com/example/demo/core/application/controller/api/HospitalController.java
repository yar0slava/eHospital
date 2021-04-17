package com.example.demo.core.application.controller.api;

import com.example.demo.core.database.entity.HospitalEntity;
import com.example.demo.core.database.entity.UserEntity;
import com.example.demo.core.domain.service.HospitalService;
import com.example.demo.core.domain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class HospitalController {
    private final HospitalService hospitalService;
    private final UserService userService;

    public HospitalController(HospitalService hospitalService, UserService userService) {
        this.hospitalService = hospitalService;
        this.userService = userService;
    }


    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String getMain(Model model) {
        List<HospitalEntity> hospitals = hospitalService.getAllHospitals();
        model.addAttribute("hospitals", hospitals);
        return "main";
    }

    @RequestMapping(value = "/hospital/{id}", method = RequestMethod.GET)
    public String getHospital(@PathVariable long id,Model model) {

        Optional<HospitalEntity> hospital = hospitalService.getHospitalById(id);

        List<UserEntity> doctors = userService.getAllDoctorsOfHospital(id);
        model.addAttribute("doctors", doctors);
        model.addAttribute("hospital", hospital.get());
        return "hospitalPage";
    }

    @ResponseBody
    @GetMapping("/search-hospitals")
    public ResponseEntity<List<HospitalEntity>> searchHospital(@RequestParam(name = "town", required = false) String town,
                                                           @RequestParam(name = "region", required = false) String region) {

        return ResponseEntity.ok().body(hospitalService.findHospitals(town,region));
    }


    @ResponseBody
    @GetMapping("/search-hospitals-input")
    public ResponseEntity<List<HospitalEntity>> searchHospitalInput(@RequestParam(name = "search", required = false) String search) {
        return ResponseEntity.ok().body(hospitalService.findHospitalsByNameTownRegion(search));
    }


}
