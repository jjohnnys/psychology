package br.com.jjohnys.psychological_care.psychological_support.domain.enums;

import java.util.Arrays;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;

public enum GenderEnum {

    MALE("masculino"),
    FEMALE("feminino");

    private String description;

    private GenderEnum(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public static GenderEnum getGenderEnum(String value) {
        return Arrays.asList(GenderEnum.values()).stream().
            filter(v -> v.getDescription().equalsIgnoreCase(value)).findFirst().
            orElseThrow(() -> new BusinessExceptions("Genero Invalido"));     
    }
}
