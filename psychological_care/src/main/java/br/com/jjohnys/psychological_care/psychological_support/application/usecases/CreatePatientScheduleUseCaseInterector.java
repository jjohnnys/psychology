package br.com.jjohnys.psychological_care.psychological_support.application.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;
import br.com.jjohnys.psychological_care.psychological_support.application.dto.PatientScheduleDTO;
import br.com.jjohnys.psychological_care.psychological_support.domain.Patient;
import br.com.jjohnys.psychological_care.psychological_support.domain.PatientSchedule;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PatientRepository;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PatientScheduleRepository;

@Service
public class CreatePatientScheduleUseCaseInterector {

    @Autowired
    private PatientScheduleRepository patienteScheduleRepository;
    @Autowired
    private PatientRepository patientRepository;

    public void create(PatientScheduleDTO patientScheduleDTO) throws BusinessExceptions {

        Patient patient = patientRepository.findPatientById(patientScheduleDTO.patienteId());
        PatientSchedule scheduleOfPatient = patienteScheduleRepository.getScheduleByPatientId(patientScheduleDTO.patienteId());
        if(scheduleOfPatient != null) throw new BusinessExceptions(String.format("Ja existe agenda para o paciente %s em dias %ss", patient.getName(), patientScheduleDTO.typeWeek()));
        PatientSchedule period = patienteScheduleRepository.getScheduleByPatientePeriod(patientScheduleDTO.timeOfDayLocalTime(), patientScheduleDTO.timeOfDayLocalTime().plusHours(1) , patientScheduleDTO.daysOfWeekEnum(), patientScheduleDTO.typeWeekEnum());
        if(period != null) throw new BusinessExceptions(String.format("O paciente %s, ja esta no horario das %s as %s de %s em semanas %sres", patient.getName(), patientScheduleDTO.timeOfDayLocalTime(), patientScheduleDTO.timeOfDayLocalTime().plusHours(1), patientScheduleDTO.daysOfWeekEnum().getDaysOfWeek(), patientScheduleDTO.typeWeekEnum().getTypeWeek()));
        PatientSchedule patientSchedule = new PatientSchedule(patient, patientScheduleDTO.daysOfWeekEnum(), patientScheduleDTO.timesOfMonth(), patientScheduleDTO.timeOfDayLocalTime(), patientScheduleDTO.typeWeekEnum());
        patienteScheduleRepository.insert(patientSchedule);
    }

    
    
}
