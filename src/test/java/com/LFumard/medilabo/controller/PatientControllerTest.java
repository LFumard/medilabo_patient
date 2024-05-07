package com.LFumard.medilabo.controller;

import com.LFumard.medilabo.model.Patient;
import com.LFumard.medilabo.service.PatientServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.RequestEntity.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(PatientController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientServiceImpl patientService;

    private List<Patient> patientList;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void testGetAllPatients() throws Exception {

        patientList = new ArrayList<>();
        patientList.add(new Patient(1L, "patientFirstName1", "patientLastName1", LocalDate.of(1971,1,1), "M", "patientAddress1", "1111111111"));
        patientList.add(new Patient(2L, "patientFirstName2", "patientLastName2", LocalDate.of(1972,2,2), "F", "patientAddress2", "2222222222"));
        given(patientService.findAll()).willReturn(patientList);

        mockMvc.perform(get("/patient/list"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect((MockMvcResultMatchers.jsonPath("$[0].firstName").value("patientFirstName1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("patientLastName1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].birthdate").value("1971-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].sex").value("M"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address").value("patientAddress1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].phoneNumber").value("1111111111"))

                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect((MockMvcResultMatchers.jsonPath("$[1].firstName").value("patientFirstName2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName").value("patientLastName2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].birthdate").value("1972-02-02"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].sex").value("F"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].address").value("patientAddress2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].phoneNumber").value("2222222222"));
    }

    @Test
    public void testShowPatient() throws Exception {

        patientList = new ArrayList<>();
        patientList.add(new Patient(1L, "patientFirstName1", "patientLastName1", LocalDate.of(1971,1,1), "M", "patientAddress1", "1111111111"));
        patientList.add(new Patient(2L, "patientFirstName2", "patientLastName2", LocalDate.of(1972,2,2), "F", "patientAddress2", "2222222222"));

        given(patientService.findById(1L)).willReturn(patientList.get(0));

        mockMvc.perform(get("/patient/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("patientFirstName1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("patientLastName1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthdate").value("1971-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sex").value("M"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("patientAddress1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("1111111111"));
    }

    @Test
    public void testDeletePatient() throws Exception {
        Long id = 1L;
        doNothing().when(patientService).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/patient/delete/1"))
                        .andExpect(status().isNoContent());

        verify(patientService, times(1)).deleteById(id);
    }

    @Test
    public void testSavePatientUri() throws Exception {

        Patient patientToAdd = new Patient(1L, "patientFirstName1", "patientLastName1", LocalDate.of(1971,1,1), "M", "patientAddress1", "1111111111");
        //patientService.addPatient(patientToAdd);
        String strContent = objectMapper.writeValueAsString(patientToAdd);
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/patient/validate")
                        .contentType(APPLICATION_JSON)
                        .content(strContent))
                .andExpect(status().isOk());

        verify(patientService, times(1)).addPatient(any());
    }

    @Test
    public void testUpdatePatient() throws Exception {

        Patient patientToUpdate = new Patient(1L, "patientFirstName1", "patientLastName1Update", LocalDate.of(1971,1,1), "M", "patientAddress1", "1111111111");
        //patientService.addPatient(patientToAdd);
        String strContent = objectMapper.writeValueAsString(patientToUpdate);
        doNothing().when(patientService).updatePatient(1L, patientToUpdate);
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/patient/update/1")
                        .contentType(APPLICATION_JSON)
                        .content(strContent))
                .andExpect(status().isOk());

        verify(patientService, times(1)).updatePatient(any(), any());

    }
}
