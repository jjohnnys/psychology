package br.com.jjohnys.psychological_care.patient.application.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.jjohnys.psychological_care.patient.domain.Responsible;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter 
@AllArgsConstructor
public class PatientDTO {

    private String id;
    private String name;
    private String cpf;
    private String rg;
    private LocalDate dateBirth;
    private Integer price;
    private String schooling;
    private String gender;
    private String address;
    private ResponsibleDTO responsible;
    private String observation; 
    private List<ContactDTO> contacts = new ArrayList<ContactDTO>(); 

    @AllArgsConstructor
    @Getter
    public class ResponsibleDTO {
        private String name;
        private String cpf;
        private String rg;
        private LocalDate dateBirth;
        private String parentenge;
        private List<ContactDTO> contacts = new ArrayList<ContactDTO>();
    }
    
}
