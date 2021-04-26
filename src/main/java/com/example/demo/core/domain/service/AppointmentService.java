package com.example.demo.core.domain.service;

import com.example.demo.core.application.dto.AddAppointmentRangeDto;
import com.example.demo.core.application.dto.AppointmentDto;
import com.example.demo.core.application.dto.AppointmentWithNameDto;
import com.example.demo.core.database.entity.AppointmentEntity;
import com.example.demo.core.database.entity.UserEntity;
import com.example.demo.core.database.repository.AppointmentRepository;
import com.example.demo.core.database.repository.UserRepository;
import com.example.demo.core.domain.model.User;
import com.example.demo.core.mapper.AppointmentMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public AppointmentDto addAppointment(long appointmentDto, long i){

        AppointmentEntity appointmentEntity = appointmentRepository.findById(appointmentDto);
           appointmentEntity.setPatientId(i);
            AppointmentEntity saved = appointmentRepository.save(appointmentEntity);
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

    public List<AppointmentWithNameDto> findByPatientId(long patientId){

        List<AppointmentWithNameDto> res = new ArrayList<>();

        List<AppointmentDto> appointments = StreamSupport.stream(appointmentRepository.findByPatientId(patientId).spliterator(), false)
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());

        AppointmentWithNameDto app;
        for (AppointmentDto a: appointments) {
            app = new AppointmentWithNameDto();
            app.setId(a.getId());
            app.setPatientId(patientId);
            app.setDateTime(a.getDateTime());
            if(a.getDoctorId() != null){
                UserEntity u = userRepository.findById(a.getDoctorId()).get();
                app.setDoctorId(a.getDoctorId());
                app.setName(u.getFirstName()+" "+u.getLastName());
            }
            res.add(app);
        }

        return res;
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
                appointmentEntity.setDoctorId(userEntity.getId());
                currTime = currTime.withHour(i).withMinute(0);
                System.out.println(currTime);
                appointmentEntity.setDateTime(currTime);
                res.add(appointmentMapper.toDto(appointmentRepository.save(appointmentEntity)));
            }
            currTime = currTime.plusDays(1);
        }
        return res;
    }

    public List<AppointmentWithNameDto> getPagedWithDoctorId(long doctorId, Integer page, Integer size) {

        List<AppointmentWithNameDto> res = new ArrayList<>();

        List<AppointmentDto> appointments = StreamSupport.stream(appointmentRepository.findByDoctorId(doctorId,
                PageRequest.of(page != null ? page : 0,
                        size != null ? size : 10,
                        Sort.by("dateTime").ascending())).spliterator(), false)
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());

        AppointmentWithNameDto app;
        for (AppointmentDto a: appointments) {
            app = new AppointmentWithNameDto();
            app.setId(a.getId());
            app.setDoctorId(a.getDoctorId());
            app.setDateTime(a.getDateTime());
            if(a.getPatientId() != null){
                UserEntity u = userRepository.findById(a.getPatientId()).get();
                app.setPatientId(a.getPatientId());
                app.setName(u.getFirstName()+" "+u.getLastName());
            }
            res.add(app);
        }

        return res;
    }
}
