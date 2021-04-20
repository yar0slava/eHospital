package com.example.demo.core.database.repository;

import com.example.demo.core.database.entity.AppointmentEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity,Long> {

    AppointmentEntity findById(long i);
    List<AppointmentEntity> findByPatientId(long patientId);

    List<AppointmentEntity> findByDoctorId(long doctorId, Pageable pageable);

    List<AppointmentEntity> findByDoctorId(long doctorId);

    List<AppointmentEntity> findByPatientIdIsNullAndDoctorIdIs(long doctorId);

    List<AppointmentEntity> findByPatientIdIsNullAndDoctorIdIsAndDateTimeAfterAndDateTimeBefore(long doctorId, LocalDateTime after, LocalDateTime before);

    List<AppointmentEntity> findByDateTimeBetween(LocalDateTime from, LocalDateTime to);

    void deleteById(long id);

}
