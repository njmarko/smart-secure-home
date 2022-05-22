package com.example.demo.repository;

import com.example.demo.model.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RealEstateRepository extends JpaRepository<RealEstate, Integer> {

    @Query("select re from RealEstate re where re.isActive=true")
    List<RealEstate> read();
}
