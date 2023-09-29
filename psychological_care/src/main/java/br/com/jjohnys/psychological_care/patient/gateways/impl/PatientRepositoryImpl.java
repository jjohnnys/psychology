package br.com.jjohnys.psychological_care.patient.gateways.impl;

import java.util.List;

import br.com.jjohnys.psychological_care.patient.domain.Contact;
import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.Responsible;
import br.com.jjohnys.psychological_care.patient.gateways.PatientRepository;

public class PatientRepositoryImpl implements PatientRepository {

    

    @Override
    public int insertPatient(Patient patient, List<Contact> contactsPatient, Responsible responsible, List<Contact> contactsResponsible) {
        



        throw new UnsupportedOperationException("Unimplemented method 'insertPatient'");
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
