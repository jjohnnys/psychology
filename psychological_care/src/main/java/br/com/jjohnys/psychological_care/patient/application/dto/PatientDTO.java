package br.com.jjohnys.psychological_care.patient.application.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
              ResponsibleDTO responsible,
              String observation,
              List<ContactDTO> contacts) {
    
    public List<ContactDTO> getContacts() {
        if(this.contacts == null) return new ArrayList<ContactDTO>();
        return this.contacts;
    }
        
    public record ResponsibleDTO(String name, String cpf, String rg, LocalDate dateBirth, String parentenge, List<ContactDTO> contacts) {
    
        public List<ContactDTO> getContacts() {
        if(this.contacts == null) return new ArrayList<ContactDTO>();
        return this.contacts;
        }
    }
    
}
