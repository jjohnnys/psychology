package br.com.jjohnys.psychological_care.psychological_support.domain.value_objects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;

public class TELEFONE {

    private String telefone;

    public TELEFONE(String telefone) {
        Pattern pattern = Pattern.compile("\\(?\\d{2,}\\)?[ -]?\\d{5,}[\\-\\s]?\\d{4}");
        Matcher matcher = pattern.matcher(telefone);
        if(!matcher.find()) throw new BusinessExceptions(String.format("Telefone inválido %s", telefone));
        this.telefone = telefone;
    }

    public String get() {
        return this.telefone;
    }
}
