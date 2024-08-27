
package Excepciones;


public class TarifarioException extends Exception{
    public TarifarioException(String message) {
        super(message);
    }

    public TarifarioException(String message, Throwable cause) {
        super(message, cause);
    }
}
