package br.com.jjohnys.psychological_care.psychological_support.application.usecases;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;
import br.com.jjohnys.psychological_care.psychological_support.application.dto.PatientDTO;
import br.com.jjohnys.psychological_care.psychological_support.domain.Contact;
import br.com.jjohnys.psychological_care.psychological_support.domain.Patient;
import br.com.jjohnys.psychological_care.psychological_support.domain.Responsible;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.GenderEnum;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.PatientStatusEnum;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PatientRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UpdatePatientInterector {

    private PatientRepository patientRepository;

    public void updatePatient(PatientDTO patientDTO) {

        if(patientRepository.existisOtherPatientWithSameCPF(patientDTO.id(), patientDTO.cpf())) 
            throw new BusinessExceptions(String.format("Ja existe o CPF: %s cadastrado", patientDTO.cpf()));        
        Patient patient = new Patient(patientDTO.id(), patientDTO.name(), patientDTO.cpf(), patientDTO.rg(), patientDTO.dateBirth(), patientDTO.price(),patientDTO.schooling(), GenderEnum.getGenderEnum(patientDTO.gender()), patientDTO.address(), PatientStatusEnum.getStatusPatientEnum(patientDTO.status()), patientDTO.observation());
        List<Contact> contactsPatient = patientDTO.getContactsDTO().stream().map(dto -> Contact.buildPatientContact(dto.id(), dto.email(), dto.telephone(), patientDTO.id())).collect(Collectors.toList());
        List<Contact> contactsResponsible = new ArrayList<Contact>();
        List<Responsible> responsibles = new ArrayList<Responsible>();
        if(patientDTO.responsiblesDTO() != null) patientDTO.responsiblesDTO().forEach(responsibleDTO -> {
            if(responsibleDTO.cpf().equals(patientDTO.cpf()))
                throw new BusinessExceptions(String.format("O CPF do responsavel nao podeser igual ao do paciente: %s", responsibleDTO.cpf()));
            if(patientRepository.existisOtherResponsibleWithSameCPF(patientDTO.id(), patientDTO.cpf())) 
                throw new BusinessExceptions(String.format("Ja existe o CPF: %s cadastrado", responsibleDTO.cpf()));
            responsibles.add(new Responsible(responsibleDTO.id(), responsibleDTO.name(), responsibleDTO.cpf(), responsibleDTO.rg(), responsibleDTO.dateBirth(), responsibleDTO.parentenge(), patient));
            contactsResponsible.addAll(responsibleDTO.getContacts().stream().map(dto -> Contact.buildResponsibleContact(dto.id(), dto.email(), dto.telephone(), responsibleDTO.id())).collect(Collectors.toList()));                        
        });        
        patientRepository.updatePatient(patient, contactsPatient, responsibles, contactsResponsible);   


    }
    
}
