package br.com.jjohnys.psychological_care.psychological_support.application.usecases.change_patient_status;

import org.springframework.stereotype.Service;

import br.com.jjohnys.psychological_care.psychological_support.domain.Patient;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PatientScheduleRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FinishTreatment extends ChangePacienteStatusInterector {
    
    private PatientScheduleRepository patientScheduleRepository;

    @Override
    void validate(Patient patient) {
        patientStatusEnum = patientStatusEnum.TREATMENT_FINISHED;
        patient.validateChangeStatus(patient, patientStatusEnum);        
    }

    @Override
    void change(Patient patient) {
        patientScheduleRepository.deleteByPatientId(patient.getId());
    }

    

    

    


    
}
