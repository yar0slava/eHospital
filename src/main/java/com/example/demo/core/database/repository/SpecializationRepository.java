package com.example.demo.core.database.repository;

import com.example.demo.core.database.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpecializationRepository extends JpaRepository<Specialization,Long>{

    @Query("SELECT b FROM Specialization b WHERE lower(b.name) LIKE %:search%")
    Optional<Specialization> findSpecializationByName(@Param("search") String search);


    Optional<Specialization> findByNameEquals(String name);

}
