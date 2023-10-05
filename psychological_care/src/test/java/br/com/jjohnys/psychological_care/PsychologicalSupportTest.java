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
import br.com.jjohnys.psychological_care.patient.domain.Responsible;
import br.com.jjohnys.psychological_care.patient.domain.enums.Gender;
import br.com.jjohnys.psychological_care.patient.gateways.ContactRepository;
import br.com.jjohnys.psychological_care.patient.gateways.PatientRepository;
import br.com.jjohnys.psychological_care.patient.infrastructure.jdbc.PatientJDBC;
import br.com.jjohnys.psychological_care.patient.infrastructure.jdbc.ResponsibleJDBC;
import br.com.jjohnys.psychological_care.psychological_support.Support;
import br.com.jjohnys.psychological_care.psychological_support.repository.SupportRepository;
import br.com.jjohnys.psychological_care.utils.DateUtils;

@SpringBootTest
@SpringJUnitConfig
public class PsychologicalSupportTest {

    @Autowired
    private PatientJDBC patientJDBC; 

    @Autowired
    private SupportRepository supportRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ResponsibleJDBC responsibleJDBC;

   

    @Test
    public void jdbcPatientTest() {

        patientJDBC.insertPatient(new Patient(UUID.randomUUID().toString(), "Ikki", "985.698.497-22", "99.689.695-5", DateUtils.stringDateToLocalDate("1987-04-15"), 100, "PosGraduado", Gender.MALE, "Gercia, 100", "Forte"));
        List<Patient> patients = patientJDBC.findPatientByName("kk");
        Contact contactIkki = Contact.buildPatientContact(UUID.randomUUID().toString(), null, "ikki@mail.com", "85 98745", patients.get(0).getId());
        contactRepository.insertContact(contactIkki);
        List<Contact> contactCreated = contactRepository.getContactById(contactIkki.getId());
        assertEquals("Ikki", patients.get(0).getName(), "Name Ok");
        assertEquals(patients.get(0).getId(), contactCreated.get(0).getPatientId(), "Name Ok");

        patients.get(0).setCpf("985.698.497-44");
        patientJDBC.updatePatient(patients.get(0));

        Patient patienteUpdated = patientJDBC.findPatientById(patients.get(0).getId());
        assertEquals("985.698.497-44", patienteUpdated.getCpf(), "Update Ok");

        Patient minor = new Patient(UUID.randomUUID().toString(), "Esmeralda", "777.555.444-33", "55.444.333-2", DateUtils.stringDateToLocalDate("1992-02-22"), 100, "PosGraduado", Gender.FEMALE, "Gercia", "Gosta do Ikki");
        patientJDBC.insertPatient(minor);
        Patient minorInserted = patientJDBC.findPatientById(minor.getId());
        Contact contactEsmeralda = Contact.buildPatientContact(UUID.randomUUID().toString(), null, "esmeralda@mail.com", "85 98744", minorInserted.getId());
        contactRepository.insertContact(contactEsmeralda);
        Contact contactParanteOfEsmeralda = Contact.buildPatientContact(UUID.randomUUID().toString(), "Ikki", "ikki@mail.com", "85 98745", minorInserted.getId());
        contactRepository.insertContact(contactParanteOfEsmeralda);
        contactRepository.updateContact(contactParanteOfEsmeralda);
        //assertEquals("985.698.497-44", minorInserted.getResponsible().getCpf(), "Menor ok");
    }

    @Test
    public void jdbcSuportTest() {

        List<Patient> patients = patientJDBC.findPatientByName("Seiya");
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

        Patient patient = patientJDBC.findPatientByName("Seiya de Pagasus").get(0);        
        responsibleJDBC.insertResponsible(new Responsible(UUID.randomUUID().toString(), "Saory Kido", "877.985.985-99", "44.888.999-5", DateUtils.stringDateToLocalDate("1988-05-14"), "Ah...", patient));
        Responsible responsible = responsibleJDBC.findResponsiblesByPatientName("Seiya de Pagasus").get(0);
        assertEquals("Saory Kido", responsible.getName(), "Responsavel OK");




    }


    
}
