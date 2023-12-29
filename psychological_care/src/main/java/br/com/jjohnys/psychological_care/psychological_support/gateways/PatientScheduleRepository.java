package br.com.jjohnys.psychological_care.psychological_support.gateways;

import java.time.LocalTime;

import br.com.jjohnys.psychological_care.psychological_support.domain.PatientSchedule;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.DaysOfWeekEnum;

public interface PatientScheduleRepository {

    int insert(PatientSchedule patientSchedule);
    PatientSchedule getScheduleByPatientId(String patient_id);
    int update(PatientSchedule patientSchedule);
    public int deleteByPatientId(String patientId) ;
    PatientSchedule getScheduleByPatientePeriod(LocalTime timeIni, LocalTime timeFin, DaysOfWeekEnum dayOfWeek, PatientSchedule.TypeWeekEnum typeWeek);
    
}
