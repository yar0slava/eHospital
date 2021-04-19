package com.example.demo.core.database.repository;

import com.example.demo.core.application.dto.UserDto;
import com.example.demo.core.database.entity.HospitalEntity;
import com.example.demo.core.database.entity.Specialization;
import com.example.demo.core.database.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface UserRepository extends PagingAndSortingRepository<UserEntity,Long> {

    Iterable<UserEntity> findAll();

    @Query("SELECT b FROM UserEntity b WHERE b.hospital is not null")
    List<UserEntity> findAllDoctors();

    List<UserEntity> findByHospital(HospitalEntity hospitalEntity);

    Page<UserEntity> findAll(Pageable pageable);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(long i);


    List<UserEntity> findBySpecializationContains(Specialization specialization);

    @Query("SELECT b FROM UserEntity b WHERE b.hospital.town LIKE %:town% ")
    List<UserEntity> findAllWhereTownLike(@Param("town") String town);

    @Query("SELECT b FROM UserEntity b WHERE b.hospital.region LIKE %:rgion%")
    List<UserEntity> findAllWhereRgionLike(@Param("rgion") String rgion);

    @Query("SELECT b FROM UserEntity b WHERE b.hospital.town LIKE %:town% AND b.hospital.region like %:rgion%")
    List<UserEntity> findAllWhereTownAndRegionLike(@Param("town") String town,
                                                   @Param("rgion") String rgion);

    @Query("SELECT b FROM UserEntity b WHERE lower(b.hospital.town) LIKE %:s% OR lower(b.hospital.region) like %:s%")
    List<UserEntity> findAllWhereTownOrRegionLike(@Param("s") String s);
}
