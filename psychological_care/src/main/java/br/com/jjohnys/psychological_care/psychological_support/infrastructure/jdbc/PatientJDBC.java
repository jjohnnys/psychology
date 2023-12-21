package br.com.jjohnys.psychological_care.psychological_support.infrastructure.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.jjohnys.psychological_care.psychological_support.domain.Contact;
import br.com.jjohnys.psychological_care.psychological_support.domain.Patient;
import br.com.jjohnys.psychological_care.psychological_support.domain.Responsible;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.GenderEnum;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.PatientStatusEnum;
import br.com.jjohnys.psychological_care.psychological_support.domain.value_objects.CPF;
import br.com.jjohnys.psychological_care.psychological_support.domain.value_objects.EMAIL;
import br.com.jjohnys.psychological_care.psychological_support.domain.value_objects.TELEFONE;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PatientRepository;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class PatientJDBC implements PatientRepository {

    JdbcTemplate jdbcTemplate;

    public void insertPatient(Patient patient, List<Contact> contactsPatient, List<Responsible> responsibles, List<Contact> contactsResponsible) {
        insertPatient(patient);
        saveAllContact(contactsPatient);
        if(responsibles != null) responsibles.forEach(responsible -> {            
            insertResponsible(responsible);
            saveAllContact(contactsResponsible);        
        });
    }

    private int insertPatient(Patient patient) {
        return jdbcTemplate.update("insert into patient (id, name, cpf, rg, date_birth, price, schooling, gender, address, status, observation) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            genereteID(patient.getId()), patient.getName(), patient.getCpf().get(), patient.getRg(), patient.getDateBirth(), patient.getPrice(), patient.getSchooling(), patient.getGender().getDescription(), patient.getAddress(), patient.getStatus().getStatus(), patient.getObservation());
    }

    
    private void saveAllContact(List<Contact> contacts) {
        contacts.forEach(contact -> this.insertContact(contact));
    }

    private int insertContact(Contact contact) {
        return jdbcTemplate.update("insert into contact (id, email, telephone, patient_id, responsible_id) values (?, ?, ?, ?, ?)", genereteID(contact.getId()), contact.getEmail().get(), contact.getTelephone().get(), contact.getPatientId(), contact.getResponsibleId());        
    }

    private void insertResponsible(Responsible responsible) {

        jdbcTemplate.update("insert into responsible (id, name, cpf, rg, date_birth, parentenge, patient_id) values (?, ?, ?, ?, ?, ?, ?)", 
        genereteID(responsible.getId()), responsible.getName(), responsible.getCpf().get(), responsible.getRg(), responsible.getDateBirth(), responsible.getParentege() , responsible.getPatient().getId());      

    }

    public void updatePatient(Patient patient, List<Contact> contactsPatient, List<Responsible> responsibles, List<Contact> contactsResponsible) {
        updatePatient(patient);
        contactsPatient.forEach(cp -> {
            if(!cp.getId().isBlank()) updateContact(cp);
            else insertContact(cp);
        });        
        if(responsibles != null) responsibles.forEach(responsible -> {
            if(responsible.getId() != null && !responsible.getId().isBlank()) updateResponsible(responsible);
            else insertResponsible(responsible);
            contactsResponsible.forEach(cr -> {
                if(!cr.getId().isBlank()) updateContact(cr);
                else insertContact(cr);
            });
        });
    }
    

    private int updatePatient(Patient patient) {
        String update = "update patient set name = ?, cpf = ?, rg = ?, date_birth = ?, price = ?, schooling = ?, gender = ?, address = ?, status = ?, observation = ? where id = ?";               
        return jdbcTemplate.update(update, patient.getName(), patient.getCpf().get(), patient.getRg(), patient.getDateBirth(), patient.getPrice(),  patient.getSchooling(), patient.getGender().getDescription(), patient.getAddress(), patient.getStatus().getStatus(),  patient.getObservation(), patient.getId());
    }

    private int updateContact(Contact contact) {
        return jdbcTemplate.update("update contact set email = ?, telephone = ?, patient_id = ?, responsible_id = ? where id = ?", contact.getEmail().get(), contact.getTelephone().get(), contact.getPatientId(), contact.getResponsibleId(), contact.getId());
    }

    private void updateResponsible(Responsible responsible) {
        jdbcTemplate.update("update responsible set name = ?, cpf = ?, rg = ?, date_birth = ?, parentenge = ?, patient_id = ? where id = ?",
         responsible.getName(), responsible.getCpf().get(), responsible.getRg(), responsible.getDateBirth(), responsible.getParentege(), responsible.getPatient().getId() , responsible.getId());
    }


    public List<Patient> findPatientByName(String name) {   
        try {     
            return jdbcTemplate.query("select * from patient where name like ?", (rs, rowNum) -> 
            createPatient(rs), new Object[]{"%"+name+"%"});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Patient findFullPatientById(String id) {
        Patient patient = findPatientById(id);
        List<Contact> contactsPatient = getContactByPatientId(id);        
        patient.addContacts(contactsPatient);
        List<Responsible> responsibles = findResponsiblesByPatientId(patient.getId());
        if(!responsibles.isEmpty()) {
            responsibles.forEach(responsible -> {
                List<Contact> contactsResponsible = getContactByResponsibelId(responsible.getId());
                responsible.addContacts(contactsResponsible);
            });
            patient.addAllResponsible(responsibles);
        }

        return patient;
    }

    public Patient findPatientById(String id) {
        try {
            return jdbcTemplate.queryForObject("select * from patient where id = ?", (rs, rowNum) -> createPatient(rs), new Object[]{id});            
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private List<Responsible> findResponsiblesByPatientId(String patientId) {
        String query = "select " +
                        " id, name, cpf, rg, date_birth, parentenge, patient_id " +
                        " from  " +
                        " responsible " +
                        " where " +
                        " patient_id = ? ";
      return jdbcTemplate.query(query, (rs, rowNum) -> createResponsible(rs), new Object[]{patientId});
    }

    private List<Contact> getContactByResponsibelId(String responsibelId) {
        String query = "select * from contact where responsible_id = '" + responsibelId + "'";
        return jdbcTemplate.query(query, (rs, rowNum) -> createContact(rs));
    }

    public Patient findPatientByCPF(CPF cpf) {
        try {            
            return jdbcTemplate.queryForObject("select * from patient where cpf = ?", (rs, rowNum) -> createPatient(rs), new Object[]{cpf.get()});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Contact> getContactByPatientId(String patientId) {
        String query = "select * from contact where patient_id = '" + patientId + "'";
        return jdbcTemplate.query(query, (rs, rowNum) -> createContact(rs));
    }

    public boolean existisOtherPatientWithSameCPF(String id, CPF cpf) {

        StringBuilder sb = new StringBuilder();
        sb.append("select * from patient where ");
        if(id != null && !id.isBlank()) sb.append("id <> '" + id + "' and ");            
        sb.append("cpf = '" + cpf.get() + "'");
        try {            
           return jdbcTemplate.queryForObject(sb.toString(), (rs, rowNum) -> createPatient(rs)) != null;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public boolean existisOtherResponsibleWithSameCPF(String id, CPF cpf) {

        StringBuilder sb = new StringBuilder();
        sb.append("select * from responsible where ");
        if(id != null && !id.isBlank()) sb.append("id <> '" + id + "' and ");            
        sb.append("cpf = '" + cpf.get() + "'");
        try {            
           return jdbcTemplate.queryForObject(sb.toString(), (rs, rowNum) -> createResponsible(rs)) != null;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    } 

    public int changeStatusPatient(String patientId, PatientStatusEnum status) {
        return jdbcTemplate.update("update patient set status = ? where id = ?", status.getStatus(), patientId);
    }


    private Patient createPatient(ResultSet rs) throws SQLException {
        return new Patient(
                rs.getString("id"), 
                rs.getString("name"), 
                new CPF(rs.getString("cpf")), 
                rs.getString("rg"), 
                (LocalDate.parse(rs.getString("date_birth"))),
                (rs.getInt("price")),
                rs.getString("schooling"),
                GenderEnum.getGenderEnum(rs.getString("gender")),
                rs.getString("address"),
                PatientStatusEnum.getStatusPatientEnum(rs.getString("status")),
                rs.getString("observation")
            );
    }

    private String genereteID(String id) {
        if(id == null || id.isBlank()) return UUID.randomUUID().toString();
        return id;
    }

    private Contact createContact(ResultSet rs) throws SQLException {

        if(rs.getString("patient_id") != null)
            return Contact.buildPatientContact(
                            rs.getString("id"),
                            new EMAIL(rs.getString("email")),
                            new TELEFONE(rs.getString("telephone")),
                            rs.getString("patient_id"));
        else {
            return Contact.buildResponsibleContact(
                            rs.getString("id"),
                            new EMAIL(rs.getString("email")),
                            new TELEFONE(rs.getString("telephone")),
                            rs.getString("responsible_id"));
        }                            
    }

    private Responsible createResponsible(ResultSet rs) throws SQLException {
        return new Responsible(
            rs.getString("id"), 
            rs.getString("name"), 
            new CPF(rs.getString("cpf")), 
            rs.getString("rg"), 
            (LocalDate.parse(rs.getString("date_birth"))), 
            rs.getString("parentenge"),
            findPatientById(rs.getString("patient_id")));
    } 
    
}
