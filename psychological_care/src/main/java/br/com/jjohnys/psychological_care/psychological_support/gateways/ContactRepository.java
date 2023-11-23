package br.com.jjohnys.psychological_care.psychological_support.gateways;

import java.util.List;

import br.com.jjohnys.psychological_care.psychological_support.domain.Contact;

public interface ContactRepository {

    int insertContact(Contact contact);
    int updateContact(Contact contact);
    List<Contact> getContactById(String id);
    void saveAll(List<Contact> contacts);
    void updateAll(List<Contact> contacts);
    
}
