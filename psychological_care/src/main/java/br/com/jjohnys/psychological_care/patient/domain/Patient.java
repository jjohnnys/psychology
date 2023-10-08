package br.com.jjohnys.psychological_care.patient.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.jjohnys.psychological_care.patient.domain.enums.Gender;
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
    private Gender gender;
    private String address;
    private String observation;
    private Responsible responsible;
    private List<Contact> contacts = new ArrayList<>(null);

    public Patient(String id, String name, String cpf, String rg, LocalDate dateBirth, Integer price, String schooling,
            Gender gender, String address, String observation) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.rg = rg;
        this.dateBirth = dateBirth;
        this.price = price;
        this.schooling = schooling;
        this.gender = gender;
        this.address = address;
        this.observation = observation;
    }

    public void setResponsible(Responsible responsible) {
        this.responsible = responsible;
    }

    public List<Contact> addContact(Contact contact) {
        this.contacts.add(contact);
        return Collections.unmodifiableList(contacts);
    }

    

    
}
