/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Observador;

/**
 *
 * @author Usuario
 */
public interface Observador {
    
    public enum Event {
        RESET,
        CAMBIO_VALOR, 
        HAY_RESULTADO,
    }
 
   public abstract void actualizar(Observable origen, Object evento);
}
