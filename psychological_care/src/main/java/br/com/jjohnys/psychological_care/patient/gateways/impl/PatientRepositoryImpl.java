package br.com.jjohnys.psychological_care.patient.gateways.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;
import br.com.jjohnys.psychological_care.patient.domain.Contact;
import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.Responsible;
import br.com.jjohnys.psychological_care.patient.gateways.PatientRepository;
import br.com.jjohnys.psychological_care.patient.infrastructure.jdbc.ContactJDBC;
import br.com.jjohnys.psychological_care.patient.infrastructure.jdbc.PatientJDBC;
import br.com.jjohnys.psychological_care.patient.infrastructure.jdbc.ResponsibleJDBC;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PatientRepositoryImpl implements PatientRepository {

    private PatientJDBC patientJDBC;
    private ResponsibleJDBC responsibleJDBC;
    private ContactJDBC contactJDBC;
    

    @Override
    public void insertPatient(Patient patient, List<Contact> contactsPatient, Responsible responsible, List<Contact> contactsResponsible) {    

        patientJDBC.insertPatient(patient);
        contactJDBC.saveAll(contactsPatient);
        if(responsible != null) {
            responsibleJDBC.insertResponsible(responsible);
            contactJDBC.saveAll(contactsResponsible);
        }
            
    }

    @Override
    public void updatePatient(Patient patient, List<Contact> contactsPatient, Responsible responsible, List<Contact> contactsResponsible) {
        patientJDBC.updatePatient(patient);

        contactsPatient.forEach(cp -> {
            if(cp.getId().isBlank()) contactJDBC.updateContact(cp);
            else contactJDBC.insertContact(cp);
        });        
        if(responsible == null) {
            responsibleJDBC.updateResponsible(responsible);
            contactsResponsible.forEach(cr -> {
                if(cr.getId().isBlank()) contactJDBC.updateContact(cr);
                else contactJDBC.insertContact(cr);
            });
        }
    }

    @Override
    public List<Patient> findPatientByName(String name) {
        List<Patient> patients = patientJDBC.findPatientByName(name);
        if(patients == null || patients.isEmpty()) 
            throw new BusinessExceptions(String.format("Não econtrado algum paciente %s", name));
        
        return patients;
    }

    @Override
    public Patient findPatientById(String id) throws BusinessExceptions {

        Patient patient = patientJDBC.findPatientById(id);
        if(patient == null)
            throw new BusinessExceptions(String.format("Não existe paciente com o ID %s", id));
        List<Contact> contactsPatient = contactJDBC.getContactByPatientId(patient.getId());
        if(contactsPatient == null)
            throw new BusinessExceptions(String.format("Não existe contato para o paciente %s", patient.getName()));
        patient.addContacts(contactsPatient);
        List<Responsible> responsibles = responsibleJDBC.findResponsiblesByPatientId(id);
        if(!responsibles.isEmpty()) {
            responsibles.forEach(responsible -> {
                List<Contact> contactsResponsible = contactJDBC.getContactByResponsibelId(patient.getId());
                if(contactsResponsible == null) 
                    throw new BusinessExceptions(String.format("Não existe contato para o paciente %s", patient.getName()));
                responsible.addContacts(contactsResponsible);
            });
            patient.addAllResponsible(responsibles);
        }

        return patient;
    }
    
}
