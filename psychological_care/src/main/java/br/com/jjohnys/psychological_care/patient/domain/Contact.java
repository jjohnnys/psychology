package br.com.jjohnys.psychological_care.patient.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class Contact {

    private String id;
    private String name;
    private String email;
    private String telephone;
    private String patientId;
    private String responsibleId;


    public static Contact buildPatientContact(String id, String name, String email, String telephone, String patient_id)  {
        return new Contact(id, name, email, telephone, patient_id, null);
    }

    public static Contact buildResponsibleContact(String id, String name, String email, String telephone, String responsibel_id)  {
        return new Contact(id, name, email, telephone, null, responsibel_id);
    }
    
}
