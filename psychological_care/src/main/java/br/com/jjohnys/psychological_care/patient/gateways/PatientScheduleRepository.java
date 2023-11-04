package br.com.jjohnys.psychological_care.patient.gateways;

import java.time.LocalTime;

import br.com.jjohnys.psychological_care.patient.domain.PatientSchedule;
import br.com.jjohnys.psychological_care.patient.domain.enums.DaysOfWeekEnum;

public interface PatientScheduleRepository {

    int insert(PatientSchedule patientSchedule);
    PatientSchedule getScheduleByPatienteId(String patient_id);
    int update(PatientSchedule patientSchedule);
    PatientSchedule getScheduleByPatientePeriod(LocalTime timeIni, LocalTime timeFin, DaysOfWeekEnum dayOfWeek);
    
}
