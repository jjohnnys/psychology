package br.com.jjohnys.psychological_care;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import br.com.jjohnys.psychological_care.patient.domain.Contact;
import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.Plan;
import br.com.jjohnys.psychological_care.patient.domain.Responsible;
import br.com.jjohnys.psychological_care.patient.domain.enums.Gender;
import br.com.jjohnys.psychological_care.patient.gateways.ContactRepository;
import br.com.jjohnys.psychological_care.patient.gateways.PatientRepository;
import br.com.jjohnys.psychological_care.patient.gateways.PlanRepository;
import br.com.jjohnys.psychological_care.patient.infrastructure.jdbc.ResponsibleJDBC;
import br.com.jjohnys.psychological_care.psychological_support.Support;
import br.com.jjohnys.psychological_care.psychological_support.repository.SupportRepository;
import br.com.jjohnys.psychological_care.utils.DateUtils;

@SpringBootTest
@SpringJUnitConfig
public class PsychologicalSupportTest {

    @Autowired
    private PatientRepository patientRepository; 

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private SupportRepository supportRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ResponsibleJDBC responsibleJDBC;

    

    @Test
    public void jdbcPlanTest() {
        planRepository.insertPlan(new Plan(UUID.randomUUID().toString(), "Anual", 10000));
        Plan plan = planRepository.getPlanByType("Anual");
        assertEquals("Anual", plan.getType(), "Plan ok");
    }

    @Test
    public void jdbcPatientTest() {
        Plan plan = planRepository.getPlanByType("top");

        patientRepository.insertPatient(new Patient(UUID.randomUUID().toString(), "Ikki", "985.698.497-22", "99.689.695-5", DateUtils.stringDateToLocalDate("1987-04-15"), plan, "PosGraduado", Gender.MALE, "Gercia, 100", "Forte"));
        List<Patient> patients = patientRepository.findPatientByName("kk");
        Contact contactIkki = new Contact(UUID.randomUUID().toString(), null, "ikki@mail.com", "85 98745", null, patients.get(0).getId());
        contactRepository.insertContact(contactIkki);
        List<Contact> contactCreated = contactRepository.getContactById(contactIkki.getId());
        assertEquals("Ikki", patients.get(0).getName(), "Name Ok");
        assertEquals(patients.get(0).getId(), contactCreated.get(0).getPatientId(), "Name Ok");

        patients.get(0).setCpf("985.698.497-44");
        patientRepository.updatePatient(patients.get(0));

        Patient patienteUpdated = patientRepository.findPatientById(patients.get(0).getId());
        assertEquals("985.698.497-44", patienteUpdated.getCpf(), "Update Ok");

        Patient minor = new Patient(UUID.randomUUID().toString(), "Esmeralda", "777.555.444-33", "55.444.333-2", DateUtils.stringDateToLocalDate("1992-02-22"), plan, "PosGraduado", Gender.FEMALE, "Gercia", "Gosta do Ikki");
        patientRepository.insertPatient(minor);
        Patient minorInserted = patientRepository.findPatientById(minor.getId());
        Contact contactEsmeralda = new Contact(UUID.randomUUID().toString(), null, "esmeralda@mail.com", "85 98744", null, minorInserted.getId());
        contactRepository.insertContact(contactEsmeralda);
        Contact contactParanteOfEsmeralda = new Contact(UUID.randomUUID().toString(), "Ikki", "ikki@mail.com", "85 98745", "Love", minorInserted.getId());
        contactRepository.insertContact(contactParanteOfEsmeralda);
        contactParanteOfEsmeralda.setParentage("Love forever");
        contactRepository.updateContact(contactParanteOfEsmeralda);
        //assertEquals("985.698.497-44", minorInserted.getResponsible().getCpf(), "Menor ok");
    }

    @Test
    public void jdbcSuportTest() {

        List<Patient> patients = patientRepository.findPatientByName("Seiya");
        Support support = new Support(UUID.randomUUID().toString(), patients.get(0), LocalDateTime.now(), "Este paciente eh bom");
        supportRepository.insertSupport(support);
        List<Support> supportInserted = supportRepository.getSupportByPatient("Seiya");
        assertEquals("Seiya de Pagasus", supportInserted.get(0).getPatient().getName(), "Suport insert ok");
        
        Support supportSeiya = new Support(UUID.randomUUID().toString(), patients.get(0), LocalDateTime.of(2023, 05, 10, 11, 10), "Este paciente eh bom");       
        
        supportRepository.insertSupport(supportSeiya);
        List<Support> supportSeiyaInserted = supportRepository.getSupportByDate(LocalDate.of(2023, 05, 10));
        assertEquals("Seiya de Pagasus", supportSeiyaInserted.get(0).getPatient().getName(), "Suport insert ok");

    }

    @Test
    public void jdbcResponsibleTest() {

        Patient patient = patientRepository.findPatientByName("Seiya de Pagasus").get(0);        
        responsibleJDBC.insertResponsible(new Responsible(UUID.randomUUID().toString(), "Saory Kido", "877+985+985-99", "44.888.999-5", DateUtils.stringDateToLocalDate("1988-05-14"), patient));
        Responsible responsible = responsibleJDBC.findResponsiblesByPatientName("Seiya de Pagasus").get(0);
        assertEquals("Saory Kido", responsible.getName(), "Responsavel OK");




    }


    
}
