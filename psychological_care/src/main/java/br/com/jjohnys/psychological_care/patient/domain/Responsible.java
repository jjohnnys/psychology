package br.com.jjohnys.psychological_care.patient.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Responsible {


    private String id;
    private String name;
    private String cpf;
    private String rg;
    private LocalDate dateBirth;
    private String parentege;
    private Patient patient;


    
}
