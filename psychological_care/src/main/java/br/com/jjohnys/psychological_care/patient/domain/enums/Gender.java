package br.com.jjohnys.psychological_care.patient.domain.enums;

public enum Gender {

    MALE("masculino"),
    FEMALE("feminino");

    private String description;

    private Gender(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public static Gender getGenderEnum(String value) {
        for (Gender gender : Gender.values()) {
            if(gender.getDescription().equalsIgnoreCase(value)) return gender;            
        }
        return null;        
    }
}
