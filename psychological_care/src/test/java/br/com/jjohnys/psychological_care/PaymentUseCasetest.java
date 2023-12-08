package br.com.jjohnys.psychological_care;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;
import br.com.jjohnys.psychological_care.psychological_support.application.dto.PaymentDTO;
import br.com.jjohnys.psychological_care.psychological_support.application.usecases.MakePaymentInterector;
import br.com.jjohnys.psychological_care.psychological_support.domain.Payment;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PaymentRepository;

@SpringBootTest
public class PaymentUseCasetest {

    @Autowired
    private MakePaymentInterector makePaymentInterector;
    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void makePaymentThenReturnPayment() {
        PaymentDTO paymentDto = new PaymentDTO(null, "paciente_1", 2023, 12, LocalDate.of(2023, 12, 6), new BigDecimal("400"));
        makePaymentInterector.execute(paymentDto);
        Payment payment = paymentRepository.getByPatientId("paciente_1");
        assertEquals(new BigDecimal("400.00"), payment.getValue());
    }
    
    @Test
    public void makePaymentOfPatientWhithYtatmentFinichedThenReturnException() {
        PaymentDTO paymentDto = new PaymentDTO(null, "paciente_5", 2023, 12, LocalDate.of(2023, 12, 6), new BigDecimal("400"));
        assertThrows(BusinessExceptions.class, () -> makePaymentInterector.execute(paymentDto));
    }
}
