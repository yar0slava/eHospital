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
public class SpecialistsController {


    @RequestMapping(value = "/doctors", method = RequestMethod.GET)
    public String getDoctors(Model model) {


        return "main";
    }

}
