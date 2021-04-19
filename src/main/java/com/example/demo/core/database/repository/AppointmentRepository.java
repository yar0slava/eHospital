package com.example.demo.core.database.repository;

import com.example.demo.core.database.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity,Long> {

    List<AppointmentEntity> findByPatientId(long patientId);

    List<AppointmentEntity> findByDoctorId(long doctorId);

    List<AppointmentEntity> findByPatientIdIsNullAndDoctorIdIs(long doctorId);

    List<AppointmentEntity> findByPatientIdIsNullAndDoctorIdIsAndDateTimeAfter(long doctorId, LocalDateTime date);

    List<AppointmentEntity> findByDateTimeBetween(LocalDateTime from, LocalDateTime to);

    void deleteById(long id);

}
