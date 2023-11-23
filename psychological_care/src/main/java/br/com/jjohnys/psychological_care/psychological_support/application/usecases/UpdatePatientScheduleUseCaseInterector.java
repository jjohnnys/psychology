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
public class UpdatePatientScheduleUseCaseInterector {

    @Autowired
    private PatientScheduleRepository patienteScheduleRepository;
    @Autowired
    private PatientRepository patientRepository;

    public void update(PatientScheduleDTO patientScheduleDTO) {

        Patient patient = patientRepository.findPatientById(patientScheduleDTO.patienteId());
        if(patient == null) throw new BusinessExceptions("Paciente nao cadastrado");
        PatientSchedule period = patienteScheduleRepository.getScheduleByPatientePeriod(patientScheduleDTO.timeOfDayLocalTime(), patientScheduleDTO.timeOfDayLocalTime().plusHours(1) , patientScheduleDTO.daysOfWeekEnum(), patientScheduleDTO.typeWeekEnum());
        if(period != null) throw new BusinessExceptions(String.format("O paciente %s, ja esta no horario das %s as %s de %s", patient.getName(), patientScheduleDTO.timeOfDayLocalTime().toString(), patientScheduleDTO.timeOfDayLocalTime().plusHours(1).toString(), patientScheduleDTO.daysOfWeekEnum().getDaysOfWeek()));
        PatientSchedule patientSchedule = new PatientSchedule(patient, patientScheduleDTO.daysOfWeekEnum(), patientScheduleDTO.timesOfMonth(), patientScheduleDTO.timeOfDayLocalTime(), patientScheduleDTO.typeWeekEnum());
        patienteScheduleRepository.update(patientSchedule);

    }
    
}
