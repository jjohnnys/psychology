package br.com.jjohnys.psychological_care;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.jjohnys.psychological_care.patient.application.dto.ContactDTO;
import br.com.jjohnys.psychological_care.patient.application.dto.PatientDTO;
import br.com.jjohnys.psychological_care.patient.domain.Contact;
import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.enums.Gender;
import br.com.jjohnys.psychological_care.patient.gateways.PatientRepository;
import br.com.jjohnys.psychological_care.utils.DateUtils;

@SpringBootTest
public class PatienteUseCaseTest {

    @Autowired
    private PatientRepository patientRepository;    

    @Test

    public void createPatientSucecss() {

        Patient newPatient = Constants.newPatient();
        List<Contact> contacts = new ArrayList<Contact>();
        contacts.add(Constants.newContact(newPatient.getId()));
        patientRepository.insertPatient(newPatient, contacts, null, null);

        Patient patient = patientRepository.findPatientById(newPatient.getId());
        
        List<ContactDTO> contactsDTO = new ArrayList<ContactDTO>();
        contactsDTO.add(new ContactDTO(null, "ikki@mail.com", "85 98745"));
        PatientDTO patientDTO = new PatientDTO(null, "Ikki", "985.698.497-22", "99.689.695-5", DateUtils.stringDateToLocalDate("1987-04-15"), 100, "PosGraduado", Gender.MALE.getDescription(), "Ilha dificil", null, "Muito forte", contactsDTO);

        assertEquals(patient.getCpf(), "985.698.497-22", "Paciente criado");
        assertEquals(patient.getContacts().stream().findFirst().get().getEmail(), "ikki@mail.com", "Contato criado");
    }


    
    
}
