package br.com.jjohnys.psychological_care.patient.repository;

import java.time.LocalDate;
import java.util.List;

import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.psychological_support.domain.ReceiptData;

public interface ReceiptDataRepository {

    List<ReceiptData> getReceiptDataForPatienteByPeriod(Patient patient, LocalDate dateStart, LocalDate dateEnd);
    
}
