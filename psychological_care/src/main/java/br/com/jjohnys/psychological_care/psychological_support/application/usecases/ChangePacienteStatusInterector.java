package br.com.jjohnys.psychological_care.psychological_support.application.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jjohnys.psychological_care.exceptions.PatientStatusException;
import br.com.jjohnys.psychological_care.psychological_support.domain.Patient;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.PatientStatusEnum;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PatientRepository;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PatientScheduleRepository;

@Service
public class ChangePacienteStatusInterector {   

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientScheduleRepository patientScheduleRepository;

    public void execute(String patientId, PatientStatusEnum patientStatusEnum) throws PatientStatusException {
        Patient patient = patientRepository.findPatientById(patientId);
        patient.validateChangeStatus(patient, patientStatusEnum);
        change(patient, patientStatusEnum);
    }

    private void change(Patient patient, PatientStatusEnum patientStatusEnum) {
        patientScheduleRepository.deleteByPatientId(patient.getId());
        patientRepository.changeStatusPatient(patient.getId(), patientStatusEnum);
    }



}
