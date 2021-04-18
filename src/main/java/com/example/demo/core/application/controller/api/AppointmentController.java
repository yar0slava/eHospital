package com.example.demo.core.application.controller.api;

import com.example.demo.core.application.dto.AppointmentDto;
import com.example.demo.core.domain.service.AppointmentService;
import org.springframework.http.HttpStatus;
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

    // doctor adds free DateTime for appointment
    // should pass doctor id, dateTime in AppointmentDto
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentDto addFreeAppointment(@RequestBody AppointmentDto appointmentDto){
        return appointmentService.addFreeAppointment(appointmentDto);
    }

    @GetMapping("/patient/{patientId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentDto> findByPatientId(@PathVariable("patientId") long patientId){
        return appointmentService.findByPatientId(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentDto> findByDoctorId(@PathVariable("doctorId") long doctorId){
        return appointmentService.findByDoctorId(doctorId);
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
