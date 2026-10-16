
package com.patientmonitoring.controller;

import com.patientmonitoring.model.VitalReading;
import com.patientmonitoring.repository.VitalReadingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vitals")
public class VitalReadingController {

    private final VitalReadingRepository vitalReadingRepository;

    public VitalReadingController(VitalReadingRepository vitalReadingRepository) {
        this.vitalReadingRepository = vitalReadingRepository;
    }

    @GetMapping
    public List<VitalReading> getAllVitals() {
        return vitalReadingRepository.findAll();
    }

    @PostMapping
    public VitalReading addVital(@RequestBody VitalReading vitalReading) {
        return vitalReadingRepository.save(vitalReading);
    }

    @GetMapping("/patient/{patientId}")
    public List<VitalReading> getVitalsByPatient(@PathVariable Long patientId) {
        return vitalReadingRepository.findByPatientId(patientId);
    }

    @GetMapping("/patient/{patientId}/latest")
    public VitalReading getLatestVital(@PathVariable Long patientId) {
        List<VitalReading> readings =
                vitalReadingRepository.findByPatientId(patientId);

        if (readings.isEmpty()) {
            return null;
        }

        return readings.get(readings.size() - 1);
    }
    @GetMapping("/patient/{patientId}/status")
public String getPatientStatus(@PathVariable Long patientId) {

    List<VitalReading> readings =
            vitalReadingRepository.findByPatientId(patientId);

    if (readings.isEmpty()) {
        return "No vital readings found for this patient";
    }

    VitalReading latest = readings.get(readings.size() - 1);

    boolean alert = false;
    String message = "";

    // Demo project reference limits only
    if (latest.getHeartRate() < 60 || latest.getHeartRate() > 100) {
        alert = true;
        message += "Heart rate alert. ";
    }

    if (latest.getTemperature() < 36.0 || latest.getTemperature() > 37.5) {
        alert = true;
        message += "Temperature alert. ";
    }

    if (latest.getSpo2() < 95) {
        alert = true;
        message += "SpO2 alert. ";
    }

    if (latest.getRespiratoryRate() < 12 ||
        latest.getRespiratoryRate() > 20) {
        alert = true;
        message += "Respiratory rate alert. ";
    }

    if (alert) {
        return "ALERT: " + message;
    }

    return "NORMAL";
}
}