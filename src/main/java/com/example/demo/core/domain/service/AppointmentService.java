package com.example.demo.core.domain.service;

import com.example.demo.core.application.dto.AddAppointmentRangeDto;
import com.example.demo.core.application.dto.AppointmentDto;
import com.example.demo.core.database.entity.AppointmentEntity;
import com.example.demo.core.database.entity.UserEntity;
import com.example.demo.core.database.repository.AppointmentRepository;
import com.example.demo.core.database.repository.UserRepository;
import com.example.demo.core.domain.model.User;
import com.example.demo.core.mapper.AppointmentMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AppointmentService {

    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentService(UserRepository userRepository, AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper) {
        this.userRepository = userRepository;
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

    public List<AppointmentDto> findDoctorsFreeAndDate(long doctorId, LocalDateTime date){
        return StreamSupport.stream(appointmentRepository.findByPatientIdIsNullAndDoctorIdIsAndDateTimeAfterAndDateTimeBefore(doctorId,date,date.plusDays(1)).spliterator(), false)
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> findBetween(LocalDateTime from, LocalDateTime to){
        return StreamSupport.stream(appointmentRepository.findByDateTimeBetween(from,to).spliterator(), false)
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> addFreeAppointment(AddAppointmentRangeDto addAppointmentRangeDto) {
        List<AppointmentDto> res = new ArrayList<>();

        LocalDateTime currTime = addAppointmentRangeDto.getFrom();

        AppointmentEntity appointmentEntity;

        while (currTime.isBefore(addAppointmentRangeDto.getTo())){
            for(int i = 12; i<18; i++){
                appointmentEntity = new AppointmentEntity();
                appointmentEntity.setDoctorId(addAppointmentRangeDto.getDoctorId());
                currTime = currTime.withHour(i).withMinute(0);
                System.out.println(currTime);
                appointmentEntity.setDateTime(currTime);
                res.add(appointmentMapper.toDto(appointmentRepository.save(appointmentEntity)));
            }
            currTime = currTime.plusDays(1);
        }
        return res;
    }

    public List<AppointmentDto> addFreeAppointment(AddAppointmentRangeDto addAppointmentRangeDto, User authenticatedUser) {
        UserEntity userEntity = userRepository.findByEmail(authenticatedUser.getEmail()).get();

        List<AppointmentDto> res = new ArrayList<>();

        LocalDateTime currTime = addAppointmentRangeDto.getFrom();
        AppointmentEntity appointmentEntity;
        while (currTime.isBefore(addAppointmentRangeDto.getTo())){
            for(int i = 12; i<18; i++){
                appointmentEntity = new AppointmentEntity();
                appointmentEntity.setDoctorId(addAppointmentRangeDto.getDoctorId());
                currTime = currTime.withHour(i).withMinute(0);
                System.out.println(currTime);
                appointmentEntity.setDateTime(currTime);
                res.add(appointmentMapper.toDto(appointmentRepository.save(appointmentEntity)));
            }
            currTime = currTime.plusDays(1);
        }
        return res;
    }

    public List<AppointmentDto> getAll(Integer page, Integer size) {

        List<AppointmentDto> appointments;
        appointments = StreamSupport.stream(appointmentRepository.findAll(
                PageRequest.of(page != null ? page : 0,
                        size != null ? size : 10,
                        Sort.by("dateTime").ascending())).spliterator(), false)
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());

        return appointments;
    }
}
