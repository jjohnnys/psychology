package br.com.jjohnys.psychological_care.psychological_support.infrastructure.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.jjohnys.psychological_care.psychological_support.domain.Payment;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PaymentRepository;
import br.com.jjohnys.psychological_care.utils.DateUtils;

@Repository
public class PaymentJDBC implements PaymentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PatientJDBC patientJDBC;

    public int insert(Payment payment) {
        return jdbcTemplate.update("insert into payment (id, patient_id, payment_year, payment_month, payment_date, payment_value) values (?, ?, ?, ?, ?, ?)", 
            genereteID(payment.getId()), payment.getPatient().getId(), payment.getYear(), payment.getMonth(), payment.getDate(), payment.getValue().toString());
    }

    public int delete(Payment payment) {
        return jdbcTemplate.update("delete from payment where id = ?", payment.getId());
    }

    public Payment getById(String id) {
        return jdbcTemplate.queryForObject("select * from payment where id = ?", (rs, rowNum) -> createPayment(rs), new Object[]{id});
    }

    public Payment getByPatientId(String patientId) {
        return jdbcTemplate.queryForObject("select * from payment where patient_id = ?", (rs, rowNum) -> createPayment(rs), new Object[]{patientId});
    }

    public Payment getByYear(int year) {
        return jdbcTemplate.queryForObject("select * from payment where payment_year = ?", (rs, rowNum) -> createPayment(rs), new Object[]{year});
    }

    public Payment getByYearEndMonth(int year, int month) {
        return jdbcTemplate.queryForObject("select * from payment where payment_year = ?, and payment_month = ?", (rs, rowNum) -> createPayment(rs), new Object[]{year, month});
    }

    private Payment createPayment(ResultSet rs) throws SQLException {
        return new Payment(
            rs.getString("id"),
            patientJDBC.findPatientById(rs.getString("patient_id")),
            rs.getInt("payment_year"),
            rs.getInt("payment_month"),
            DateUtils.stringDateToLocalDate(rs.getString("payment_date")),
            rs.getBigDecimal("payment_value"));
    }

    private String genereteID(String id) {
        if(id == null || id.isBlank()) return UUID.randomUUID().toString();
        return id;
    }
}
