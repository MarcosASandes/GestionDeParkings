/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *
 * @author Usuario
 */
public class AnomaliaException extends Exception {
    public AnomaliaException(String message) {
        super(message);
    }

    public AnomaliaException(String message, Throwable cause) {
        super(message, cause);
    }
}
