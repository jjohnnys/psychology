package br.com.jjohnys.psychological_care.patient.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id")
public class Responsible {


    private String id;
    private String name;
    private String cpf;
    private String rg;
    private LocalDate dateBirth;
    private String parentege;
    private Patient patient;
    private List<Contact> contacts = new ArrayList<Contact>();

    public Responsible(String id, String name, String cpf, String rg, LocalDate dateBirth, String parentege,
            Patient patient) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.rg = rg;
        this.dateBirth = dateBirth;
        this.parentege = parentege;
        this.patient = patient;
    }

    public List<Contact> addContact(Contact contact) {
        this.contacts.add(contact);
        return Collections.unmodifiableList(contacts);
    }

    public List<Contact> addContacts(List<Contact> contacts) {
        this.contacts.addAll(contacts);
        return Collections.unmodifiableList(contacts);
    }


    
}
