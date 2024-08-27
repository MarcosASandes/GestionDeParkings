
package Excepciones;


public class TipoVehiculoException extends Exception{
    public TipoVehiculoException(String message) {
        super(message);
    }

    public TipoVehiculoException(String message, Throwable cause) {
        super(message, cause);
    }
}
