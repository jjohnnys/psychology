package br.com.jjohnys.psychological_care.psychological_support.gateways;

import java.time.LocalDate;
import java.util.List;

import br.com.jjohnys.psychological_care.psychological_support.domain.Patient;
import br.com.jjohnys.psychological_care.report.domain.ReceiptData;

public interface ReceiptDataRepository {

    List<ReceiptData> getReceiptDataForPatienteByPeriod(Patient patient, LocalDate dateStart, LocalDate dateEnd);
    
}
