package com.example.demo.core.database.repository;

import com.example.demo.core.database.entity.HospitalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository extends JpaRepository<HospitalEntity,Long> {

    List<HospitalEntity> findAll();

    Optional<HospitalEntity> findById(long i);

    Optional<HospitalEntity> findByCodeHospital(String s);

    @Query("SELECT b FROM HospitalEntity b WHERE lower(b.region) LIKE %:search%")
    List<HospitalEntity> findAllWhereRegionLike(@Param("search") String search);

    @Query("SELECT b FROM HospitalEntity b WHERE lower(b.town) LIKE %:search%")
    List<HospitalEntity> findAllWhereTownLike(@Param("search") String search);

    @Query("SELECT b FROM HospitalEntity b WHERE lower(b.town) LIKE %:search% AND lower(b.region) LIKE %:search1%")
    List<HospitalEntity> findAllWhereTownLikeAndRegionLike(@Param("search") String search,@Param("search1") String search1);

    @Query("SELECT b FROM HospitalEntity b WHERE lower(b.name) LIKE %:search% OR lower(b.town) LIKE %:search% OR lower(b.region) LIKE %:search%")
    List<HospitalEntity> findAllWhereNameLikeOrTownLikeOrRegionLike(@Param("search") String search);
}
