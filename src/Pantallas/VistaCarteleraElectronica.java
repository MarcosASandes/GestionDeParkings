/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Pantallas;

import Dominio.Parking;
import Dominio.Tarifario;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public interface VistaCarteleraElectronica {

    public void mostrarDatosParking(Parking parking, ArrayList<Tarifario> tarifas);
    
}
