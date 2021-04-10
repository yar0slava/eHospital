package com.example.demo.core.database.repository;

import com.example.demo.core.database.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity,Long> {

    Optional<AppointmentEntity> findByPatientId(long patientId);

    Optional<AppointmentEntity> findByDoctorId(long doctorId);

    Optional<AppointmentEntity> findByPatientIdIsNullAndDoctorIdIs(long doctorId);
}
