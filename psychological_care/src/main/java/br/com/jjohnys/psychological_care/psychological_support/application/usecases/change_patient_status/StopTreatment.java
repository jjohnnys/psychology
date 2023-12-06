package br.com.jjohnys.psychological_care.psychological_support.application.usecases.change_patient_status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jjohnys.psychological_care.psychological_support.domain.Patient;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PatientScheduleRepository;

@Service
public class StopTreatment extends ChangePacienteStatusInterector {

    @Autowired
    private PatientScheduleRepository patientScheduleRepository;

    @Override
    void validate(Patient patient) {
        patientStatusEnum = patientStatusEnum.TREATMENT_STOPED;
        patient.validateChangeStatus(patient, patientStatusEnum);        
    }

    @Override
    void change(Patient patient) {
        patientScheduleRepository.deleteByPatientId(patient.getId());
    }

    

    
}
