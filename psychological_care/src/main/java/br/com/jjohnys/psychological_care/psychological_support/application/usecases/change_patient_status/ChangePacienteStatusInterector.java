package br.com.jjohnys.psychological_care.psychological_support.application.usecases.change_patient_status;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.jjohnys.psychological_care.psychological_support.domain.Patient;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.PatientStatusEnum;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PatientRepository;


public abstract class ChangePacienteStatusInterector {    

    @Autowired
    private PatientRepository patientRepository;
    protected PatientStatusEnum patientStatusEnum;

    abstract void validate(Patient patient);
    abstract void change(Patient patient);
    
    public void execute(String patientId) {
        Patient patient = patientRepository.findPatientById(patientId);
        validate(patient);
        change(patient);
        changeRepository(patientId);        
    }  

    void changeRepository(String patientId) {
        patientRepository.changeStatusPatient(patientId, patientStatusEnum);
    }

}
