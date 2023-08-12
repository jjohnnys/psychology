package br.com.jjohnys.psychological_care;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.Plan;
import br.com.jjohnys.psychological_care.patient.domain.Support;
import br.com.jjohnys.psychological_care.patient.repository.PatientRepository;
import br.com.jjohnys.psychological_care.patient.repository.PlanRepository;
import br.com.jjohnys.psychological_care.patient.repository.SupportRepository;

@SpringBootTest
@SpringJUnitConfig
public class PsychologicalSupportTest {

    @Autowired
    private PatientRepository patientRepository; 

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private SupportRepository supportRepository;

    @Test
    public void jdbcPlanTest() {
        planRepository.insertPlan(new Plan("plan_anual", "Anual", 10000));
        Plan plan = planRepository.getPlanByType("Anual");
        assertEquals("Anual", plan.getType(), "Plan ok");
    }

    @Test
    public void jdbcPatientTest() {
        Plan plan = planRepository.getPlanByType("top");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");        
        patientRepository.insertPatient(new Patient(null, "Ikki", "985.698.497-22", "99.689.695-5", LocalDate.parse("1987-04-15", formatter), "ikki@cdz.com", plan, "Forte"));
        Patient patient = patientRepository.findPatientByName("kk");
        assertEquals("Ikki", patient.getName(), "Name Ok");

        patient.setCpf("985.698.497-44");
        patientRepository.updatePatient(patient);

        Patient patienteUpdated = patientRepository.findPatientById(patient.getId());
        assertEquals("985.698.497-44", patienteUpdated.getCpf(), "Update Ok");

    }

    @Test
    public void jdbcSuportTest() {

        Patient patient = patientRepository.findPatientByName("Seiya");
        Support support = new Support(null, patient, LocalDateTime.now(), "Este paciente eh bom");
        supportRepository.insertSupport(support);
        List<Support> supportInserted = supportRepository.getSupportByPatient("Seiya");
        assertEquals("Seiya de Pagasus", supportInserted.get(0).getPatient().getName(), "Suport insert ok");
        
        Support supportSeiya = new Support(null, patient, LocalDateTime.of(2023, 05, 10, 11, 10), "Este paciente eh bom");       
        
        supportRepository.insertSupport(supportSeiya);
        List<Support> supportSeiyaInserted = supportRepository.getSupportByDate(LocalDate.of(2023, 05, 10));
        assertEquals("Seiya de Pagasus", supportSeiyaInserted.get(0).getPatient().getName(), "Suport insert ok");



    }


    
}
