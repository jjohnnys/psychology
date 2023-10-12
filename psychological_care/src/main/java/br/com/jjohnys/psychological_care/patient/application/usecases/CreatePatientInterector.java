package br.com.jjohnys.psychological_care.patient.application.usecases;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.jjohnys.psychological_care.patient.application.dto.PatientDTO;
import br.com.jjohnys.psychological_care.patient.domain.Contact;
import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.Responsible;
import br.com.jjohnys.psychological_care.patient.domain.enums.Gender;
import br.com.jjohnys.psychological_care.patient.gateways.PatientRepository;
import lombok.NonNull;

@Service
public class CreatePatientInterector {

    @NonNull
    private PatientRepository patientRepository;

    public void createPatient(PatientDTO patientDTO) {
        String patientId = UUID.randomUUID().toString();
        Patient patient = new Patient(patientId, patientDTO.name(), patientDTO.cpf(), patientDTO.rg(), patientDTO.dateBirth(), patientDTO.price(),patientDTO.schooling(), Gender.getGenderEnum(patientDTO.gender()), patientDTO.address(), patientDTO.observation());
        List<Contact> contactsPatient = patientDTO.getContacts().stream().map(dto -> Contact.buildPatientContact(UUID.randomUUID().toString(), dto.email(), dto.telephone(), patientId)).collect(Collectors.toList());
        List<Contact> contactsResponsible = null;
        Responsible responsible = null;
        if(patientDTO.responsible() != null) {
            String responsibleId = UUID.randomUUID().toString();
            responsible = new Responsible(null, patientDTO.responsible().name(), patientDTO.responsible().cpf(), patientDTO.responsible().rg(), patientDTO.responsible().dateBirth(), patientDTO.responsible().parentenge(), patient);
            contactsResponsible = patientDTO.responsible().getContacts().stream().map(dto -> Contact.buildResponsibleContact(UUID.randomUUID().toString(), dto.email(), dto.telephone(), responsibleId)).collect(Collectors.toList());                
        }

        patientRepository.insertPatient(patient, contactsPatient, responsible, contactsResponsible);        
        
    }

    

    
    
}
