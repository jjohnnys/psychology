package br.com.jjohnys.psychological_care;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;
import br.com.jjohnys.psychological_care.patient.application.dto.ContactDTO;
import br.com.jjohnys.psychological_care.patient.application.dto.PatientDTO;
import br.com.jjohnys.psychological_care.patient.application.usecases.CreatePatientInterector;
import br.com.jjohnys.psychological_care.patient.application.usecases.UpdatePatientInterector;
import br.com.jjohnys.psychological_care.patient.domain.Contact;
import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.Responsible;
import br.com.jjohnys.psychological_care.patient.domain.enums.Gender;
import br.com.jjohnys.psychological_care.patient.gateways.PatientRepository;
import br.com.jjohnys.psychological_care.utils.DateUtils;

@SpringBootTest
public class PatienteUseCaseTest {

    @Autowired
    private CreatePatientInterector createPatientInterector;   
    @Autowired
    private UpdatePatientInterector updatePatientInterector;
    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void createPatientSuccess() {
      
        List<ContactDTO> contactsDTO = new ArrayList<ContactDTO>();
        contactsDTO.add(new ContactDTO(null, "ikki@mail.com", "85 98745"));
        PatientDTO patientDTO = new PatientDTO(null, "Ikki", "985.698.497-22", "99.689.695-5", DateUtils.stringDateToLocalDate("1987-04-15"), 100, "PosGraduado", Gender.MALE.getDescription(), "Ilha dificil", null, "Muito forte", contactsDTO);       
        createPatientInterector.createPatient(patientDTO);
        Patient patient = patientRepository.findPatientByCpf("985.698.497-22");

        assertEquals(patient.getCpf(), "985.698.497-22", "Paciente criado");
        assertEquals(patient.getContacts().stream().findFirst().get().getEmail(), "ikki@mail.com", "Contato do paciente criado");        
    }

    @Test
    public void createPatientDuplicateCpfError() {

        List<ContactDTO> contactsDTO = new ArrayList<ContactDTO>();
        contactsDTO.add(new ContactDTO(null, "miro@mail.com", "11 9875478"));
        PatientDTO patientDTO = new PatientDTO(null, "Miro", "777.887.888-11", "42.221.332-5", DateUtils.stringDateToLocalDate("1987-04-15"), 100, "PosGraduado", Gender.MALE.getDescription(), "Grecia", null, "Certeiro", contactsDTO);       
        createPatientInterector.createPatient(patientDTO);

        List<ContactDTO> contactsDuplicateCfp = new ArrayList<ContactDTO>();
        contactsDTO.add(new ContactDTO(null, "mu@mail.com", "11 8555254"));
        PatientDTO patientDuplicateCfp = new PatientDTO(null, "Mu", "777.887.888-11", "55.288.647-2", DateUtils.stringDateToLocalDate("1987-04-15"), 100, "PosGraduado", Gender.MALE.getDescription(), "Grecia", null, "Certeiro", contactsDuplicateCfp);       

        assertThrows(BusinessExceptions.class, () -> createPatientInterector.createPatient(patientDuplicateCfp), "Ja existe o CPF: 777.887.888-11 cadastrado");

    }

    @Test
    public void createPatientWithResponsibelSuccess() {
      
        List<ContactDTO> contactsResponsivelDTO = new ArrayList<ContactDTO>();
        contactsResponsivelDTO.add(new ContactDTO(null, "mestre@mail.com", "55 5544551"));
        List<PatientDTO.ResponsibleDTO> responsiblesDTO = new ArrayList<PatientDTO.ResponsibleDTO>();
        responsiblesDTO.add(new PatientDTO.ResponsibleDTO(null, "Mestre", "888.654.654-55", "44.663.445-2", DateUtils.stringDateToLocalDate("1980-04-15"), "mestre", contactsResponsivelDTO));
        
        List<ContactDTO> contactsDTO = new ArrayList<ContactDTO>();
        contactsDTO.add(new ContactDTO(null, "aldebaram@mail.com", "13 56987"));
        PatientDTO patientDTO = new PatientDTO(null, "Aldebaram", "453.222.333-44", "99.689.888-5", DateUtils.stringDateToLocalDate("1987-04-15"), 100, "PosGraduado", Gender.MALE.getDescription(), "Ilha dificil", responsiblesDTO, "Muito forte", contactsDTO);       
        
        createPatientInterector.createPatient(patientDTO);
        Patient patient = patientRepository.findPatientByCpf("453.222.333-44");

        assertEquals(patient.getResponsibles().stream().findFirst().get().getCpf(), "888.654.654-55", "Mestre criado");
        assertEquals(patient.getResponsibles().stream().findFirst().get().getContacts().stream().findFirst().get().getEmail(), "mestre@mail.com", "Contato do mestre criado");        
    }

    @Test
    public void getPatientSuccess() {
        Patient patient = patientRepository.findPatientByCpf("879.597.845-98");
        PatientDTO patientDTO = PatientDTO.cretePatientDTO(patient);        
        assertEquals(patientDTO.name(), "Seiya de Pagasus", "Encontrou o paciente!");        
        assertEquals(patientDTO.getResponsiblesDTO().stream().findFirst().get().name(), "Shun de Andromeda", "Encontrou o responssavel!");
    }

    @Test
    public void updatePacienteSuccess() {

        Patient patient = patientRepository.findPatientByCpf("879.597.845-98");
        Responsible responsible = patient.getResponsibles().stream().findAny().get();
        Contact responsibleContact = patient.getResponsibles().stream().findAny().get().getContacts().stream().findFirst().get();
        ContactDTO contactResponsibelDTO = new ContactDTO(responsibleContact.getId(), "shumdeandromeda@email.com", responsibleContact.getTelephone());
        List<ContactDTO> contactsResponsibelDTO = new ArrayList<ContactDTO>();
        contactsResponsibelDTO.add(contactResponsibelDTO);

        PatientDTO.ResponsibleDTO responsibleDTO = new PatientDTO.ResponsibleDTO(responsible.getId(), responsible.getName(), "777.548.852-47", responsible.getRg(), responsible.getDateBirth(), responsible.getParentege(), contactsResponsibelDTO);
        List<PatientDTO.ResponsibleDTO> responsibelsDTO = new ArrayList<PatientDTO.ResponsibleDTO>();
        responsibelsDTO.add(responsibleDTO);
        Contact contact = patient.getContacts().stream().findFirst().get();
        ContactDTO contactDTO = new ContactDTO(contact.getId(), "seiyadepegasus@email.com", contact.getTelephone());
        List<ContactDTO> contactsDTO = new ArrayList<ContactDTO>();
        contactsDTO.add(contactDTO);
        PatientDTO patientDTO = new PatientDTO(patient.getId(), patient.getName(), patient.getCpf(), patient.getRg(), patient.getDateBirth(), patient.getPrice(), "Lutador", patient.getGender().getDescription(), patient.getAddress(), responsibelsDTO, patient.getObservation(), contactsDTO);
        
        updatePatientInterector.updatePatient(patientDTO);

        Patient patientUpdated = patientRepository.findPatientByCpf("879.597.845-98");
        PatientDTO patientDTOUpdated = PatientDTO.cretePatientDTO(patientUpdated); 


        assertEquals(patientDTOUpdated.responsiblesDTO().stream().findFirst().get().contacts().stream().findFirst().get().email() , "shumdeandromeda@email.com", "Email do responsavel alterado!");
        assertEquals(patientDTOUpdated.contactsDTO().stream().findFirst().get().email() , "seiyadepegasus@email.com", "Email do paciente alterado!");

        assertEquals(patientDTOUpdated.responsiblesDTO().stream().findFirst().get().cpf() , "777.548.852-47", "CPF do responsavel alterado!");
        assertEquals(patientDTOUpdated.schooling() , "Lutador", "Escolaridade do paciente alterado!");


    }

    
    
}
