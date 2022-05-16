package com.example.demo.repository;

import com.example.demo.model.CertificateData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificateDataRepository extends JpaRepository<CertificateData, Integer> {

    @Query("select c from CertificateData c where c.id = :serialNumber")
    Optional<CertificateData> readBySerialNumber(@Param(value = "serialNumber") Integer serialNumber);

    @Query("select c from CertificateData c where c.id = :serialNumber and c.revocation.id is null")
    Optional<CertificateData> readBySerialNumberNonCancelled(@Param(value = "serialNumber") Integer serialNumber);

    @Query("select c from CertificateData c where c.revocation.id is null")
    Page<CertificateData> readNonInvalidated(Pageable pageable);
}
