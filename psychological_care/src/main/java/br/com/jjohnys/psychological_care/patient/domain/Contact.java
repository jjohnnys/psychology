package br.com.jjohnys.psychological_care.patient.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Contact {

    @EqualsAndHashCode.Include
    private String id;
    private String name;
    private String email;
    private String telephone;
    private String parentage;
    private String patientId;
    
}
