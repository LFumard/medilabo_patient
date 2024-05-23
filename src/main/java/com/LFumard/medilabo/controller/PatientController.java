package com.LFumard.medilabo.controller;

import com.LFumard.medilabo.model.Patient;
import com.LFumard.medilabo.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;

    @GetMapping("/list")
    public List<Patient> getAllPatients() {

        logger.info("New request GetMapping : show all Patients");
        return patientService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {

        logger.info("New request DeleteMapping : deletePatient : " + id);
        patientService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping(path = "/validate")
    public void savePatientUri(@RequestBody Patient newPatient) {

        logger.info("New request PostMapping : savePatientUri : " + newPatient.toString());
        patientService.addPatient(newPatient);
    }

    @PutMapping("/update/{id}")
    public void updatePatient(@PathVariable Long id,@RequestBody @Valid Patient patient) {

        logger.info("New request PutMapping : updatePatient : " + patient.toString());
        patientService.updatePatient(id, patient);
    }


    @GetMapping("/{id}")
    public Patient showPatient(@PathVariable Long id) {

        logger.info("New request GetMapping : showPatient");
        return patientService.findById(id);
    }

}