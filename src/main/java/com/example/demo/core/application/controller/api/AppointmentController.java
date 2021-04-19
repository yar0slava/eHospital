package com.example.demo.core.application.controller.api;

import com.example.demo.core.application.dto.AddAppointmentRangeDto;
import com.example.demo.core.application.dto.AppointmentDto;
import com.example.demo.core.domain.model.User;
import com.example.demo.core.domain.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    public AppointmentDto addAppointment(@RequestBody AppointmentDto appointmentDto){
        return appointmentService.addAppointment(appointmentDto);
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
//        return appointmentService.addFreeAppointment(addAppointmentRangeDto);
//    }

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

    @GetMapping("/free/doctor/{doctorId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentDto> findByPatientIdIsNullAndDoctorIdIs(@PathVariable("doctorId") long doctorId){
        return appointmentService.findDoctorsFree(doctorId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentDto> findByDateTimeBetween(@RequestParam(value = "from")LocalDateTime from,
                                                      @RequestParam(value = "to")LocalDateTime to){
        return appointmentService.findBetween(from,to);
    }
}
