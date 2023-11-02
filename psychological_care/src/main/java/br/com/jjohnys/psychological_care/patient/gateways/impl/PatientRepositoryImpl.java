package br.com.jjohnys.psychological_care.patient.gateways.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;
import br.com.jjohnys.psychological_care.patient.domain.Contact;
import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.Responsible;
import br.com.jjohnys.psychological_care.patient.domain.enums.PatientStatusEnum;
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
    public void insertPatient(Patient patient, List<Contact> contactsPatient, List<Responsible> responsibles, List<Contact> contactsResponsible) {    

        patientJDBC.insertPatient(patient);
        contactJDBC.saveAll(contactsPatient);
        if(responsibles != null) responsibles.forEach(responsible -> {            
            responsibleJDBC.insertResponsible(responsible);
            contactJDBC.saveAll(contactsResponsible);        
        });
        
            
    }

    @Override
    public void updatePatient(Patient patient, List<Contact> contactsPatient, List<Responsible> responsibles, List<Contact> contactsResponsible) {
        patientJDBC.updatePatient(patient);

        contactsPatient.forEach(cp -> {
            if(!cp.getId().isBlank()) contactJDBC.updateContact(cp);
            else contactJDBC.insertContact(cp);
        });        
        if(responsibles != null) responsibles.forEach(responsible -> {
            if(responsible.getId() != null && !responsible.getId().isBlank()) responsibleJDBC.updateResponsible(responsible);
            else responsibleJDBC.insertResponsible(responsible);
            contactsResponsible.forEach(cr -> {
                if(!cr.getId().isBlank()) contactJDBC.updateContact(cr);
                else contactJDBC.insertContact(cr);
            });
        });
    }

    @Override
    public List<Patient> findPatientByName(String name) {
        List<Patient> patients = patientJDBC.findPatientByName(name);
        if(patients == null || patients.isEmpty()) 
            throw new BusinessExceptions(String.format("Não econtrado algum paciente %s", name));
        
        return patients;
    }

    @Override
    public Patient findFullPatientById(String id) throws BusinessExceptions {
        return this.getFullPatiente(patientJDBC.findPatientById(id));        
    }

    @Override
    public Patient findPatientById(String id) throws BusinessExceptions {
        return patientJDBC.findPatientById(id);        
    }

    @Override
    public Patient findPatientByCpf(String cpf) throws BusinessExceptions {
        return this.getFullPatiente(patientJDBC.findPatientByCPF(cpf));        
    }

    private Patient getFullPatiente(Patient patient) {
        if(patient == null)
            return null;
        List<Contact> contactsPatient = contactJDBC.getContactByPatientId(patient.getId());
        if(contactsPatient == null)
            throw new BusinessExceptions(String.format("Não existe contato para o paciente %s", patient.getName()));
        patient.addContacts(contactsPatient);
        List<Responsible> responsibles = responsibleJDBC.findResponsiblesByPatientId(patient.getId());
        if(!responsibles.isEmpty()) {
            responsibles.forEach(responsible -> {
                List<Contact> contactsResponsible = contactJDBC.getContactByResponsibelId(responsible.getId());
                if(contactsResponsible == null) 
                    throw new BusinessExceptions(String.format("Não existe contato para o responsavel %s", responsible.getName()));
                responsible.addContacts(contactsResponsible);
            });
            patient.addAllResponsible(responsibles);
        }

        return patient;

    }

    @Override
    public boolean existisOtherPatientWithSameCPF(String id, String cpf) {
        return patientJDBC.existisOtherPatientWithSameCPF(id, cpf);
    }

    @Override
    public boolean existisOtherResponsibleWithSameCPF(String id, String cpf) {
        return responsibleJDBC.existisOtherResponsibleWithSameCPF(id, cpf);
    }

    @Override
    public int chengeStatusPatient(String patientId, PatientStatusEnum status) {        
        return patientJDBC.chengeStatusPatient(patientId, status);
    }

    

    
    
}
