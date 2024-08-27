/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Pantallas;

import java.util.ArrayList;
import simuladortransito.Estacionable;
import simuladortransito.Transitable;

/**
 *
 * @author Usuario
 */
public interface VistaIngresoEgreso {

    public void mostrarVehiculosCocheras(ArrayList<Estacionable> cocheras, ArrayList<Transitable> vehiculos);
    
}
