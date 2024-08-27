
package Dominio;
import Excepciones.AnomaliaException;
import Excepciones.VehiculoException;
import Servicios.Fachada;
import java.time.Duration;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import simuladortransito.Estacionable;
import simuladortransito.Sensor;
import simuladortransito.Transitable;


public class SensorSim implements Sensor {

    private Fachada servicios;
    private ArrayList<Evento> eventosRegistrados;

    public SensorSim() {
        this.servicios = Fachada.getInstancia();
        this.eventosRegistrados = new ArrayList<>();
    }

    @Override
    public void ingreso(Transitable transitable, Estacionable estacionable) {
        try {
            Vehiculo v = (Vehiculo) transitable;
            Cochera c = (Cochera) estacionable;
            Estadia estadiaNueva = new Estadia(v, c, c.getParking().getFactorDemanda());
            Ingreso nuevoIngreso = new Ingreso(estadiaNueva.getEntrada(), estadiaNueva);

            if (c.isOcupada()) {
                Estadia estadiaAnomala = servicios.getUltimaEstadiaDeCochera(c);
                c.getParking().finalizarEstadiaAnomala("HOUDINI", estadiaAnomala);
            }
            c.getParking().agregarEstadia(estadiaNueva);
            String estadoAnterior = c.getParking().getTipoTendencia().getEstado(); // ESTADO ANTERIOR AL CAMBIO DE TENDENCIA

            servicios.RecalcularTendencia(c.getParking());
            String estadoActual = c.getParking().getTipoTendencia().getEstado(); // ESTADO DE TENDENCIA CAMBIADO

            eventosRegistrados.add(nuevoIngreso);
            if (estadoAnterior != estadoActual) {
                Evento primerEvento = eventosRegistrados.get(0);
                int finalPos = eventosRegistrados.size() - 1;
                Evento penUltimoEvento = eventosRegistrados.get(finalPos - 1);

                Evento ultimoEvento = eventosRegistrados.get(finalPos);

                Duration duracion = Duration.between(primerEvento.getHoraEvento(), ultimoEvento.getHoraEvento());
                int duracionEnSegundos = (int) duracion.getSeconds();

                c.getParking().actualizarFactorDemanda(estadoAnterior, duracionEnSegundos);

                eventosRegistrados.clear();
            }
        } catch (VehiculoException | AnomaliaException ex) {
            // Manejo de la excepción AnomaliaException
            String errorMessage = "Error durante el ingreso: " + ex.getMessage();
            try {
                throw new AnomaliaException(errorMessage, ex);
            } catch (AnomaliaException ex1) {
                Logger.getLogger(SensorSim.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (Exception ex) {
            // Manejo de cualquier otra excepción no especificada
            throw new RuntimeException("Error durante el ingreso: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void egreso(Transitable transitable, Estacionable estacionable) {
        try {
            Vehiculo v = (Vehiculo) transitable;
            Cochera c = (Cochera) estacionable;
            Estadia estadiaSalidaAux = new Estadia(v, c, c.getParking().getFactorDemanda());

            if (!c.isOcupada()) {
                c.getParking().agregarEstadia(estadiaSalidaAux);// AGREGA EL INGRESO
                Estadia estadiaAnomala = servicios.getUltimaEstadiaDeCochera(c);
                c.getParking().finalizarEstadiaAnomala("MISTERY", estadiaAnomala);
            } else {
                Estadia estadiaSalidaEncontrada = null;
                estadiaSalidaEncontrada = c.getParking().finalizarEstadia(estadiaSalidaAux); // AGREGA EL EGRESO
                if (estadiaSalidaEncontrada == null) {
                    // AGREGA EL INGRESO
                    c.getParking().agregarEstadia(estadiaSalidaAux);
                    Estadia estadiaAnomala = servicios.getUltimaEstadiaDeCochera(c);
                    c.getParking().finalizarEstadiaAnomala("TRANSPORTADOR2", estadiaAnomala);

                } else {
                    Egreso nuevoEgreso = new Egreso(estadiaSalidaEncontrada.getSalida(), estadiaSalidaEncontrada);
                    String minutosSeg = nuevoEgreso.getHoraEvento().getMinute() + ":" + nuevoEgreso.getHoraEvento().getSecond();

                    String estadoAnterior = c.getParking().getTipoTendencia().getEstado(); // ESTADO ANTERIOR AL CAMBIO DE TENDENCIA

                    servicios.RecalcularTendencia(c.getParking());
                    String estadoActual = c.getParking().getTipoTendencia().getEstado(); // ESTADO DE TENDENCIA CAMBIADO

                    eventosRegistrados.add(nuevoEgreso);

                    if (estadoAnterior != estadoActual) { // SI SE CAMBIA LA TENDENCIA RECALCULAR FACTOR DEMANDA
                        Evento primerEvento = eventosRegistrados.get(0);
                        int finalPos = eventosRegistrados.size() - 1;
                        Evento penUltimoEvento = eventosRegistrados.get(finalPos - 1);

                        Evento ultimoEvento = eventosRegistrados.get(finalPos);

                        Duration duracion = Duration.between(primerEvento.getHoraEvento(), ultimoEvento.getHoraEvento());
                        int duracionEnSegundos = (int) duracion.getSeconds();

                        c.getParking().actualizarFactorDemanda(estadoAnterior, duracionEnSegundos);

                        eventosRegistrados.clear();
                    }
                }
            }
        } catch (VehiculoException ex) {
            // Manejo de excepciones específicas
            String errorMessage = "Error al finalizar el egreso: " + ex.getMessage();
            try {
                throw ex;
            } catch (VehiculoException ex1) {
                Logger.getLogger(SensorSim.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (Exception ex) {
            // Manejo de cualquier otra excepción no especificada
            throw new RuntimeException("Error inesperado durante el egreso: " + ex.getMessage(), ex);
        }

    }
}
