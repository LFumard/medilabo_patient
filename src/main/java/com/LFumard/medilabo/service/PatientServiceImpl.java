package com.LFumard.medilabo.service;

import com.LFumard.medilabo.model.Patient;
import com.LFumard.medilabo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void addPatient(Patient patient) {
        Patient patientToSave = new Patient();
        patientToSave.setAddress(patient.getAddress());
        patientToSave.setBirthdate(patient.getBirthdate());
        patientToSave.setSex(patient.getSex());
        patientToSave.setPhoneNumber(patient.getPhoneNumber());
        patientToSave.setFirstName(patient.getFirstName());
        patientToSave.setLastName(patient.getLastName());
        patientRepository.save(patientToSave);
    }

    @Override
    public void updatePatient(Long patientId, Patient patient) {
        Patient patientToSave = patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient doesn't exist"));
        patientRepository.save(patient);
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public Patient findById(Long patientId) {
        return patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient doesn't exist"));
    }

    @Override
    public void deleteById(Long patientId) {
        Patient patientToDel = patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient doesn't exist"));
        patientRepository.delete(patientToDel);
    }
}
