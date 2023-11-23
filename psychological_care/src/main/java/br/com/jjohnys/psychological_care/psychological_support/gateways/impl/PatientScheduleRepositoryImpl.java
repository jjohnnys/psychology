package br.com.jjohnys.psychological_care.psychological_support.gateways.impl;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.jjohnys.psychological_care.psychological_support.domain.PatientSchedule;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.DaysOfWeekEnum;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PatientScheduleRepository;
import br.com.jjohnys.psychological_care.psychological_support.infrastructure.jdbc.PatientScheduleJDBC;

@Component
public class PatientScheduleRepositoryImpl implements PatientScheduleRepository {

    @Autowired
    private PatientScheduleJDBC patientScheduleJDBC;

    @Override
    public int insert(PatientSchedule patientSchedule) {
        return patientScheduleJDBC.insert(patientSchedule);
    }

    @Override
    public PatientSchedule getScheduleByPatienteId(String patientId) {
        return patientScheduleJDBC.getScheduleByPatienteId(patientId);
    }

    @Override
    public int update(PatientSchedule patientSchedule) {
        return patientScheduleJDBC.update(patientSchedule);
    }
    public int deleteByPatientId(String patientId) {
        return patientScheduleJDBC.deleteByPatientId(patientId);
    }

    @Override
    public PatientSchedule getScheduleByPatientePeriod(LocalTime timeIni, LocalTime timeFin, DaysOfWeekEnum dayOfWeek, PatientSchedule.TypeWeekEnum typeWeek) {
        return patientScheduleJDBC.getScheduleByPatientePeriod(timeIni, timeFin, dayOfWeek, typeWeek);
    }
    
}
