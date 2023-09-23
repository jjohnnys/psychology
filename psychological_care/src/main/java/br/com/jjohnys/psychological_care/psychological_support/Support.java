package br.com.jjohnys.psychological_care.psychological_support;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.jjohnys.psychological_care.patient.domain.Patient;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Support {

    private String id;
    private Patient patient;
    private LocalDateTime dateSuport;
    private String observation;

}
