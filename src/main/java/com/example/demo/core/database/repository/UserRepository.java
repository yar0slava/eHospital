package com.example.demo.core.database.repository;

import com.example.demo.core.database.entity.HospitalEntity;
import com.example.demo.core.database.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends PagingAndSortingRepository<UserEntity,Long> {

    Iterable<UserEntity> findAll();

    List<UserEntity> findByHospital(HospitalEntity hospitalEntity);

    Page<UserEntity> findAll(Pageable pageable);

    Optional<UserEntity> findByEmail(String email);

}
