package br.com.jjohnys.psychological_care.patient.gateways;

import java.util.List;

import br.com.jjohnys.psychological_care.patient.domain.Patient;

public interface PatientRepository {

    int insertPatient(Patient patient);

    int updatePatient(Patient patient);

    List<Patient> findPatientByName(String name);

    Patient findPatientById(String id);    
}
