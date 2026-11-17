package interfaces;

import exception.CupomInvalidoException;
import java.util.Map;

public interface Promocional {

    public static final Map<String, Double> cuponsValidos = Map.of("FATEC10", 10.0, "FATEC20", 20.0, "PROMO5", 5.0);

    public double aplicaCupom(String codigoCupom) throws CupomInvalidoException;

    public static boolean verificaCupom(String codigoCupom) {
        if(codigoCupom != null && cuponsValidos.containsKey(codigoCupom.trim().toUpperCase())) { return true; } else { return false; }
    }
}
