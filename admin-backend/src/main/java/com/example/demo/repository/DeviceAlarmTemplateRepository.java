package com.example.demo.repository;

import com.example.demo.model.DeviceAlarmTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceAlarmTemplateRepository extends MongoRepository<DeviceAlarmTemplate, String> {
    @Query("{active: true}")
    List<DeviceAlarmTemplate> findActive();

    @Query("{active: true}")
    Page<DeviceAlarmTemplate> findActive(Pageable pageable);
}
