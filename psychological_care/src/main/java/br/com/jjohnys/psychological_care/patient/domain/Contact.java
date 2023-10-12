package br.com.jjohnys.psychological_care.patient.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class Contact {

    private String id;
    private String email;
    private String telephone;
    private String patientId;
    private String responsibleId;


    public static Contact buildPatientContact(String id, String email, String telephone, String patient_id)  {
        return new Contact(id, email, telephone, patient_id, null);
    }

    public static Contact buildResponsibleContact(String id, String email, String telephone, String responsibel_id)  {
        return new Contact(id, email, telephone, null, responsibel_id);
    }
    
}
