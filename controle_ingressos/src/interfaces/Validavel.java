package interfaces;

import exception.IngressoInvalidoException;
import model.Ingresso;

public interface Validavel {

    public void validarNaPortaria(Ingresso ingresso) throws IngressoInvalidoException;
    public String formatarStatus(String StatusIngresso);

}
