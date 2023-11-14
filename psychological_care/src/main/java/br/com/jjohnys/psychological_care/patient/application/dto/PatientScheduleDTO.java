package br.com.jjohnys.psychological_care.patient.application.dto;

import java.time.LocalTime;

import br.com.jjohnys.psychological_care.patient.domain.PatientSchedule;
import br.com.jjohnys.psychological_care.patient.domain.enums.DaysOfWeekEnum;

public record PatientScheduleDTO(String patienteId, String daysOfWeek, Integer timesOfMonth, String timeOfDay, String typeWeek) {   

    public DaysOfWeekEnum daysOfWeekEnum() {
        return DaysOfWeekEnum.getDaysOfWeekEnum(this.daysOfWeek());
    }

    public LocalTime timeOfDayLocalTime() {
        return LocalTime.parse(this.timeOfDay);
    }

    public PatientSchedule.TypeWeekEnum typeWeekEnum() {
        return PatientSchedule.TypeWeekEnum.getTypeWeekEnum(typeWeek);
    }

}
