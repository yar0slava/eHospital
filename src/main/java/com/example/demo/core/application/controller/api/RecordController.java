package com.example.demo.core.application.controller.api;

import com.example.demo.core.application.dto.UserDto;
import com.example.demo.core.database.entity.UserEntity;
import com.example.demo.core.domain.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RecordController {

    private final UserService userService;

    public RecordController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/reception/{doctorId}")
    public String gtReception(@PathVariable("doctorId") long doctorId, Model model){

        UserEntity userDto = userService.getDoctorById(doctorId);
        model.addAttribute("doctor", userDto);
        return "recordPage";
    }
}
