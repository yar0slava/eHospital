package com.example.demo.core.application.controller.api;

import com.example.demo.core.database.entity.HospitalEntity;
import com.example.demo.core.domain.service.HospitalService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HospitalController {
    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }


    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String getMain(Model model) {
        List<HospitalEntity> hospitals = hospitalService.getAllHospitals();
        model.addAttribute("hospitals", hospitals);
        return "main";
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
