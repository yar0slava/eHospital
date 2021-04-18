package com.example.demo.core.domain.service;

import com.example.demo.core.application.dto.AppointmentDto;
import com.example.demo.core.database.entity.AppointmentEntity;
import com.example.demo.core.database.repository.AppointmentRepository;
import com.example.demo.core.mapper.AppointmentMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentService(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
    }

    //  patient makes an appointment for existing DateTime
    // should pass AppointmentDto with id, patient id, (datetime), (doctor id)
    public AppointmentDto addAppointment(AppointmentDto appointmentDto){
        AppointmentEntity saved = appointmentRepository.save(appointmentMapper.toEntity(appointmentDto));
        return appointmentMapper.toDto(saved);
    }

    // patient cancels an appointment
    // should pass AppointmentDto with id, null intead of patient id, (datetime), (doctor id)
    public AppointmentDto cancelAppointment(AppointmentDto appointmentDto){
        AppointmentEntity saved = appointmentRepository.save(appointmentMapper.toEntity(appointmentDto));
        return appointmentMapper.toDto(saved);
    }

    // doctor deletes free DateTime for appointment
    // should pass id
    public void deleteFreeAppointment(long id){
        appointmentRepository.deleteById(id);
    }

    // doctor adds free DateTime for appointment
    // should pass doctor id, dateTime in AppointmentDto
    public AppointmentDto addFreeAppointment(AppointmentDto appointmentDto){
        AppointmentEntity saved = appointmentRepository.save(appointmentMapper.toEntity(appointmentDto));
        return appointmentMapper.toDto(saved);
    }

    public List<AppointmentDto> findByPatientId(long patientId){
        return StreamSupport.stream(appointmentRepository.findByPatientId(patientId).spliterator(), false)
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> findByDoctorId(long doctorId){
        return StreamSupport.stream(appointmentRepository.findByDoctorId(doctorId).spliterator(), false)
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> findDoctorsFree(long doctorId){
        return StreamSupport.stream(appointmentRepository.findByPatientIdIsNullAndDoctorIdIs(doctorId).spliterator(), false)
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> findBetween(LocalDateTime from, LocalDateTime to){
        return StreamSupport.stream(appointmentRepository.findByDateTimeBetween(from,to).spliterator(), false)
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }
}
