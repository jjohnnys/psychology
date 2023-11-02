package br.com.jjohnys.psychological_care.patient.domain.enums;

import java.util.Arrays;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PatientStatusEnum {
    
    IN_TREATMENT("Em tratamento"),
    TREATMENT_FINISHED("Tratamento Finalizado"),
    TREATMENT_STOPED("Tratamento interrompido");

    @Getter
    private String status;


    public static PatientStatusEnum getStatusPatientEnum(String value) {
        return Arrays.asList(PatientStatusEnum.values()).stream().
            filter(v -> v.getStatus().equalsIgnoreCase(value)).findFirst().
            orElseThrow( () -> new BusinessExceptions("Tipo status nao encontrado"));      
    }



}
