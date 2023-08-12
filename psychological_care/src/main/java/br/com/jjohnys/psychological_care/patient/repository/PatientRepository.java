package br.com.jjohnys.psychological_care.patient.repository;

import br.com.jjohnys.psychological_care.patient.domain.Patient;

public interface PatientRepository {

    int insertPatient(Patient patient);

    int updatePatient(Patient patient);

    Patient findPatientByName(String name);

    Patient findPatientById(String id);
    
}
