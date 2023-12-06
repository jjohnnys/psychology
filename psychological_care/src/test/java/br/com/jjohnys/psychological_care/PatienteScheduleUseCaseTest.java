package br.com.jjohnys.psychological_care;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;
import br.com.jjohnys.psychological_care.psychological_support.application.dto.PatientScheduleDTO;
import br.com.jjohnys.psychological_care.psychological_support.application.usecases.CreatePatientScheduleUseCaseInterector;
import br.com.jjohnys.psychological_care.psychological_support.application.usecases.UpdatePatientScheduleUseCaseInterector;
import br.com.jjohnys.psychological_care.psychological_support.domain.Patient;
import br.com.jjohnys.psychological_care.psychological_support.domain.PatientSchedule;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.DaysOfWeekEnum;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.PatientStatusEnum;
import br.com.jjohnys.psychological_care.psychological_support.domain.value_objects.CPF;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PatientRepository;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PatientScheduleRepository;
import br.com.jjohnys.psychological_care.utils.DateUtils;

@SpringBootTest
public class PatienteScheduleUseCaseTest {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientScheduleRepository patientScheduleRepository;
    @Autowired
    private CreatePatientScheduleUseCaseInterector createPatientScheduleUseCase;
    @Autowired
    private UpdatePatientScheduleUseCaseInterector updatePatientScheduleUseCase;

    @Test
    public void createPatienteSchedule() {

        Patient patient = patientRepository.findPatientById("paciente_1");
        DaysOfWeekEnum dayOfWeek = DaysOfWeekEnum.MONDAY;
        var time = LocalTime.of(10, 30, 0);
        Integer timesOfMonth = 4;        
        
        PatientScheduleDTO patientScheduleDTO = new PatientScheduleDTO(patient.getId(), dayOfWeek.getDaysOfWeek(), timesOfMonth, "10:30:00", PatientSchedule.TypeWeekEnum.PAIR.getTypeWeek());
        createPatientScheduleUseCase.create(patientScheduleDTO);
        
        PatientSchedule patientSchedule = patientScheduleRepository.getScheduleByPatienteId(patient.getId());
        assertEquals(dayOfWeek, patientSchedule.getDayOfWeek());
        assertEquals(timesOfMonth, patientSchedule.getTimesOfMonth());
        assertEquals(time, patientSchedule.getTime());

    }

    @Test
    public void updatePatienteSchedule() {

        Patient patient = patientRepository.findPatientById("paciente_2");
        DaysOfWeekEnum dayOfWeek = DaysOfWeekEnum.WEDNESDAY;
        var time = LocalTime.of(10, 30, 0);
        Integer timesOfMonth = 2;        
        
        PatientScheduleDTO patientScheduleDTO = new PatientScheduleDTO(patient.getId(), dayOfWeek.getDaysOfWeek(), timesOfMonth, "10:30:00", PatientSchedule.TypeWeekEnum.ODD.getTypeWeek());
        updatePatientScheduleUseCase.update(patientScheduleDTO);
        
        PatientSchedule patientSchedule = patientScheduleRepository.getScheduleByPatienteId(patient.getId());
        assertEquals(dayOfWeek, patientSchedule.getDayOfWeek());
        assertEquals(timesOfMonth, patientSchedule.getTimesOfMonth());
        assertEquals(time, patientSchedule.getTime());

    }    

    @Test
    public void sholdReturnErrorWithInvalidPatiente() {

        Patient patient = new Patient("invalid_patient", "Invalid Patient", new CPF("111.587.965.88"), "55.552.333-4", DateUtils.stringDateToLocalDate("2000-04-07"), 100, "Nao estoudou", null, "Qualquer lugar", PatientStatusEnum.IN_TREATMENT, "Nao existe");        
        Integer timesOfMonth = 4;
        PatientScheduleDTO patientScheduleDTO = new PatientScheduleDTO(patient.getId(), "Segunda-feira", timesOfMonth, "10:30:00", PatientSchedule.TypeWeekEnum.PAIR.getTypeWeek());
        assertThrows(BusinessExceptions.class, () -> createPatientScheduleUseCase.create(patientScheduleDTO), "Paciente nao cadastrado");

    }

    @Test
    public void shouldReturnErrorWhenCreatingConflictingAgent() {
        Patient patient = patientRepository.findPatientById("paciente_4");
        DaysOfWeekEnum dayOfWeek = DaysOfWeekEnum.TUESDAY;
        var time = LocalTime.of(10, 30, 0);
        Integer timesOfMonth = 4;
        PatientScheduleDTO patientScheduleDTO = new PatientScheduleDTO(patient.getId(), dayOfWeek.getDaysOfWeek(), timesOfMonth, time.toString(), PatientSchedule.TypeWeekEnum.PAIR.getTypeWeek());
        assertThrows(BusinessExceptions.class, () -> createPatientScheduleUseCase.create(patientScheduleDTO), String.format("O paciente %s, ja esta no horario das %s as %s de %s em semanas %ses", patient.getName(), time, time.plusHours(1), dayOfWeek.getDaysOfWeek(), PatientSchedule.TypeWeekEnum.PAIR.getTypeWeek()));
    }

    @Test
    public void shouldReturnErrorWhenPatienHaveBeenSchedule() {
        Patient patient = patientRepository.findPatientById("paciente_2");
        DaysOfWeekEnum dayOfWeek = DaysOfWeekEnum.FRIDAY;
        var time = LocalTime.of(10, 30, 0);
        Integer timesOfMonth = 2;
        PatientScheduleDTO patientScheduleDTO = new PatientScheduleDTO(patient.getId(), dayOfWeek.getDaysOfWeek(), timesOfMonth, time.toString(), PatientSchedule.TypeWeekEnum.PAIR.getTypeWeek());
        assertThrows(BusinessExceptions.class, () -> createPatientScheduleUseCase.create(patientScheduleDTO), String.format("Ja existe agenda para o paciente %s", patient.getName()));
    }
    
    
}
