package com.example.demo.core.application.controller.api;

import com.example.demo.core.application.dto.AddHospitalDto;
import com.example.demo.core.application.dto.HospitalDto;
import com.example.demo.core.database.entity.HospitalEntity;
import com.example.demo.core.database.entity.UserEntity;
import com.example.demo.core.domain.service.HospitalService;
import com.example.demo.core.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class HospitalController {
    private final HospitalService hospitalService;
    private final UserService userService;

    public HospitalController(HospitalService hospitalService, UserService userService) {
        this.hospitalService = hospitalService;
        this.userService = userService;
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String getMain(Model model) {
        List<HospitalEntity> hospitals = hospitalService.getAllHospitals();
        model.addAttribute("hospitals", hospitals);
        return "main";
    }


    @RequestMapping(value = "/hospital/add", method = RequestMethod.GET)
    public String getHospitalAdd(Model model) {
        List<HospitalEntity> hospitals = hospitalService.getAllHospitals();
        Set<String> allTowns = hospitalService.getUniqueTowns(hospitals);
        Set<String> allRgions = hospitalService.getUniqueRgions(hospitals);

        model.addAttribute("towns", allTowns);
        model.addAttribute("regions", allRgions);
        return "addHospital";
    }

    @ResponseBody
    @RequestMapping(value = "/hospital/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public HospitalDto signUp(@RequestBody AddHospitalDto hospitalDto) {
        System.out.println(hospitalDto.getName());
        System.out.println(hospitalDto.getRegion());
        System.out.println(hospitalDto.getRegion());
        return hospitalService.addHospital(hospitalDto);
      //  return userService.addUser(userDto);
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
