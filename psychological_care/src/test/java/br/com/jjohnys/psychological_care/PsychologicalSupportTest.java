package br.com.jjohnys.psychological_care;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.repository.PatientRepository;

@SpringBootTest
@SpringJUnitConfig
public class PsychologicalSupportTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void insertAndSelectPatient() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");        
        patientRepository.insertPatient(new Patient(null, "Ikki", "985.698.497-22", "99.689.695-5", LocalDate.parse("1987-04-15", formatter), "ikki@cdz.com", 100, "Forte"););
        Patient patient = patientRepository.findPatientByName("Ikki");

        assertEquals("Ikki", patient.getName(), "Name Ok");

    }


    
}
