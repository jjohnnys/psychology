package br.com.jjohnys.psychological_care.psychological_support.gateways;

import br.com.jjohnys.psychological_care.psychological_support.domain.Payment;

public interface PaymentRepository {

    int insert(Payment payment);
    int delete(Payment payment);
    Payment getById(String id);
    Payment getByPatientId(String patientId);
    Payment getByYear(int year);
    Payment getByYearEndMonth(int year, int month);
}
