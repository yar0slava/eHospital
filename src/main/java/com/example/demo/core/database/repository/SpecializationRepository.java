package com.example.demo.core.database.repository;

import com.example.demo.core.database.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecializationRepository extends JpaRepository<Specialization,Long>{
    Optional<Specialization> findSpecializationByName(String nam);
}
