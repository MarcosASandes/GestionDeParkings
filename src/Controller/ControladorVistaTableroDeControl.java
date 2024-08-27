
package Controller;

import Dominio.Anomalia;
import Dominio.Parking;
import Dominio.TipoAviso;
import Observador.Observable;
import Observador.Observador;
import Pantallas.VistaTableroDeControl;
import Servicios.Fachada;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ControladorVistaTableroDeControl implements Observador {

    private final VistaTableroDeControl vista;
    private final ArrayList<Parking> parkings;
    private final Fachada servicios = Fachada.getInstancia();
    private boolean checkboxSeleccionado = false;
    LocalDateTime comienzoLista = null;
    public ControladorVistaTableroDeControl(VistaTableroDeControl vista, ArrayList<Parking> parkings) {
        this.parkings = parkings;
        this.vista = vista;
        for (Parking p: this.parkings){
            p.agregar(this);
        }
    }
    public void setCheckboxSeleccionado(boolean seleccionado, LocalDateTime comienzoLista) {
        this.checkboxSeleccionado = seleccionado;
        this.comienzoLista = comienzoLista;
    }
    @Override
    public void actualizar(Observable origen, Object evento) {
        if (TipoAviso.ESTADIA_AGREGADA.equals(evento) || TipoAviso.ESTADIA_FINALIZADA.equals(evento)) { 
            generarVista();
        }
        else if (TipoAviso.ANOMALIA_AGREGADA.equals(evento) && checkboxSeleccionado) { 
            cargarListaAnomalia();
        }
    }
    private void generarVista() {
        vista.mostrarTotalEstadiasParking(servicios.getEstadias());
        vista.mostrarMontoTotalParking(obtenerMontoTotal());
        vista.mostrarListaParking(parkings);
    }

    public void cargarListaAnomalia() {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        ArrayList<Object[]> datos = new ArrayList<>();
        for (Anomalia a : servicios.getAnomalias()) {
            if (comienzoLista != null && comienzoLista.isBefore(a.getFecha()) || comienzoLista.isEqual(a.getFecha())){
                String fechaString = a.getFecha().format(formatter);
                Object[] fila = new Object[]{
                    fechaString,
                    a.getEstadia().getVehiculo().getPropietario().getNombreCompleto(),
                    a.getCodigoError(),
                    a.getEstadia().getCochera().getCodigo()
                };
                datos.add(fila);
            }
        }
        vista.mostrarListaAnomalias(datos);
    }

    private double obtenerMontoTotal() {
        double montoTotal=0;
        for (Parking p: this.parkings){
           montoTotal += p.getSubtotalParking();
        }
        return montoTotal;
    }

}
