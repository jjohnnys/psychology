package br.com.jjohnys.psychological_care.patient.infrastructure.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.jjohnys.psychological_care.patient.domain.Responsible;

@Repository
public class ResponsibleJDBC {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PatientJDBC patientJDBC;

    public void insertResponsible(Responsible responsible) {

        jdbcTemplate.update("insert into responsible (id, name, cpf, rg, date_birth, parentenge = ?, patient_id) values (?, ?, ?, ?, ?, ?)", 
        responsible.getId(), responsible.getName(), responsible.getCpf(), responsible.getRg(), responsible.getDateBirth(), responsible.getPatient().getId());      

    }

    public void updateResponsible(Responsible responsible) {
        jdbcTemplate.update("update responsible set name = ?, cpf = ?, rg = ? date_birth = ?, parentenge = ?, patient_id = ? where id = ?",
         responsible.getName(), responsible.getCpf(), responsible.getRg(), responsible.getDateBirth(),responsible.getPatient().getId() , responsible.getId());
    }

    public Responsible findResponsiblesByCPF(String cpf) {
        String query = "select * from where cpf = ? ";
      return jdbcTemplate.queryForObject(query, (rs, rowNum) -> createResponsible(rs), new Object[]{cpf});

    }

    public List<Responsible> findResponsiblesByPatientId(String patientId) {
        String query = "select " +
                        " id, name, cpf, rg, date_birth, parentenge, patient_id " +
                        " from  " +
                        " responsible , " +
                        " patient p " +
                        " where " +
                        " patient_id = ? ";
      return jdbcTemplate.query(query, (rs, rowNum) -> createResponsible(rs), new Object[]{patientId});
    }

    public List<Responsible> findResponsiblesByPatientName(String patientName) {
        String query = "select " +
                        " r.id, r.name, r.cpf, r.rg, r.date_birth, parentenge, r.patient_id " +
                        " from  " +
                        " responsible r, " +
                        " patient p " +
                        " where " +
                        " r.patient_id = p.id " +
                        " and p.name like ? ";
      return jdbcTemplate.query(query, (rs, rowNum) -> createResponsible(rs), new Object[]{"%"+patientName+"%"});
    }

    private Responsible createResponsible(ResultSet rs) throws SQLException {
        return new Responsible(
            rs.getString("id"), 
            rs.getString("name"), 
            rs.getString("cpf"), 
            rs.getString("rg"), 
            (LocalDate.parse(rs.getString("date_birth"))), 
            rs.getString("parentenge"),
            patientJDBC.findPatientById(rs.getString("patient_id")));
    }
    
}
