package br.com.jjohnys.psychological_care.psychological_support.application.usecases.change_patient_status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jjohnys.psychological_care.exceptions.PatientStatusException;
import br.com.jjohnys.psychological_care.psychological_support.domain.Patient;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.PatientStatusEnum;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PatientRepository;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PatientScheduleRepository;

public abstract class ChangePacienteStatusInterector {   

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientScheduleRepository patientScheduleRepository;
    protected PatientStatusEnum patientStatusEnum;

    public void execute(String patientId) throws PatientStatusException {
        Patient patient = patientRepository.findPatientById(patientId);
        validate(patient);
        change(patient);
    }

    private void validate(Patient patient) {
        patient.validateChangeStatus(patient, patientStatusEnum);
    }

    private void change(Patient patient) {
        patientScheduleRepository.deleteByPatientId(patient.getId());
        patientRepository.changeStatusPatient(patient.getId(), patientStatusEnum);
    }



}
