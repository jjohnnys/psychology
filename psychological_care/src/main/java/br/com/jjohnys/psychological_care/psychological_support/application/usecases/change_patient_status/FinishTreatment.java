package br.com.jjohnys.psychological_care.psychological_support.application.usecases.change_patient_status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jjohnys.psychological_care.exceptions.PatientStatusException;
import br.com.jjohnys.psychological_care.psychological_support.domain.Patient;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.PatientStatusEnum;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PatientScheduleRepository;

@Service
public class FinishTreatment extends ChangePacienteStatusInterector {

    @Autowired
    private PatientScheduleRepository patientScheduleRepository;

    @Override
    public void validateChangeStatus(Patient patient, PatientStatusEnum newPatientStatus) {
        if(newPatientStatus == PatientStatusEnum.TREATMENT_FINISHED && 
            (patient.getStatus() == PatientStatusEnum.TREATMENT_FINISHED || patient.getStatus() == PatientStatusEnum.TREATMENT_STOPED)) {
                throw new PatientStatusException("Nao pode finalizar atendimento de um paciente sem estar com o atendimento em andamento");
        }
    }

    @Override
    void change(Patient patient, PatientStatusEnum newPatientStatus) {
        patientScheduleRepository.deleteByPatientId(patient.getId());
        changeStatuPatient(patient.getId(), newPatientStatus);
    }

    


    
}
