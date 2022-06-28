package com.example.demo.repository;

import com.example.demo.model.Device;
import com.example.demo.model.RealEstate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {

    @Query("select d from Device d where d.isActive=true and d.realEstate.id=:id")
    List<Device> getForRealEstate(Integer id);

    @Query("select d from Device d left outer join d.realEstate where d.isActive=true and lower(d.name)=:regex")
    Page<Device> getAllPageable(String regex, Pageable pageable);

      List<Device> getAllByRealEstateIn(List<RealEstate> realEstates);
}
