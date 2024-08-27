/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *
 * @author Usuario
 */
public class CocheraException extends Exception {
    public CocheraException(String message) {
        super(message);
    }

    public CocheraException(String message, Throwable cause) {
        super(message, cause);
    }
}
