package com.example.demo.core.database.repository;

import com.example.demo.core.database.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority,Long> {

    Optional<Authority> findByNameEquals(String s);

}
