package br.com.jjohnys.psychological_care;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.util.Assert;

import br.com.jjohnys.psychological_care.domain.Patient;
import br.com.jjohnys.psychological_care.repository.PatientRepository;

@SpringBootTest
@SpringJUnitConfig
public class PsychologicalSupportTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void insertAndSelectPatient() {

        patientRepository.insertPatient("Ikki_ID", "Ikki", "985.698.497-22", "99.689.695-5", "ikki@cdz.com", "1987-04-15", 100, "Forte");
        Patient patient = patientRepository.findPatientByName("Ikki");

        assertEquals("Ikki", patient.getName(), "Name Ok");

    }
    
}
