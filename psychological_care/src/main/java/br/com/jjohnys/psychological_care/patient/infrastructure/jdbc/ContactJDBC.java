package br.com.jjohnys.psychological_care.patient.infrastructure.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import br.com.jjohnys.psychological_care.patient.domain.Contact;
import br.com.jjohnys.psychological_care.patient.gateways.ContactRepository;

@Component
public class ContactJDBC implements ContactRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int insertContact(Contact contact) {
        return jdbcTemplate.update("insert into contact (id, name, email, telephone, patient_id, responsible_id) values (?, ?, ?, ?, ?, ?, ?)", contact.getId(), contact.getName(), contact.getEmail(), contact.getTelephone(), contact.getPatientId(), contact.getResponsibleId());        
    }

    public int updateContact(Contact contact) {
        return jdbcTemplate.update("update contact set name  = ?, email = ?, telephone = ?, patient_id = ?, responsible_id = ? where id = ?", contact.getName(), contact.getEmail(), contact.getTelephone(), contact.getPatientId(), contact.getId(), contact.getResponsibleId());
    }

    public List<Contact> getContactById(String id) {
        String query = "select * from contact where id = '" + id + "'";
        return jdbcTemplate.query(query, (rs, rowNum) -> createContact(rs));
    }

    public List<Contact> getContactByPatientId(String patientId) {
        String query = "select * from contact where patient_id = '" + patientId + "'";
        return jdbcTemplate.query(query, (rs, rowNum) -> createContact(rs));
    }

    public List<Contact> getContactByResponsibelId(String responsibelId) {
        String query = "select * from contact where responsible_id = '" + responsibelId + "'";
        return jdbcTemplate.query(query, (rs, rowNum) -> createContact(rs));
    }

    @Override
    public void saveAll(List<Contact> contacts) {
        contacts.forEach(contact -> this.insertContact(contact));
    }

    @Override
    public void updateAll(List<Contact> contacts) {
        contacts.forEach(contact -> this.updateContact(contact));
    }

    private Contact createContact(ResultSet rs) throws SQLException {

        if(!rs.getString("patient_id").isBlank())
            return Contact.buildPatientContact(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("telephone"),
                            rs.getString("patient_id"));
        else {
            return Contact.buildResponsibleContact(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("telephone"),
                            rs.getString("responsible_id"));
        }                            

    }

    
    
}
