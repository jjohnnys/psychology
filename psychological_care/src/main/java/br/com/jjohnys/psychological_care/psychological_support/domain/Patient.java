package br.com.jjohnys.psychological_care.psychological_support.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.jjohnys.psychological_care.exceptions.PatientStatusException;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.GenderEnum;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.PatientStatusEnum;
import br.com.jjohnys.psychological_care.psychological_support.domain.value_objects.CPF;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = "id")
@ToString
public class Patient {

    private String id;
    private String name;
    private CPF cpf;
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

    public Patient(String id, String name, CPF cpf, String rg, LocalDate dateBirth, Integer price, String schooling,
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

    public Long getAge() {
        return ChronoUnit.YEARS.between(this.dateBirth, LocalDate.now());
    }

    public void validateChangeStatus(Patient patient, PatientStatusEnum newPatientStatus) throws PatientStatusException {
        if (newPatientStatus == PatientStatusEnum.TREATMENT_FINISHED) canFinishTratment();
        else if(newPatientStatus == PatientStatusEnum.TREATMENT_FINISHED) validateStopTratment();
        else throw new PatientStatusException("Status não mapeado em fluxo de negocio");
    }

    private void canFinishTratment() throws PatientStatusException {
        boolean canFinish = (this.status == PatientStatusEnum.TREATMENT_FINISHED || this.status == PatientStatusEnum.TREATMENT_STOPED);
        if(!canFinish) throw new PatientStatusException("Nao pode finalizar atendimento de um paciente sem estar com o atendimento em andamento");        
    }

    private void validateStopTratment() throws PatientStatusException {
        boolean canStop = !(this.status == PatientStatusEnum.TREATMENT_STOPED);
        if(!canStop) throw new PatientStatusException("Só é possivel interromper um tratamento quando o tratamento esta em andamento");
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
