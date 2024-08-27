
package Controller;

import Dominio.Parking;
import Pantallas.VistaMenu;
import Servicios.Fachada;
import java.util.ArrayList;

public class ControladorVistaMenu {
    private final VistaMenu vista;
    private final Fachada servicios = Fachada.getInstancia();
    public ControladorVistaMenu(VistaMenu vista) {
        this.vista = vista;
    }

    public ArrayList<Parking> getParkings() {
        return servicios.getParkings();
    }
    
}
