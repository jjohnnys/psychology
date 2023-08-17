package br.com.jjohnys.psychological_care.patient.repository.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import br.com.jjohnys.psychological_care.patient.domain.Contact;
import br.com.jjohnys.psychological_care.patient.repository.ContactRepository;

@Component
public class ContactJDBC implements ContactRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int insertContact(Contact contact) {
        return jdbcTemplate.update("insert into contact (id, name, email, telephone, parentage, patient_id) values (?, ?, ?, ?, ?, ?)", contact.getId(), contact.getName(), contact.getEmail(), contact.getTelephone(), contact.getParentage(), contact.getPatientId());        
    }

    public int updateContact(Contact contact) {
        return jdbcTemplate.update("update contact set name  = ?, email = ?, telephone = ?, parentage = ?, patient_id = ? where id = ?", contact.getName(), contact.getEmail(), contact.getTelephone(), contact.getParentage(), contact.getPatientId(), contact.getId());
    }

    public List<Contact> getContactById(String id) {
        String query = "select * from contact where id = '" + id + "'";
        return jdbcTemplate.query(query, (rs, rowNum) -> 
            new Contact(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("telephone"),
                rs.getString("parentage"),
                rs.getString("patient_id")));
    }
    
}
