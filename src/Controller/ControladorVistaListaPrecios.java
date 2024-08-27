package Controller;

import Dominio.Parking;
import Dominio.TipoAviso;
import Dominio.TipoVehiculo;
import Observador.Observable;
import Observador.Observador;
import Pantallas.VistaListaPrecios;
import Servicios.Fachada;

public class ControladorVistaListaPrecios implements Observador {

    private final VistaListaPrecios vista;
    private Fachada servicios = Fachada.getInstancia();
    private Parking parking;

    public ControladorVistaListaPrecios(VistaListaPrecios vista, int filaSeleccionada) {
        this.vista = vista;
        this.parking = servicios.getParkings().get(filaSeleccionada);
        this.parking.agregar(this);
        inicializarVista();
    }

    private void inicializarVista() {
        vista.mostrarDatosPrecios(parking, parking.getTarifario());
    }

    public double actualizarValorTarifa(int filaSeleccionadaTarifa, double nuevoValor) {
        TipoVehiculo tipoVehiculo = parking.getTarifario().get(filaSeleccionadaTarifa).getTipoVehiculo();
        double promedio = 2 * this.servicios.CalcularPromedioTarifa(tipoVehiculo);
        if (nuevoValor > 0 && nuevoValor < promedio) {
            parking.actualizarTarifa(nuevoValor, tipoVehiculo);
            return -1;
        }else{
            return promedio;
        }
    }

    @Override
    public void actualizar(Observable origen, Object evento) {
        if (TipoAviso.TARIFA_ACTUALIZADA.equals(evento)) {
            vista.mostrarDatosPrecios(parking, parking.getTarifario());
        }
    }

}
