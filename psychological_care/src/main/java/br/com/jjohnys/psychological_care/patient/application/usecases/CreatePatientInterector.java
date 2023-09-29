package br.com.jjohnys.psychological_care.patient.application.usecases;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.jjohnys.psychological_care.patient.application.dto.ContactDTO;
import br.com.jjohnys.psychological_care.patient.application.dto.PatientDTO;
import br.com.jjohnys.psychological_care.patient.domain.Contact;
import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.Responsible;
import br.com.jjohnys.psychological_care.patient.domain.enums.Gender;

public class CreatePatientInterector {

    public void createPatient(PatientDTO patientDTO) {

        List<Contact> contactsPatient = createDomainPatientContacts(patientDTO.getContacts());
        List<Contact> contactsResponsible = null;
        if(patientDTO.getResponsible() != null)
            contactsResponsible = createDomainResponsibleContacts(patientDTO.getResponsible().getContacts());
        

        

        
    }

    private Responsible createDomainResponsible(PatientDTO.ResponsibleDTO responsibleDTO, Patient patient) {
        return new Responsible(null, responsibleDTO.getName(), responsibleDTO.getCpf(), responsibleDTO.getRg(), responsibleDTO.getDateBirth(), responsibleDTO.getParentenge(), patient);
    }

    private Patient createDomaiPatient(PatientDTO patientDTO) {
        return new Patient(UUID.randomUUID().toString(), 
                   patientDTO.getName(), 
                   patientDTO.getCpf(), 
                   patientDTO.getRg(), 
                   patientDTO.getDateBirth(), 
                   patientDTO.getPrice(),
                   patientDTO.getSchooling(), 
                   Gender.getGenderEnum(patientDTO.getGender()), 
                   patientDTO.getAddress(), 
                   patientDTO.getObservation());
    }

    private List<Contact> createDomainPatientContacts(List<ContactDTO> contactDTOs) {
        return  contactDTOs.stream().map(dto -> new Contact(
                UUID.randomUUID().toString(),
                dto.getName(),
                dto.getEmail(),
                dto.getTelephone(),
                dto.getPatientId(), 
                null)).collect(Collectors.toList());
    }

    private List<Contact> createDomainResponsibleContacts(List<ContactDTO> contactDTOs) {
        return  contactDTOs.stream().map(dto -> new Contact(
                UUID.randomUUID().toString(),
                dto.getName(),
                dto.getEmail(),
                dto.getTelephone(),
                null, dto.getResponsibleId())).collect(Collectors.toList());
    }

    
    
}
