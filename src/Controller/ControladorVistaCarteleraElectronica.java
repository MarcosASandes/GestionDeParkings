
package Controller;

import Dominio.Parking;
import Pantallas.VistaCarteleraElectronica;
import Servicios.Fachada;

public class ControladorVistaCarteleraElectronica{
    private final VistaCarteleraElectronica vista;
    private Fachada servicios = Fachada.getInstancia();
    private int fila;
    
    public ControladorVistaCarteleraElectronica(VistaCarteleraElectronica vista, int filaSeleccionada) {
        this.vista = vista;
        this.fila = filaSeleccionada;
        inicializarVista();
    }
    private void inicializarVista() {
        Parking parking = servicios.getParkings().get(fila);
        vista.mostrarDatosParking(parking, parking.getTarifario());
    }

}
