package br.com.jjohnys.psychological_care.patient.domain;

import java.time.LocalTime;

import br.com.jjohnys.psychological_care.patient.domain.enums.DaysOfWeekEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatientSchedule {    
    
    private Patient patient;
    private DaysOfWeekEnum dayOfWeek;
    private Integer timesOfMonth;
    private LocalTime time;


}
