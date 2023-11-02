package br.com.jjohnys.psychological_care.patient.gateways;

import br.com.jjohnys.psychological_care.patient.domain.PatientSchedule;

public interface PatientScheduleRepository {

    int insert(PatientSchedule patientSchedule);
    PatientSchedule getScheduleByPatienteId(String patient_id);
    int update(PatientSchedule patientSchedule);
    
}
