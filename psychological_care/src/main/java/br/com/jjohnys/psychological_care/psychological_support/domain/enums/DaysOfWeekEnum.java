package br.com.jjohnys.psychological_care.psychological_support.domain.enums;

import java.util.Arrays;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;

public enum DaysOfWeekEnum {

    SUNDAY("Domingo"),
    MONDAY("Segunda-feira"),
    TUESDAY("Terca-feira"),
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

    public int getNumber() {

         return switch(this) {
            case SUNDAY -> 1;
            case MONDAY -> 2;
            case TUESDAY -> 3;
            case WEDNESDAY -> 4;
            case THURSDAY -> 5;
            case FRIDAY -> 6;
            case SATURDAY -> 7;
            default -> 0;
         };
    }

    public static DaysOfWeekEnum getDaysOfWeekEnum(String daysOfWeek) {
        return Arrays.asList(DaysOfWeekEnum.values()).stream().
            filter(v -> v.getDaysOfWeek().equalsIgnoreCase(daysOfWeek)).findFirst().
            orElseThrow(() -> new BusinessExceptions("Dia da semana invalido"));     
    }
    
}
