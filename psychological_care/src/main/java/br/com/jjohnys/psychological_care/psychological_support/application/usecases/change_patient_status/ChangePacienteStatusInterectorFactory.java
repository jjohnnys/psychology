package br.com.jjohnys.psychological_care.psychological_support.application.usecases.change_patient_status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.jjohnys.psychological_care.exceptions.PatientStatusException;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.PatientStatusEnum;

@Component
public class ChangePacienteStatusInterectorFactory {

    @Autowired
    private FinishTreatment finishTreatment;
    @Autowired
    private StopTreatment stopTreatment;
    
    public ChangePacienteStatusInterector create(PatientStatusEnum newPatientStatus) throws PatientStatusException {
        return switch(newPatientStatus) {
            case TREATMENT_FINISHED -> finishTreatment;
            case TREATMENT_STOPED -> stopTreatment;
            default -> throw new PatientStatusException("Status nao suportado");
        };
    }
}
