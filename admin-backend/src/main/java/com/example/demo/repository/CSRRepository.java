package com.example.demo.repository;

import com.example.demo.model.CSR;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CSRRepository extends JpaRepository<CSR, Integer> {


    Page<CSR> findAllByIsActive(Pageable pageable);
}
