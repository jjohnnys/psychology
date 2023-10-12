package br.com.jjohnys.psychological_care;

import java.util.UUID;

import br.com.jjohnys.psychological_care.patient.application.dto.PatientDTO;
import br.com.jjohnys.psychological_care.patient.domain.Contact;
import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.enums.Gender;
import br.com.jjohnys.psychological_care.utils.DateUtils;

public final class Constants {

    public static Patient newPatient() {

        String patientId = UUID.randomUUID().toString();
        return new Patient(patientId, "Ikki", "985.698.497-22", "99.689.695-5", DateUtils.stringDateToLocalDate("1987-04-15"), 100, "PosGraduado", Gender.MALE, "Gercia, 100", "Forte");        
        
    }

    public static Contact newContact(String ownContatctId) {

        return Contact.buildPatientContact(UUID.randomUUID().toString(), "ikki@mail.com", "85 98745", ownContatctId);
                
    }

    public static PatientDTO newPatientDTO() {

        PatientDTO patientDTO = new PatientDTO(null, "Ikki", "985.698.497-22", "99.689.695-5", DateUtils.stringDateToLocalDate("1987-04-15"), 100, "PosGraduado", Gender.MALE.getDescription(), "Ilha dificil", null, "Muito forte", null);

        return null;
    }
    

    
}
