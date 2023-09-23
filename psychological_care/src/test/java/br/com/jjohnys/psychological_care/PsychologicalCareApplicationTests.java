package br.com.jjohnys.psychological_care;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;
import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.Plan;
import br.com.jjohnys.psychological_care.patient.domain.enums.Gender;
import br.com.jjohnys.psychological_care.patient.repository.PatientRepository;
import br.com.jjohnys.psychological_care.patient.repository.PlanRepository;
import br.com.jjohnys.psychological_care.psychological_support.Support;
import br.com.jjohnys.psychological_care.psychological_support.domain.ReceiptData;
import br.com.jjohnys.psychological_care.psychological_support.repository.SupportRepository;
import br.com.jjohnys.psychological_care.psychological_support.service.ReceiptSupportService;
import br.com.jjohnys.psychological_care.utils.DateUtils;

@SpringBootTest
class PsychologicalCareApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private PlanRepository planRepository;
	@Autowired
	private SupportRepository supportRepository;
	@Autowired
	private ReceiptSupportService receiptSupportService;
	

	@Test
	public void sholdReturnTotalValueOfSuportsbyPeriod() {

		Patient yoga = createPatiente("Yoga", "747.887.996-22", "23.335.335-1");

		createSupport(yoga, LocalDateTime.of(2023, 8, 07, 10, 30));
		createSupport(yoga, LocalDateTime.of(2023, 8, 14, 10, 30));
		createSupport(yoga, LocalDateTime.of(2023, 8, 21, 10, 30));
		createSupport(yoga, LocalDateTime.of(2023, 8, 28, 10, 30));

		ReceiptData receiptData = receiptSupportService.getReceiptDataForPatienteByPeriod(yoga, LocalDate.of(2023, 8, 1), LocalDate.of(2023, 8, 31));
		assertEquals(400, receiptData.getTotalValue(), "Total ok");

	}

	@Test
	public void sholdReturnExceptionWhenPatientDontHaveSupport() {

		Patient miro = createPatiente("Miro", "114.421.998-88", "77+887+987-3");		
		BusinessExceptions exception = assertThrows(BusinessExceptions.class, () -> receiptSupportService.getReceiptDataForPatienteByPeriod(miro, LocalDate.of(2023, 8, 1), LocalDate.of(2023, 8, 31)));		
		assertTrue(exception.getMessage().equals("Paciente sem atendimento cadastrado neste periodo"));

	}

	private Patient createPatiente(String nome, String cpf, String rg) {
		Plan plan = planRepository.getPlanById("plan_id");
		Patient patient = new Patient(UUID.randomUUID().toString(), nome, cpf, rg, DateUtils.stringDateToLocalDate("1987-04-20"), plan, "Superior", Gender.MALE, "Siberia", null, "Gosta do frio");
		patientRepository.insertPatient(patient);
		return patient;
	}

	private void createSupport(Patient patient, LocalDateTime time) {
		
		Support support = new Support(UUID.randomUUID().toString(), patient, time, null);
		supportRepository.insertSupport(support);

	}

}
