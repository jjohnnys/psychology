package br.com.jjohnys.psychological_care.patient.application.dto;

import java.time.LocalDate;

import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.Plan;
import br.com.jjohnys.psychological_care.patient.domain.enums.Gender;

public class PatientDTO {

    private String id;
    private String name;
    private String cpf;
    private String rg;
    private LocalDate dateBirth;
    private Plan plan;
    private String schooling;
    private Gender gender;
    private String address;
    private Patient responsible;
    private String observation;  
    
}
