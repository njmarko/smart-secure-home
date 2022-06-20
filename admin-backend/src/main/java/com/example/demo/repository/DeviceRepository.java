package com.example.demo.repository;

import com.example.demo.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {

    @Query("select d from Device d where d.isActive=true and d.realEstate.id=:id")
    List<Device> getForRealEstate(Integer id);
}
