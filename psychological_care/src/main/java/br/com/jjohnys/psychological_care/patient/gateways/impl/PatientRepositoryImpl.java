package br.com.jjohnys.psychological_care.patient.gateways.impl;

import java.util.List;

import org.springframework.stereotype.Component;

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
        if(responsible == null)
            responsibleJDBC.insertResponsible(responsible);
            contactJDBC.saveAll(contactsResponsible);
            
    }

    @Override
    public int updatePatient(Patient patient) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePatient'");
    }

    @Override
    public List<Patient> findPatientByName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findPatientByName'");
    }

    @Override
    public Patient findPatientById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findPatientById'");
    }
    
}
