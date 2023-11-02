package br.com.jjohnys.psychological_care.patient.domain.enums;

import java.util.Arrays;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;

public enum DaysOfWeekEnum {

    SUNDAY("Domingo"),
    MONDAY("Segunda-feira"),
    TUESDAY("Terça-feira"),
    WEDNESDAY("Quarta-feira"),
    THURSDAY("Quinta-feira"),
    FRIDAY("Sexta-feira"),
    SATURDAY("Sabado");
    

    private String daysOfWeek;

    private DaysOfWeekEnum(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }
    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public static DaysOfWeekEnum getDaysOfWeekEnum(String daysOfWeek) {
        return Arrays.asList(DaysOfWeekEnum.values()).stream().
            filter(v -> v.getDaysOfWeek().equalsIgnoreCase(daysOfWeek)).findFirst().
            orElseThrow(() -> new BusinessExceptions("Dia da semana invalido"));     
    }
    
}
