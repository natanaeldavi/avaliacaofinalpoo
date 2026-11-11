package interfaces;

import exception.IngressoInvalidoException;
import model.StatusIngresso;

public interface Validavel {

    public void validarNaPortaria() throws IngressoInvalidoException;
    public String formatarStatuis(String StatusIngresso);

}
