package br.com.jjohnys.psychological_care.patient.gateways;

import java.util.List;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;
import br.com.jjohnys.psychological_care.patient.domain.Contact;
import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.Responsible;
import br.com.jjohnys.psychological_care.patient.domain.enums.PatientStatusEnum;

public interface PatientRepository {

    void insertPatient(Patient patient, List<Contact> contactsPatient, List<Responsible> responsibles, List<Contact> contactsResponsible);

    void updatePatient(Patient patient, List<Contact> contactsPatient, List<Responsible> responsibles, List<Contact> contactsResponsible);

    List<Patient> findPatientByName(String name);

    Patient findPatientById(String id); 

    Patient findFullPatientById(String id);

    Patient findPatientByCpf(String cpf);
    
    boolean existisOtherPatientWithSameCPF(String id, String cpf);

    boolean existisOtherResponsibleWithSameCPF(String id, String cpf);

    int chengeStatusPatient(String patientId, PatientStatusEnum status);
}
