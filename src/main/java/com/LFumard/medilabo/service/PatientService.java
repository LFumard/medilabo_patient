package com.LFumard.medilabo.service;

import com.LFumard.medilabo.model.Patient;

import java.util.List;

public interface PatientService {

    void addPatient(Patient patient);

    Patient getPatient(Long patientId);

    void updatePatient(Long id, Patient patient);

    List<Patient> findAll();

    Patient findById(Long patientId);

    void deleteById(Long patientId);
}
