package br.com.jjohnys.psychological_care.psychological_support.gateways;

import java.util.List;

import br.com.jjohnys.psychological_care.psychological_support.domain.Contact;
import br.com.jjohnys.psychological_care.psychological_support.domain.Patient;
import br.com.jjohnys.psychological_care.psychological_support.domain.Responsible;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.PatientStatusEnum;
import br.com.jjohnys.psychological_care.psychological_support.domain.value_objects.CPF;

public interface PatientRepository {

    void insertPatient(Patient patient, List<Contact> contactsPatient, List<Responsible> responsibles, List<Contact> contactsResponsible);
    void updatePatient(Patient patient, List<Contact> contactsPatient, List<Responsible> responsibles, List<Contact> contactsResponsible);
    List<Patient> findPatientByName(String name);
    Patient findPatientById(String id);
    Patient findFullPatientById(String id);
    Patient findPatientByCPF(CPF cpf);    
    boolean existisOtherPatientWithSameCPF(String id, CPF cpf);
    boolean existisOtherResponsibleWithSameCPF(String id, CPF cpf);
    int changeStatusPatient(String patientId, PatientStatusEnum status);
}
