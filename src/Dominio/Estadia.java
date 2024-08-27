package Dominio;
import Excepciones.CocheraException;
import Excepciones.MultaException;
import Excepciones.ParkingException;
import Excepciones.PropietarioException;
import Excepciones.TipoVehiculoException;
import Excepciones.VehiculoException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Estadia {

    private LocalDateTime Entrada;
    private LocalDateTime Salida;
    private double VFacturado;
    private double FactorDemandaEstadia;
    private Vehiculo vehiculo;
    private Cochera cochera;
    private ArrayList<Multa> multas;

    public Estadia(Vehiculo pVehiculo, Cochera pCochera, double pFactorDemanda) {
        this.Entrada = LocalDateTime.now();
        this.FactorDemandaEstadia = pFactorDemanda;
        this.vehiculo = pVehiculo;
        this.cochera = pCochera;
        this.Salida = null;
        this.VFacturado = 0;
        this.multas = new ArrayList<Multa>();
    }

    /*
    Una vez que se finalice la estadia, se invoca este metodo y este es el encargado de crear
    las multas que corresponda y agregarlas a la lista.
    */
    private void detectarMultas() throws MultaException, VehiculoException, CocheraException, ParkingException, PropietarioException, TipoVehiculoException {
        try {
            if (this.cochera.getEtiquetas().size() > 0) {
                for (Etiqueta cochETQ : this.cochera.getEtiquetas()) {
                    if (!this.vehiculo.getEtiquetas().contains(cochETQ)) {
                        if (cochETQ.getNombre() == "Discapacitado") {
                            MultaDiscapacitado nuevaMulta = new MultaDiscapacitado("Discapacitado", this);
                            this.agregarMulta(nuevaMulta);
                        } else if (cochETQ.getNombre() == "Electrico") {
                            MultaVehiculoElectrico nuevaMulta = new MultaVehiculoElectrico("Vehiculo electrico", this);
                            this.agregarMulta(nuevaMulta);
                        } else if (cochETQ.getNombre() == "Empleado") {
                            MultaEmpleado nuevaMulta = new MultaEmpleado("Lugar empleado", this);
                            this.agregarMulta(nuevaMulta);
                        } else {
                            throw new MultaException("Etiqueta no reconocida: " + cochETQ.getNombre());
                        }
                    }

                }
            }
        } catch (VehiculoException ex1) {
            // Captura VehiculoException
            throw new MultaException("Error de vehículo al procesar: " + ex1.getMessage(), ex1);
        } catch (MultaException ex2) {
            // Captura MultaException
            throw new MultaException("Error de multa al procesar: " + ex2.getMessage(), ex2);
        } catch (IllegalArgumentException e) {
            // Captura IllegalArgumentException
            throw new MultaException("No se pudieron detectar multas: " + e.getMessage(), e);
        }

    }

    private void agregarMulta(Multa m) throws MultaException, VehiculoException, CocheraException, ParkingException, PropietarioException, TipoVehiculoException {
        if (m != null) {
            m.Validar();
            this.multas.add(m);
        } else {
            throw new MultaException("La multa no puede ser nula.");
        }
    }

    public double CalcularTotal() {
        return CalcularValorEstadia() + GetMontoInfracciones();
    }

    public double CalcularValorEstadia() {
        double valorEstadiaSinMultas = 0;
        if (this.Salida != null) {
            double PB = this.cochera.getParking().getTarifaPorTipoVehiculo(this.vehiculo.getTipoVehiculo());
            double FD = this.FactorDemandaEstadia;
            Duration duration = Duration.between(this.Entrada, this.Salida);
            int cantUT = UT.getInstancia().convertir_a_ut((int) duration.getSeconds());

            valorEstadiaSinMultas = (PB * cantUT * FD);
        }

        return valorEstadiaSinMultas;
    }

    public void finalizarEstadia() throws MultaException, VehiculoException, CocheraException, ParkingException, PropietarioException, TipoVehiculoException {
        this.setSalida(LocalDateTime.now());
        this.cochera.setLibre();
        detectarMultas();
        this.setVFacturado(this.CalcularTotal());
        this.vehiculo.getPropietario().pagarEstadia(this.VFacturado);
    }

    public int getDuracionEstadiaEnUT() {
        Duration duration = Duration.between(this.Entrada, this.Salida);
        int cantUT = UT.getInstancia().convertir_a_ut((int) duration.getSeconds());
        return cantUT;
    }

    public double GetMontoInfracciones() {
        double total = 0;
        if (!this.multas.isEmpty()) {
            for (Multa m : multas) {
                total += m.CalcularMonto(this);
            }
        }

        return total;
    }

    public void Validar() throws VehiculoException, CocheraException, ParkingException, PropietarioException, TipoVehiculoException {
        this.ValidarVehiculo();
        this.ValidarCochera();
    }

    private void ValidarVehiculo() throws VehiculoException, PropietarioException, PropietarioException, TipoVehiculoException {
        if (this.vehiculo == null) {
            throw new VehiculoException("El vehiculo no puede ser nulo.");
        } else {
            this.vehiculo.Validar();
        }
    }

    private void ValidarCochera() throws CocheraException, ParkingException {
        if (this.cochera == null) {
            throw new CocheraException("La cochera no puede ser nula.");
        } else {
            this.cochera.Validar();
        }
    }

    /**
     * @return the Entrada
     */
    public LocalDateTime getEntrada() {
        return Entrada;
    }

    /**
     * @param Entrada the Entrada to set
     */
    public void setEntrada(LocalDateTime Entrada) {
        this.Entrada = Entrada;
    }

    /**
     * @return the Salida
     */
    public LocalDateTime getSalida() {
        return Salida;
    }

    /**
     * @param Salida the Salida to set
     */
    public void setSalida(LocalDateTime Salida) {
        this.Salida = Salida;
    }

    /**
     * @return the VFacturado
     */
    public double getVFacturado() {
        return VFacturado;
    }

    /**
     * @param VFacturado the VFacturado to set
     */
    public void setVFacturado(double VFacturado) {
        this.VFacturado = VFacturado;
    }

    /**
     * @return the vehiculo
     */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    /**
     * @param vehiculo the vehiculo to set
     */
    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    /**
     * @return the cochera
     */
    public Cochera getCochera() {
        return cochera;
    }

    /**
     * @param cochera the cochera to set
     */
    public void setCochera(Cochera cochera) {
        this.cochera = cochera;
    }

}
