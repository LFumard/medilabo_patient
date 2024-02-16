package com.LFumard.medilabo.controller;

import com.LFumard.medilabo.model.Patient;
import com.LFumard.medilabo.repository.PatientRepository;
import com.LFumard.medilabo.service.PatientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    private static final Logger logger = LogManager.getLogger("PatientController");


    @GetMapping("/patient/list")
    public String home(Model model) {
        logger.info("New request Mapping : show all Patients");
        model.addAttribute("patientsList", patientRepository.findAll());
        return "patient/list";
    }

    @GetMapping("/patient/add")
    public String addPatient(Model model) {
        logger.info("New request Mapping : show form to add new Patient");
        model.addAttribute("patient", new Patient());
        return "patient/add";
    }

    @PostMapping("/patient/validate")
    public String validate(@Valid Patient patient, BindingResult result, Model model) {

        if(result.hasErrors()) {
            logger.error("New request Post Mapping : ERROR add new patient : " + patient);
            return "patient/add";
        }
            patientService.save(patient);
            model.addAttribute("patientsList", patientService.findAll());
            logger.info("New request Post Mapping : Add new Patient : " + patient);
            return "redirect:/patient/list";
    }

    @GetMapping("/patient/update/{patientId}")
    public String showUpdatePatientForm(@PathVariable("patientId") Long patientId, Model model) {

        model.addAttribute("bidList",patientService.findById(patientId));
        logger.info("New request Get Mapping : update patient : " + patientId);
        return "patient/update";
    }

    @PostMapping("/patient/update/{patientId}")
    public String updatePatient(@PathVariable("patientId") Long patientId, Patient patient,
                                BindingResult result, Model model) {

        if (result.hasErrors()) {
            logger.error("New request Post Mapping : ERROR update patient : " + patient);
            return "patient/update";
        }
        logger.info("New request Post Mapping : update patient : " + patient);
        patientService.updatePatient(patient, patientId);
        model.addAttribute("patient", patientService.findAll());
        return "redirect:/patient/list";
    }

    @DeleteMapping(value = "/Patient/delete/{patientId}")
    public void deletePatientById(@PathVariable Long patientId) {
        patientService.deleteById(patientId);
        logger.info("New request Get Mapping : delete patient : " + patientId);

    }
}
