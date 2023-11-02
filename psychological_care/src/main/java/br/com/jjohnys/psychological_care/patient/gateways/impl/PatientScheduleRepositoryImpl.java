package br.com.jjohnys.psychological_care.patient.gateways.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.jjohnys.psychological_care.patient.domain.PatientSchedule;
import br.com.jjohnys.psychological_care.patient.gateways.PatientScheduleRepository;
import br.com.jjohnys.psychological_care.patient.infrastructure.jdbc.PatientScheduleJDBC;

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
    
}