package com.patientmonitoring.controller;

import com.patientmonitoring.model.Patient;
import com.patientmonitoring.repository.PatientRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientMonitoringController {

    private final PatientRepository patientRepository;

    public PatientMonitoringController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @PostMapping
    public Patient addPatient(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }

    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable Long id,
                                 @RequestBody Patient patient) {

        Patient existingPatient = patientRepository.findById(id).orElse(null);

        if (existingPatient == null) {
            return null;
        }

        existingPatient.setName(patient.getName());
        existingPatient.setAge(patient.getAge());
        existingPatient.setGender(patient.getGender());
        existingPatient.setPhone(patient.getPhone());

        return patientRepository.save(existingPatient);
    }

    @DeleteMapping("/{id}")
    public String deletePatient(@PathVariable Long id) {

        if (!patientRepository.existsById(id)) {
            return "Patient not found";
        }

        patientRepository.deleteById(id);
        return "Patient deleted successfully";
    }
    @GetMapping("/{id}")
public Patient getPatientById(@PathVariable Long id) {
    return patientRepository.findById(id).orElse(null);
}
}