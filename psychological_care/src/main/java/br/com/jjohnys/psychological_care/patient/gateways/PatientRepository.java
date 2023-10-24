package br.com.jjohnys.psychological_care.patient.gateways;

import java.util.List;

import br.com.jjohnys.psychological_care.patient.domain.Contact;
import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.Responsible;

public interface PatientRepository {

    void insertPatient(Patient patient, List<Contact> contactsPatient, List<Responsible> responsibles, List<Contact> contactsResponsible);

    void updatePatient(Patient patient, List<Contact> contactsPatient, List<Responsible> responsibles, List<Contact> contactsResponsible);

    List<Patient> findPatientByName(String name);

    Patient findPatientById(String id); 

    public Patient findPatientByCpf(String cpf);
    
    public boolean existisOtherPatientWithSameCPF(String id, String cpf);

    public boolean existisOtherResponsibleWithSameCPF(String id, String cpf);
}
