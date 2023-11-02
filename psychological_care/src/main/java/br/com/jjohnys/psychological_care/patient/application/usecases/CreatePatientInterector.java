package br.com.jjohnys.psychological_care.patient.application.usecases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;
import br.com.jjohnys.psychological_care.patient.application.dto.PatientDTO;
import br.com.jjohnys.psychological_care.patient.domain.Contact;
import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.Responsible;
import br.com.jjohnys.psychological_care.patient.domain.enums.GenderEnum;
import br.com.jjohnys.psychological_care.patient.domain.enums.PatientStatusEnum;
import br.com.jjohnys.psychological_care.patient.gateways.PatientRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreatePatientInterector {
    
    private PatientRepository patientRepository;

    public void createPatient(PatientDTO patientDTO) {

        if(patientRepository.existisOtherPatientWithSameCPF(patientDTO.id(), patientDTO.cpf())) 
            throw new BusinessExceptions(String.format("Ja existe o CPF: %s cadastrado", patientDTO.cpf()));
        String patientId = UUID.randomUUID().toString();
        Patient patient = new Patient(patientId, patientDTO.name(), patientDTO.cpf(), patientDTO.rg(), patientDTO.dateBirth(), patientDTO.price(),patientDTO.schooling(), GenderEnum.getGenderEnum(patientDTO.gender()), patientDTO.address(), PatientStatusEnum.getStatusPatientEnum(patientDTO.status()), patientDTO.observation());
        List<Contact> contactsPatient = patientDTO.getContactsDTO().stream().map(dto -> Contact.buildPatientContact(UUID.randomUUID().toString(), dto.email(), dto.telephone(), patientId)).collect(Collectors.toList());
        List<Contact> contactsResponsible = new ArrayList<Contact>();
        List<Responsible> responsibles = new ArrayList<Responsible>();
        if(patientDTO.responsiblesDTO() != null) patientDTO.responsiblesDTO().forEach(responsibleDTO -> {
            if(responsibleDTO.cpf().equals(patientDTO.cpf()))
                throw new BusinessExceptions(String.format("O CPF do responsavel nao podeser igual ao do paciente: %s", responsibleDTO.cpf()));
            if(patientRepository.existisOtherResponsibleWithSameCPF(patientDTO.id(), patientDTO.cpf())) 
                throw new BusinessExceptions(String.format("Ja existe o CPF: %s cadastrado", responsibleDTO.cpf()));
            String responsibleId = UUID.randomUUID().toString();
            responsibles.add(new Responsible(responsibleId, responsibleDTO.name(), responsibleDTO.cpf(), responsibleDTO.rg(), responsibleDTO.dateBirth(), responsibleDTO.parentenge(), patient));
            contactsResponsible.addAll(responsibleDTO.getContacts().stream().map(dto -> Contact.buildResponsibleContact(UUID.randomUUID().toString(), dto.email(), dto.telephone(), responsibleId)).collect(Collectors.toList()));                        
        });        
        patientRepository.insertPatient(patient, contactsPatient, responsibles, contactsResponsible);                
    }

    

    
    
}
