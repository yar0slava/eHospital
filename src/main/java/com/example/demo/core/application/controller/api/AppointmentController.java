package com.example.demo.core.application.controller.api;

import com.example.demo.core.application.dto.AddAppointmentRangeDto;
import com.example.demo.core.application.dto.AppointmentDto;
import com.example.demo.core.application.dto.AppointmentWithNameDto;
import com.example.demo.core.domain.model.User;
import com.example.demo.core.domain.service.AppointmentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }


    //  patient makes an appointment for existing DateTime
    // should pass AppointmentDto with id, patient id, (datetime), (doctor id)
    @PreAuthorize("hasAuthority('patient')")
    @PutMapping(value = "/signup")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentDto addAppointment(@RequestParam(name = "meeting", required = true) long meeting){
        System.out.println("======================");
        System.out.println(meeting);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final User authenticatedUser = (User) auth.getPrincipal();

        System.out.println(authenticatedUser.getId());
        return appointmentService.addAppointment(meeting, authenticatedUser.getId());
    }

    // patient cancels an appointment
    // should pass AppointmentDto with id, null intead of patient id, (datetime), (doctor id)
    @PreAuthorize("hasAuthority('patient')")
    @PutMapping(value = "/cancel")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentDto cancelAppointment(@RequestBody AppointmentDto appointmentDto){
        return appointmentService.cancelAppointment(appointmentDto);
    }

    // doctor deletes free DateTime for appointment
    // should pass id
    @PreAuthorize("hasAuthority('doctor')")
    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFreeAppointment(@PathVariable("id") Long id){
        appointmentService.deleteFreeAppointment(id);
    }

    // doctor adds free DateTime for appointments from date 1 to date 2
    // should pass doctor id, dateTime and dateTime
    @PreAuthorize("hasAuthority('doctor')")
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentDto> addFreeAppointment(@RequestBody AddAppointmentRangeDto addAppointmentRangeDto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final User authenticatedUser = (User) auth.getPrincipal();

        return appointmentService.addFreeAppointment(addAppointmentRangeDto, authenticatedUser);
    }

    // doctor adds free DateTime for appointments from date 1 to date 2
    // should pass doctor id, dateTime and dateTime
//    @PostMapping("/add")
//    @ResponseStatus(HttpStatus.OK)
//    public List<AppointmentDto> addFreeAppointment(@RequestBody AddAppointmentRangeDto addAppointmentRangeDto){
//        System.out.println("=============================");
//        System.out.println(addAppointmentRangeDto.getDoctorId());
//        System.out.println(addAppointmentRangeDto.getFrom());
//        System.out.println(addAppointmentRangeDto.getTo());
//        return appointmentService.addFreeAppointment(addAppointmentRangeDto);
//    }

    @CrossOrigin
    @PreAuthorize("hasAuthority('patient')")
    @GetMapping("/patient")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentWithNameDto> findByPatientId(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final User authenticatedUser = (User) auth.getPrincipal();

        return appointmentService.findByPatientId(authenticatedUser.getId());
    }

    @PreAuthorize("hasAuthority('doctor')")
    @GetMapping("/doctor/{doctorId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentWithNameDto> getPagedForPatient(@PathVariable("doctorId") long doctorId,
                                                   @RequestParam(value = "page") Integer page,
                                                   @RequestParam(value = "size") Integer size){
        return appointmentService.getPagedWithDoctorId(doctorId,page,size);
    }

//    @PreAuthorize("hasAuthority('doctor')")
//    @GetMapping("/free")
//    @ResponseStatus(HttpStatus.OK)
//    public List<AppointmentDto> findFreeForDoctorAndDay(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        final User authenticatedUser = (User) auth.getPrincipal();
//
//        return appointmentService.findDoctorsFreeAndDate(authenticatedUser.getId(), date);
//    }

//    @PreAuthorize("hasAuthority('patient')")
    @GetMapping("/free")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentDto> findFreeForDoctorAndDay(@RequestParam("doctorId") long doctorId,
                                                        @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date){
        return appointmentService.findDoctorsFreeAndDate(doctorId, date);
    }

    @PreAuthorize("hasAuthority('doctor')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentWithNameDto> getPagedForDoctor(@RequestParam(value = "page") Integer page,
                                                          @RequestParam(value = "size") Integer size) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final User authenticatedUser = (User) auth.getPrincipal();

        return appointmentService.getPagedWithDoctorId(authenticatedUser.getId(),page, size);
    }
}
