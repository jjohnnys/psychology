package br.com.jjohnys.psychological_care.psychological_support.domain.value_objects;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "cpf")
public class CPF {

    private String cpf;

    public CPF(String cpf) {
        CPFValidator cpfValidator = new CPFValidator(true);        
        try {
            cpfValidator.assertValid(cpf);            
        } catch (InvalidStateException e) {
            throw new BusinessExceptions(String.format("CPF Invalido %s", cpf));
        }
        this.cpf = cpf;
    }

    public String get() {
        return this.cpf;
    }

    
    
}
