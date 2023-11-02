package br.com.jjohnys.psychological_care;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;
import br.com.jjohnys.psychological_care.patient.application.dto.PatientScheduleDTO;
import br.com.jjohnys.psychological_care.patient.application.usecases.CreatePatientScheduleUseCaseInterector;
import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.PatientSchedule;
import br.com.jjohnys.psychological_care.patient.domain.enums.DaysOfWeekEnum;
import br.com.jjohnys.psychological_care.patient.domain.enums.PatientStatusEnum;
import br.com.jjohnys.psychological_care.patient.gateways.PatientRepository;
import br.com.jjohnys.psychological_care.patient.gateways.PatientScheduleRepository;
import br.com.jjohnys.psychological_care.utils.DateUtils;

@SpringBootTest
public class PatienteScheduleUseCaseTest {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientScheduleRepository patientScheduleRepository;
    @Autowired
    private CreatePatientScheduleUseCaseInterector createPatientScheduleUseCase;

    @Test
    public void createPatienteSchedule() {

        Patient patient = patientRepository.findPatientById("paciente_1");
        DaysOfWeekEnum dayOfWeek = DaysOfWeekEnum.MONDAY;
        var time = LocalTime.of(10, 30, 0);
        Integer timesOfMonth = 4;        
        
        PatientScheduleDTO patientScheduleDTO = new PatientScheduleDTO(patient.getId(), "Segunda-feira", timesOfMonth, "10:30:00");
        createPatientScheduleUseCase.create(patientScheduleDTO);
        
        PatientSchedule patientSchedule = patientScheduleRepository.getScheduleByPatienteId(patient.getId());
        assertEquals(dayOfWeek, patientSchedule.getDayOfWeek());
        assertEquals(timesOfMonth, patientSchedule.getTimesOfMonth());
        assertEquals(time, patientSchedule.getTime());

    }

    @Test
    public void returnErrorWithInvalidPatiente() {

        Patient patient = new Patient("invalid_patient", "Invalid Patient", "111.587.965.88", "55.552.333-4", DateUtils.stringDateToLocalDate("2000-04-07"), 100, "Nao estoudou", null, "Qualquer lugar", PatientStatusEnum.IN_TREATMENT, "Nao existe");        
        Integer timesOfMonth = 4;
        PatientScheduleDTO patientScheduleDTO = new PatientScheduleDTO(patient.getId(), "Segunda-feira", timesOfMonth, "10:30:00");
        assertThrows(BusinessExceptions.class, () ->  createPatientScheduleUseCase.create(patientScheduleDTO), "Paciente nao cadastrado");

    }
    
}
