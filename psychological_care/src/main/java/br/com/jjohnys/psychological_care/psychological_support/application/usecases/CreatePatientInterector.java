package br.com.jjohnys.psychological_care.psychological_support.application.usecases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;
import br.com.jjohnys.psychological_care.psychological_support.application.dto.PatientDTO;
import br.com.jjohnys.psychological_care.psychological_support.domain.Contact;
import br.com.jjohnys.psychological_care.psychological_support.domain.Patient;
import br.com.jjohnys.psychological_care.psychological_support.domain.Responsible;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.GenderEnum;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.PatientStatusEnum;
import br.com.jjohnys.psychological_care.psychological_support.domain.value_objects.CPF;
import br.com.jjohnys.psychological_care.psychological_support.domain.value_objects.EMAIL;
import br.com.jjohnys.psychological_care.psychological_support.domain.value_objects.TELEFONE;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PatientRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreatePatientInterector {
    
    private PatientRepository patientRepository;

    public void createPatient(PatientDTO patientDTO) {
        
        String patientId = UUID.randomUUID().toString();
        Patient patient = new Patient(patientId, patientDTO.name(), new CPF(patientDTO.cpf()), patientDTO.rg(), patientDTO.dateBirth(), patientDTO.price(),patientDTO.schooling(), GenderEnum.getGenderEnum(patientDTO.gender()), patientDTO.address(), PatientStatusEnum.getStatusPatientEnum(patientDTO.status()), patientDTO.observation());
        if(patientRepository.existisOtherPatientWithSameCPF(null, patient.getCpf())) 
            throw new BusinessExceptions(String.format("Ja existe o CPF: %s cadastrado", patientDTO.cpf()));        
        List<Contact> contactsPatient = patientDTO.getContactsDTO().stream().map(dto -> Contact.buildPatientContact(UUID.randomUUID().toString(), new EMAIL(dto.email()), new TELEFONE(dto.telephone()), patientId)).collect(Collectors.toList());
        List<Contact> contactsResponsible = new ArrayList<Contact>();
        List<Responsible> responsibles = new ArrayList<Responsible>();
        if(patientDTO.responsiblesDTO() != null) patientDTO.responsiblesDTO().forEach(responsibleDTO -> {
            String responsibleId = UUID.randomUUID().toString();
            Responsible responsible = new Responsible(responsibleId, responsibleDTO.name(), new CPF(responsibleDTO.cpf()), responsibleDTO.rg(), responsibleDTO.dateBirth(), responsibleDTO.parentenge(), patient);
            if(patient.getCpf().equals(responsible.getCpf()))
                throw new BusinessExceptions(String.format("O CPF do responsavel nao podeser igual ao do paciente: %s", responsibleDTO.cpf()));
            if(patientRepository.existisOtherResponsibleWithSameCPF(null, responsible.getCpf())) 
                throw new BusinessExceptions(String.format("Ja existe o CPF: %s cadastrado", responsibleDTO.cpf()));
            
            responsibles.add(responsible);
            contactsResponsible.addAll(responsibleDTO.getContacts().stream().map(dto -> Contact.buildResponsibleContact(UUID.randomUUID().toString(), new EMAIL(dto.email()), new TELEFONE(dto.telephone()), responsibleId)).collect(Collectors.toList()));                        
        });        
        patientRepository.insertPatient(patient, contactsPatient, responsibles, contactsResponsible);                
    }

    

    
    
}
