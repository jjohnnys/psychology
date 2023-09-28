package br.com.jjohnys.psychological_care.patient.gateways;

import java.util.List;

import br.com.jjohnys.psychological_care.patient.domain.Contact;

public interface ContactRepository {

    int insertContact(Contact contact);
    int updateContact(Contact contact);
    List<Contact> getContactById(String id);
    
}
