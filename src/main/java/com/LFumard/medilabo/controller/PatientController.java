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

    @GetMapping("/list") // OK
    public List<Patient> getAllPatients() {

        logger.info("New request GetMapping : show all Patients");
        return patientService.findAll();
    }

    @DeleteMapping("/delete/{id}") // OK
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {

        logger.info("New request DeleteMapping : deletePatient : " + id);
        patientService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //@PostMapping(path = "/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    //@PostMapping(path = "/validate", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  /*  @PostMapping(path = "/validate")
    //public RedirectView savePatientUri(@ModelAttribute Patient newPatient) {
    public RedirectView savePatientUri(@RequestBody Patient newPatient) {
        patientService.addPatient(newPatient);
        return new RedirectView("/patient/all");
    }*/

    @PostMapping(path = "/validate") // OK
    public void savePatientUri(@RequestBody Patient newPatient) {

        logger.info("New request PostMapping : savePatientUri : " + newPatient.toString());
        patientService.addPatient(newPatient);
    }

    @PutMapping("/update/{id}") // OK
    public void updatePatient(@PathVariable Long id,@RequestBody @Valid Patient patient) {

        logger.info("New request PutMapping : updatePatient : " + patient.toString());
        patientService.updatePatient(id, patient);
    }

/*    @GetMapping("/update/{id}")
    public Patient showUpdateForm(@PathVariable Long id) {

        logger.info("New request GetMapping : show Update Form");
        return patientService.findById(id);
    }*/
    @GetMapping("/{id}") // OK
    public Patient showPatient(@PathVariable Long id) {

        logger.info("New request GetMapping : showPatient");
        return patientService.findById(id);
    }
/*    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void savePatient(@RequestBody Patient newPatient) {

        logger.info("New request PostMapping : savePatient : " + newPatient.toString());
        patientService.addPatient(newPatient);
    }*/
}