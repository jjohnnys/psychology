package br.com.jjohnys.psychological_care.psychological_support.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.jjohnys.psychological_care.psychological_support.domain.enums.PatientStatusEnum;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
public class Payment {

    private String id;
    private Patient patient;
    private int year;
    private int month;
    private LocalDate date;
    private BigDecimal value;


    public boolean validate() {
        boolean patientInTreatment = patient.getStatus() == PatientStatusEnum.IN_TREATMENT;
        if(patientInTreatment) return true;
        return false;
    }
    
}
