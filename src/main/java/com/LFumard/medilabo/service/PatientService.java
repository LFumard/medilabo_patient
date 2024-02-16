package com.LFumard.medilabo.service;

import com.LFumard.medilabo.model.Patient;

import java.util.List;

public interface PatientService {

    void save(Patient patient);

    Patient getPatient(Long patientId);

    void updatePatient(Patient patient, Long patientId);

    List<Patient> findAll();

    Patient findById(Long patientId);

    void deleteById(Long patientId);
}
