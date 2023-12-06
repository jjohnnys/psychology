package br.com.jjohnys.psychological_care.psychological_support.domain.value_objects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;
import lombok.ToString;

@ToString
public class EMAIL {

    private String email;

    public EMAIL(String email) {
        Pattern pattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        if(!matcher.find()) throw new BusinessExceptions(String.format("Email inválido %s", email));
        this.email = email;
    }
    public String get() {
        return this.email;
    }    
}
