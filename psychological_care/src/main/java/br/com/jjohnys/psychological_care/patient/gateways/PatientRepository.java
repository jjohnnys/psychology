package br.com.jjohnys.psychological_care.patient.gateways;

import java.util.List;

import br.com.jjohnys.psychological_care.patient.domain.Contact;
import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.Responsible;

public interface PatientRepository {

    void insertPatient(Patient patient, List<Contact> contactsPatient, Responsible responsible, List<Contact> contactsResponsible);

    void updatePatient(Patient patient, List<Contact> contactsPatient, Responsible responsible, List<Contact> contactsResponsible);

    List<Patient> findPatientByName(String name);

    Patient findPatientById(String id); 
}
