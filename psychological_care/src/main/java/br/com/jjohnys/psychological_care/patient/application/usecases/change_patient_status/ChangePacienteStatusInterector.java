package br.com.jjohnys.psychological_care.patient.application.usecases.change_patient_status;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.enums.PatientStatusEnum;
import br.com.jjohnys.psychological_care.patient.gateways.PatientRepository;


public abstract class ChangePacienteStatusInterector {    

    @Autowired
    private PatientRepository patientRepository;

    abstract void validateChangeStatus(Patient patient, PatientStatusEnum newPatientStatus);
    abstract void change(Patient patient, PatientStatusEnum newPatientStatus);
    
    public void execute(String patientId, String status) {
        PatientStatusEnum newPatientStatus = PatientStatusEnum.getStatusPatientEnum(status);        
        Patient patient = patientRepository.findPatientById(patientId);
        validateChangeStatus(patient, newPatientStatus);
        change(patient, newPatientStatus);
        changeStatuPatient(patientId, newPatientStatus);
        
    }  

    void changeStatuPatient(String patientId, PatientStatusEnum newPatientStatus) {
        patientRepository.changeStatusPatient(patientId, newPatientStatus);
    }

}
