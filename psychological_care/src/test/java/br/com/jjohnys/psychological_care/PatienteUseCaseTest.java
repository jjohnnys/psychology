package br.com.jjohnys.psychological_care;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;
import br.com.jjohnys.psychological_care.exceptions.PatientStatusException;
import br.com.jjohnys.psychological_care.psychological_support.application.dto.ContactDTO;
import br.com.jjohnys.psychological_care.psychological_support.application.dto.PatientDTO;
import br.com.jjohnys.psychological_care.psychological_support.application.dto.PatientScheduleDTO;
import br.com.jjohnys.psychological_care.psychological_support.application.usecases.CreatePatientInterector;
import br.com.jjohnys.psychological_care.psychological_support.application.usecases.CreatePatientScheduleUseCaseInterector;
import br.com.jjohnys.psychological_care.psychological_support.application.usecases.UpdatePatientInterector;
import br.com.jjohnys.psychological_care.psychological_support.application.usecases.change_patient_status.ChangePacienteStatusInterector;
import br.com.jjohnys.psychological_care.psychological_support.application.usecases.change_patient_status.ChangePacienteStatusInterectorFactory;
import br.com.jjohnys.psychological_care.psychological_support.domain.Contact;
import br.com.jjohnys.psychological_care.psychological_support.domain.Patient;
import br.com.jjohnys.psychological_care.psychological_support.domain.PatientSchedule;
import br.com.jjohnys.psychological_care.psychological_support.domain.Responsible;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.DaysOfWeekEnum;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.GenderEnum;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.PatientStatusEnum;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PatientRepository;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PatientScheduleRepository;
import br.com.jjohnys.psychological_care.utils.DateUtils;

@SpringBootTest
public class PatienteUseCaseTest {

    @Autowired
    private CreatePatientInterector createPatientInterector;   
    @Autowired
    private UpdatePatientInterector updatePatientInterector;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private ChangePacienteStatusInterectorFactory changePacienteStatusInterectorFactory;   
    @Autowired
    private CreatePatientScheduleUseCaseInterector createPatientScheduleUseCase;
    @Autowired 
    PatientScheduleRepository patientScheduleRepository;
    

    @Test
    public void createPatientSuccess() {
      
        List<ContactDTO> contactsDTO = new ArrayList<ContactDTO>();
        contactsDTO.add(new ContactDTO(null, "ikki@mail.com", "85 98745"));
        PatientDTO patientDTO = new PatientDTO(null, "Ikki", "985.698.497-22", "99.689.695-5", DateUtils.stringDateToLocalDate("1987-04-15"), 100, "PosGraduado", GenderEnum.MALE.getDescription(), "Ilha dificil", null, PatientStatusEnum.IN_TREATMENT.getStatus(), "Muito forte", contactsDTO);       
        createPatientInterector.createPatient(patientDTO);
        Patient patient = patientRepository.findPatientByCpf("985.698.497-22");

        assertEquals(patient.getCpf(), "985.698.497-22", "Paciente criado");
        assertEquals(patient.getContacts().stream().findFirst().get().getEmail(), "ikki@mail.com", "Contato do paciente criado");        
    }

    @Test
    public void createPatientDuplicateCpfError() {

        List<ContactDTO> contactsDTO = new ArrayList<ContactDTO>();
        contactsDTO.add(new ContactDTO(null, "miro@mail.com", "11 9875478"));
        PatientDTO patientDTO = new PatientDTO(null, "Miro", "777.887.888-11", "42.221.332-5", DateUtils.stringDateToLocalDate("1987-04-15"), 100, "PosGraduado", GenderEnum.MALE.getDescription(), "Grecia", null, PatientStatusEnum.IN_TREATMENT.getStatus(), "Certeiro", contactsDTO);       
        createPatientInterector.createPatient(patientDTO);

        List<ContactDTO> contactsDuplicateCfp = new ArrayList<ContactDTO>();
        contactsDTO.add(new ContactDTO(null, "mu@mail.com", "11 8555254"));
        PatientDTO patientDuplicateCfp = new PatientDTO(null, "Mu", "777.887.888-11", "55.288.647-2", DateUtils.stringDateToLocalDate("1987-04-15"), 100, "PosGraduado", GenderEnum.MALE.getDescription(), "Grecia", null, PatientStatusEnum.IN_TREATMENT.getStatus(), "Certeiro", contactsDuplicateCfp);       

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
        PatientDTO patientDTO = new PatientDTO(null, "Aldebaram", "453.222.333-44", "99.689.888-5", DateUtils.stringDateToLocalDate("1987-04-15"), 100, "PosGraduado", GenderEnum.MALE.getDescription(), "Ilha dificil", responsiblesDTO, PatientStatusEnum.IN_TREATMENT.getStatus(), "Muito forte", contactsDTO);       
        
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
        PatientDTO patientDTO = new PatientDTO(patient.getId(), patient.getName(), patient.getCpf(), patient.getRg(), patient.getDateBirth(), patient.getPrice(), "Lutador", patient.getGender().getDescription(), patient.getAddress(), responsibelsDTO, PatientStatusEnum.IN_TREATMENT.getStatus(), patient.getObservation(), contactsDTO);
        
        updatePatientInterector.updatePatient(patientDTO);

        Patient patientUpdated = patientRepository.findPatientByCpf("879.597.845-98");
        PatientDTO patientDTOUpdated = PatientDTO.cretePatientDTO(patientUpdated); 


        assertEquals(patientDTOUpdated.responsiblesDTO().stream().findFirst().get().contacts().stream().findFirst().get().email() , "shumdeandromeda@email.com", "Email do responsavel alterado!");
        assertEquals(patientDTOUpdated.contactsDTO().stream().findFirst().get().email() , "seiyadepegasus@email.com", "Email do paciente alterado!");

        assertEquals(patientDTOUpdated.responsiblesDTO().stream().findFirst().get().cpf() , "777.548.852-47", "CPF do responsavel alterado!");
        assertEquals(patientDTOUpdated.schooling() , "Lutador", "Escolaridade do paciente alterado!");


    }

    @Test
    public void changeStatusPatient() {
        String patientId = patientRepository.findPatientByCpf("879.597.845-98").getId();
        PatientStatusEnum newStatus = PatientStatusEnum.TREATMENT_FINISHED;
        ChangePacienteStatusInterector changePacienteStatus = changePacienteStatusInterectorFactory.create(newStatus);      
        changePacienteStatus.execute(patientId, newStatus.getStatus());
        Patient patient = patientRepository.findPatientByCpf("879.597.845-98");
        assertTrue(patient.getStatus() == PatientStatusEnum.TREATMENT_FINISHED, "Status alterado com sucesso");
    }

    @Test
    public void dasablePatient() {
        List<ContactDTO> contactsDTO = new ArrayList<ContactDTO>();
        contactsDTO.add(new ContactDTO(null, "mu@mail.com", "85 98745"));
        PatientDTO patientDTO = new PatientDTO(null, "Mu", "445.225.112-21", "88.551.362-7", DateUtils.stringDateToLocalDate("1987-04-15"), 100, "PosGraduado", GenderEnum.MALE.getDescription(), "Ilha dificil", null, PatientStatusEnum.IN_TREATMENT.getStatus(), "Sereno", contactsDTO);       
        createPatientInterector.createPatient(patientDTO);
        Patient patient = patientRepository.findPatientByCpf("445.225.112-21");
        DaysOfWeekEnum dayOfWeek = DaysOfWeekEnum.SATURDAY;
        Integer timesOfMonth = 4;        
        
        PatientScheduleDTO patientScheduleDTO = new PatientScheduleDTO(patient.getId(), dayOfWeek.getDaysOfWeek(), timesOfMonth, "10:30:00", PatientSchedule.TypeWeekEnum.PAIR.getTypeWeek());
        createPatientScheduleUseCase.create(patientScheduleDTO);
        PatientStatusEnum newStatus = PatientStatusEnum.TREATMENT_STOPED;
        ChangePacienteStatusInterector changePacienteStatus = changePacienteStatusInterectorFactory.create(newStatus);      
        changePacienteStatus.execute(patient.getId(), newStatus.getStatus());
        PatientSchedule patientSchedule = patientScheduleRepository.getScheduleByPatienteId(patient.getId());
        assertNull(patientSchedule);

    }

    @Test
    public void ReturnsErrorWhenCompletingServiceWithoutBeingInProgress() {        
        String patientId = patientRepository.findPatientByCpf("215.444.554-88").getId();
        PatientStatusEnum newStatus = PatientStatusEnum.TREATMENT_FINISHED;  
        ChangePacienteStatusInterector changePacienteStatus = changePacienteStatusInterectorFactory.create(newStatus);      
        assertThrows(PatientStatusException.class, () -> changePacienteStatus.execute(patientId, newStatus.getStatus()), "Nao pode finalizar atendimento de um paciente sem estar com o atendimento em andamento");
    }

    @Test
    public void ReturnsErrorWhenInterruptedAFinishedService() {        
        String patientId = patientRepository.findPatientByCpf("877.441.558-88").getId();
        PatientStatusEnum newStatus = PatientStatusEnum.TREATMENT_STOPED; 
        ChangePacienteStatusInterector changePacienteStatus = changePacienteStatusInterectorFactory.create(newStatus);             
        assertThrows(PatientStatusException.class, () -> changePacienteStatus.execute(patientId, newStatus.getStatus()), "Só é possivel interromper um tratamento quando o tratamento esta em andamento");
    }
    
}
