package com.example.demo.repository;

import com.example.demo.model.CertificateData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface CertificateDataRepository extends JpaRepository<CertificateData, Integer> {

    @Query("select c from CertificateData c where c.serialNumber = :serialNumber")
    Optional<CertificateData> readBySerialNumber(@Param(value = "serialNumber")BigInteger serialNumber);

    @Query("select c from CertificateData c where c.serialNumber = :serialNumber and c.isCancelled = false")
    Optional<CertificateData> readBySerialNumberNonCancelled(@Param(value = "serialNumber") BigInteger serialNumber);
}
