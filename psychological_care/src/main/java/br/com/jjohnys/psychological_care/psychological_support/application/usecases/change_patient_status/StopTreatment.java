package br.com.jjohnys.psychological_care.psychological_support.application.usecases.change_patient_status;

import org.springframework.stereotype.Service;

import br.com.jjohnys.psychological_care.psychological_support.domain.enums.PatientStatusEnum;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StopTreatment extends ChangePacienteStatusInterector {
    
    @Override
    void setPatientStatusEnum() {
        super.patientStatusEnum = PatientStatusEnum.TREATMENT_STOPED;
    }
    
}
