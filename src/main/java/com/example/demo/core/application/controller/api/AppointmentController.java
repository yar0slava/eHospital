package com.example.demo.core.application.controller.api;

import com.example.demo.core.application.dto.AddAppointmentRangeDto;
import com.example.demo.core.application.dto.AppointmentDto;
import com.example.demo.core.application.dto.UserDto;
import com.example.demo.core.domain.model.User;
import com.example.demo.core.domain.service.AppointmentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.security.Security;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }


    //  patient makes an appointment for existing DateTime
    // should pass AppointmentDto with id, patient id, (datetime), (doctor id)

    @PutMapping(value = "/signup")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentDto addAppointment(@RequestParam(name = "meeting", required = true) long meeting){
        System.out.println("======================");
        System.out.println(meeting);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("AUTH");
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(name);
       // final User authenticatedUser = (User) auth.getPrincipal();
return null;
        //System.out.println(authenticatedUser.getId());
        //return appointmentService.addAppointment(meeting, authenticatedUser.getId());
    }

    // patient cancels an appointment
    // should pass AppointmentDto with id, null intead of patient id, (datetime), (doctor id)
    @PutMapping(value = "/cancel")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentDto cancelAppointment(@RequestBody AppointmentDto appointmentDto){
        return appointmentService.cancelAppointment(appointmentDto);
    }

    // doctor deletes free DateTime for appointment
    // should pass id
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFreeAppointment(@PathVariable("id") Long id){
        appointmentService.deleteFreeAppointment(id);
    }

    // doctor adds free DateTime for appointments from date 1 to date 2
    // should pass doctor id, dateTime and dateTime
//    @PostMapping("/add")
//    @ResponseStatus(HttpStatus.OK)
//    public List<AppointmentDto> addFreeAppointment(@RequestBody AddAppointmentRangeDto addAppointmentRangeDto){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        final User authenticatedUser = (User) auth.getPrincipal();
//
//        return appointmentService.addFreeAppointment(addAppointmentRangeDto, authenticatedUser);
//    }

    // doctor adds free DateTime for appointments from date 1 to date 2
    // should pass doctor id, dateTime and dateTime
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentDto> addFreeAppointment(@RequestBody AddAppointmentRangeDto addAppointmentRangeDto){
        System.out.println("=============================");
        System.out.println(addAppointmentRangeDto.getDoctorId());
        System.out.println(addAppointmentRangeDto.getFrom());
        System.out.println(addAppointmentRangeDto.getTo());
        return appointmentService.addFreeAppointment(addAppointmentRangeDto);
    }

    @GetMapping("/patient/{patientId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentDto> findByPatientId(@PathVariable("patientId") long patientId){
        return appointmentService.findByPatientId(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentDto> findByDoctorId(@PathVariable("doctorId") long doctorId){
        List<AppointmentDto> res = appointmentService.findByDoctorId(doctorId);
        for (AppointmentDto a: res) {
            System.out.println(a.toString());
        }
        return res;
    }

    @GetMapping("/free")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentDto> findFreeForDoctorAndDay(@RequestParam("doctorId") long doctorId,
                                                        @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date){
        return appointmentService.findDoctorsFreeAndDate(doctorId, date);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentDto> getPaged(@RequestParam(value = "page") Integer page,
                                   @RequestParam(value = "size") Integer size) {
        return appointmentService.getAll(page, size);
    }
}
