package br.com.jjohnys.psychological_care.patient.application.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.jjohnys.psychological_care.patient.application.dto.PatientDTO.ResponsibleDTO;
import br.com.jjohnys.psychological_care.patient.domain.Patient;

public record PatientDTO (
              String id,
              String name,
              String cpf,
              String rg,
              LocalDate dateBirth,
              Integer price,
              String schooling,
              String gender,
              String address,
              List<ResponsibleDTO> responsiblesDTO,
              String status,
              String observation,
              List<ContactDTO> contactsDTO) {
    
    public List<ContactDTO> getContactsDTO() {
        return this.contactsDTO;
    }

    public List<ResponsibleDTO> getResponsiblesDTO() {        
        return this.responsiblesDTO;
    }

    public static PatientDTO cretePatientDTO(Patient patient) {        
        return new PatientDTO(patient.getId(), patient.getName(), patient.getCpf(), patient.getRg(), patient.getDateBirth(), patient.getPrice(), patient.getSchooling(), patient.getGender().getDescription(), patient.getAddress(), 
            patient.getResponsibles().stream().map(responsible -> 
                new ResponsibleDTO(responsible.getId(), responsible.getName(), responsible.getCpf(), responsible.getRg(), responsible.getDateBirth(), responsible.getParentege(), 
                responsible.getContacts().stream().map(contact -> new ContactDTO(contact.getId(), contact.getEmail(), contact.getTelephone())).collect(Collectors.toList()))).
                collect(Collectors.toList()), 
            patient.getStatus().getStatus(), patient.getObservation(), patient.getContacts().stream().map(contact -> new ContactDTO(contact.getId(), contact.getEmail(), contact.getTelephone())).collect(Collectors.toList()));
    }
        
    public record ResponsibleDTO(String id, String name, String cpf, String rg, LocalDate dateBirth, String parentenge, List<ContactDTO> contacts) {
    
        public List<ContactDTO> getContacts() {
        if(this.contacts == null) return new ArrayList<ContactDTO>();
        return this.contacts;
        }
    }
    
}
