package com.LFumard.medilabo.service;

import com.LFumard.medilabo.model.Patient;
import com.LFumard.medilabo.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    private List<Patient> patientList;

   @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);
        patientList = new ArrayList<>();
        patientList.add(new Patient(1L, "patientFirstName1", "patientLastName1", LocalDate.of(1971,1,1), "M", "patientAddress1", "1111111111"));
        patientList.add(new Patient(2L, "patientFirstName2", "patientLastName2", LocalDate.of(1972,2,2), "M", "patientAddress2", "2222222222"));
        patientList.add(new Patient(3L, "patientFirstName3", "patientLastName3", LocalDate.of(1973,3,3), "M", "patientAddress3", "3333333333"));

    }
    @Test
    public void testGetPatient() {

        Long id = 1L;
        Patient expectedPatient = new Patient(1L, "patientFirstName1", "patientLastName1", LocalDate.of(1971,1,1), "M", "patientAddress1", "1111111111");
        when(patientRepository.findById(id)).thenReturn(Optional.of(expectedPatient));

        Patient actualPatient = patientService.findById(id);

        assertNotNull(actualPatient);
        assertEquals(expectedPatient, actualPatient);
        verify(patientRepository, times(1)).findById(id);
    }

    @Test
    public void testGetPatient_WhenPatientNotExist() {

        Long id = 1L;
        //Patient expectedPatient = new Patient(1L, "patientFirstName1", "patientLastName1", LocalDate.of(1971,1,1), "M", "patientAddress1", "1111111111");
        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            patientService.findById(id);
        });
    }
    @Test
    public void testFindAll() {

        when(patientRepository.findAll()).thenReturn(patientList);

        List<Patient> actualPatientList = patientService.findAll();

        assertNotNull(actualPatientList);
        assertEquals(patientList.size(), actualPatientList.size());
        assertEquals(patientList, actualPatientList);
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    public void testAddPatient() {

        Patient patientToAdd = new Patient(10L, "patientFirstName1", "patientLastName1", LocalDate.of(1971,1,1), "M", "patientAddress1", "1111111111");

        patientService.addPatient(patientToAdd);

        verify(patientRepository, times(1)).save(any());
    }

    @Test
    public void testDeleteById() {

        Long id = 1L;
        Patient patientToDel = new Patient(id, "patientFirstName1", "patientLastName1", LocalDate.of(1971,1,1), "M", "patientAddress1", "1111111111");
        when(patientRepository.findById(id)).thenReturn(Optional.of(patientToDel));

        patientService.deleteById(id);

        verify(patientRepository, times(1)).delete(patientToDel);
    }

    @Test
    public void testDeleteById_WhenPatientNotExist() {

        Long id = 1L;
        Patient patientToDel = new Patient(id, "patientFirstName1", "patientLastName1", LocalDate.of(1971,1,1), "M", "patientAddress1", "1111111111");
        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            patientService.deleteById(id);
        });
    }
    @Test
    public void testUpdatePatient() {

        Patient existingPatient = new Patient(10L, "patientFirstName1", "patientLastName1", LocalDate.of(1971,1,1), "M", "patientAddress1", "1111111111");
        when(patientRepository.findById(existingPatient.getId())).thenReturn(Optional.of(existingPatient));
        Patient updatedPatient = new Patient(100L, "patientFirstName1Updated", "patientLastName1", LocalDate.of(1971,1,1), "M", "patientAddress1", "1111111111");

        patientService.updatePatient(10L, updatedPatient);

        verify(patientRepository, times(1)).save(any());
    }

    @Test
    public void testUpdatePatient_WhenPatientNotExist() {

        Long id = 1L;
        Patient updatedPatient = new Patient(10L, "patientFirstName1", "patientLastName1", LocalDate.of(1971,1,1), "M", "patientAddress1", "1111111111");
        when(patientRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            patientService.updatePatient(10L, updatedPatient);
        });
    }
}
