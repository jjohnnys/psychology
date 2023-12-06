package br.com.jjohnys.psychological_care.psychological_support.domain;

import br.com.jjohnys.psychological_care.psychological_support.domain.value_objects.EMAIL;
import br.com.jjohnys.psychological_care.psychological_support.domain.value_objects.TELEFONE;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class Contact {

    private String id;
    private EMAIL email;
    private TELEFONE telephone;
    private String patientId;
    private String responsibleId;


    public static Contact buildPatientContact(String id, EMAIL email, TELEFONE telephone, String patient_id)  {
        return new Contact(id, email, telephone, patient_id, null);
    }

    public static Contact buildResponsibleContact(String id, EMAIL email, TELEFONE telephone, String responsibel_id)  {
        return new Contact(id, email, telephone, null, responsibel_id);
    }
    
}
