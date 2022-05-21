package com.example.demo.repository;

import com.example.demo.model.CSR;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface CSRRepository extends JpaRepository<CSR, Integer> {

    Page<CSR> findByIsActiveTrueAndVerifiedTrue(Pageable pageable);

    @Lock(LockModeType.OPTIMISTIC)
    Optional<CSR> findByIdAndIsActiveTrue(Integer id);
}
