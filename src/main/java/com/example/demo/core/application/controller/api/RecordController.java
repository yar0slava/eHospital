package com.example.demo.core.application.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RecordController {


    @GetMapping("/reception/{doctorId}")
    public String gtReception(@PathVariable("doctorId") long doctorId){
        return "recordPage";
    }
}
