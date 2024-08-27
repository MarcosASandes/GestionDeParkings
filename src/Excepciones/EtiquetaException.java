
package Excepciones;


public class EtiquetaException extends Exception{
    public EtiquetaException(String message) {
        super(message);
    }

    public EtiquetaException(String message, Throwable cause) {
        super(message, cause);
    }
}
