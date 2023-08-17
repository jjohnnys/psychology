package br.com.jjohnys.psychological_care.patient.domain;

import java.time.LocalDate;

import br.com.jjohnys.psychological_care.patient.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@ToString
public class Patient {

    @EqualsAndHashCode.Include
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
