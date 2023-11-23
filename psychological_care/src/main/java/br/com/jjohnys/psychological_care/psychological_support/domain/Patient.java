package br.com.jjohnys.psychological_care.psychological_support.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.jjohnys.psychological_care.psychological_support.domain.enums.GenderEnum;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.PatientStatusEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = "id")
@ToString
public class Patient {

    private String id;
    private String name;
    private String cpf;
    private String rg;
    private LocalDate dateBirth;
    private Integer price;
    private String schooling;
    private GenderEnum gender;
    private String address;
    private PatientStatusEnum status;
    private String observation;
    private List<Responsible> responsibles = new ArrayList<Responsible>();;
    private List<Contact> contacts = new ArrayList<Contact>();

    public Patient(String id, String name, String cpf, String rg, LocalDate dateBirth, Integer price, String schooling,
            GenderEnum gender, String address, PatientStatusEnum status, String observation) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.rg = rg;
        this.dateBirth = dateBirth;
        this.price = price;
        this.schooling = schooling;
        this.gender = gender;
        this.address = address;
        this.status = status;
        this.observation = observation;
    }

    public List<Responsible> addResponsible(Responsible responsible) {
        this.responsibles.add(responsible);
        return Collections.unmodifiableList(responsibles);
    }

    public List<Responsible> addAllResponsible(List<Responsible> responsibles) {
        this.responsibles.addAll(responsibles);
        return Collections.unmodifiableList(responsibles);
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
