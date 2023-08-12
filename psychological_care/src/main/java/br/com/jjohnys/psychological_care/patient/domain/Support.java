package br.com.jjohnys.psychological_care.patient.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Support {

    @EqualsAndHashCode.Include
    private String id;
    private Patient patient;
    private LocalDateTime dateSuport;
    private String observation;

}
