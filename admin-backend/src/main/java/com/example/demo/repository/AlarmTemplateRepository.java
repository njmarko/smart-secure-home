package com.example.demo.repository;

import com.example.demo.model.AlarmTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmTemplateRepository extends MongoRepository<AlarmTemplate, String> {
    @Query("{active: true}")
    List<AlarmTemplate> findActive();

    @Query("{active: true}")
    Page<AlarmTemplate> findActive(Pageable pageable);
}
