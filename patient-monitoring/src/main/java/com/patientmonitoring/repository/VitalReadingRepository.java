package com.patientmonitoring.repository;

import com.patientmonitoring.model.VitalReading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VitalReadingRepository extends JpaRepository<VitalReading, Long> {

    List<VitalReading> findByPatientId(Long patientId);
}