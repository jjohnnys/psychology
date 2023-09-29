package br.com.jjohnys.psychological_care.patient.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContactDTO {

    private String id;
    private String name;
    private String email;
    private String telephone;
    private String parentage;
    private String patientId;
    private String responsibleId;
    
}
