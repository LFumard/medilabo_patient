package com.LFumard.medilabo.service;

import com.LFumard.medilabo.model.Patient;
import com.LFumard.medilabo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServieImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void save(Patient patient) {
        Patient patientToSave = new Patient();
        patientToSave.setAddress(patient.getAddress());
        patientToSave.setBirthdate(patient.getBirthdate());
        patientToSave.setSex(patient.getSex());
        patientToSave.setPhonenumber(patient.getPhonenumber());
        patientToSave.setFirstName(patient.getFirstName());
        patientToSave.setLastName(patient.getLastName());
        patientRepository.save(patientToSave);
    }

    @Override
    public Patient getPatient(Long patientId) {
        return patientRepository.findById(patientId).orElse(null);
    }

    @Override
    public void updatePatient(Patient patient, Long patientId) {
        Patient patientToSave = patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient doesn't exist"));
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public Patient findById(Long patientId) {
        return patientRepository.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Patient doesn't exist"));
    }

    @Override
    public void deleteById(Long patientId) {
        Patient patientToDel = patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient doesn't exist"));
    }
}
