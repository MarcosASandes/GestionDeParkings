/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Pantallas;

import Dominio.Estadia;
import Dominio.Parking;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public interface VistaTableroDeControl {

    public void mostrarTotalEstadiasParking(ArrayList<Estadia> estadias);

    public void mostrarMontoTotalParking(double montoTotal);

    public void mostrarListaParking(ArrayList<Parking> parkings);

    public void mostrarListaAnomalias(ArrayList<Object[]> datos);
    
}
