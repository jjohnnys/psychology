package br.com.jjohnys.psychological_care.repository;

import br.com.jjohnys.psychological_care.domain.Patient;

public interface PatientRepository {

    int insertPatient(String id, String name, String cpf, String rg, String email, String date_birth, Integer priceService, String  observation);

    Patient findPatientByName(String name);

    Patient findPatientById(String id);
    
}
