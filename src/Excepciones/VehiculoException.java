/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *
 * @author Marcos
 */
public class VehiculoException extends Exception {
    public VehiculoException(String message) {
        super(message);
    }

    public VehiculoException(String message, Throwable cause) {
        super(message, cause);
    }
}
