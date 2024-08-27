package Dominio;

import Excepciones.AnomaliaException;
import Excepciones.CocheraException;
import Excepciones.EstadiaException;
import Excepciones.MultaException;
import Excepciones.ParkingException;
import Excepciones.PropietarioException;
import Excepciones.TipoVehiculoException;
import Excepciones.VehiculoException;
import Observador.Observable;
import Servicios.Fachada;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import simuladortransito.Estacionable;

public class Parking extends Observable {

    private String Nombre;
    private String Direccion;
    private TipoTendencia tp;
    private double FactorDemanda;
    private ArrayList<Cochera> listaCochera;
    private ArrayList<Tarifario> tarifario;
    private ContextoFactorDemanda context;
    private ArrayList<Estadia> listaEstadia;
    private ArrayList<Anomalia> anomalias;
    
    public Parking(String pNombre, String pDir) {
        this.context = new ContextoFactorDemanda();
        this.tp = new TipoTendencia();
        this.FactorDemanda = 1;
        this.Nombre = pNombre;
        this.Direccion = pDir;
        this.listaCochera = new ArrayList<Cochera>();
        this.tarifario = new ArrayList<Tarifario>();
        this.listaEstadia = new ArrayList<Estadia>();
        this.anomalias = new ArrayList<Anomalia>();
    }
    
    public ArrayList<Estacionable> getEstacionables(){
        ArrayList<Estacionable> estacionables = new ArrayList<Estacionable>();
        for(Cochera c : this.listaCochera){
            estacionables.add((Estacionable)c);
        }
        return estacionables;
    }
    
   /*
    Verifica si falta alguna etiqueta en el parking, es decir, entre todas las cocheras,
    aparecen todas las etiquetas, si no es asi, devuelve las etiquetas que faltan.
    */
    public ArrayList<Etiqueta> verificarEtiquetasFaltantes(ArrayList<Etiqueta> todasLasEtiquetas) {
        Set<Etiqueta> etiquetasEncontradas = new HashSet<>();

        for (Cochera cochera : this.listaCochera) {
            etiquetasEncontradas.addAll(cochera.getEtiquetas());
        }

        ArrayList<Etiqueta> etiquetasFaltantes = new ArrayList<>();
        for (Etiqueta etiqueta : todasLasEtiquetas) {
            if (!etiquetasEncontradas.contains(etiqueta)) {
                etiquetasFaltantes.add(etiqueta);
            }
        }

        return etiquetasFaltantes;
    }
    

    private void agregarAnomalia(Anomalia a) throws AnomaliaException, CocheraException, ParkingException, PropietarioException, TipoVehiculoException {
    if (a != null) {
        try {
            a.Validar();
            this.anomalias.add(a);
        } catch (VehiculoException | EstadiaException ex) {
            throw new AnomaliaException("Error al validar la anomalia: " + ex.getMessage(), ex);
        }
    } else {
        throw new AnomaliaException("Error al validar la anomalia, no puede ser nula");
    }
}
    
    public void finalizarEstadiaAnomala(String codigo, Estadia e) throws AnomaliaException, CocheraException, ParkingException, PropietarioException, TipoVehiculoException {
        switch (codigo) {
            case "HOUDINI":
                e.setSalida(null);
                e.getCochera().setLibre();
                e.setVFacturado(0);
                Anomalia anomalia1 = new Anomalia(LocalDateTime.now(), "HOUDINI", e);
                this.agregarAnomalia(anomalia1);
                avisar(TipoAviso.ANOMALIA_AGREGADA);
                break;
            case "MISTERY":
                e.setSalida(LocalDateTime.now());
                e.getCochera().setLibre();
                e.setVFacturado(0);
                Anomalia anomalia2 = new Anomalia(LocalDateTime.now(), "MISTERY", e);
                this.agregarAnomalia(anomalia2);
                avisar(TipoAviso.ANOMALIA_AGREGADA);
                break;
            case "TRANSPORTADOR1":
                e.setSalida(LocalDateTime.now());
                e.getCochera().setLibre();
                e.setVFacturado(0);
                Anomalia anomalia3 = new Anomalia(LocalDateTime.now(), "TRANSPORTADOR1", e);
                this.agregarAnomalia(anomalia3);
                avisar(TipoAviso.ANOMALIA_AGREGADA);
                break;
            case "TRANSPORTADOR2":
                e.setSalida(LocalDateTime.now());
                e.getCochera().setLibre();
                e.setVFacturado(0);
                Anomalia anomalia4 = new Anomalia(LocalDateTime.now(), "TRANSPORTADOR2", e);
                this.agregarAnomalia(anomalia4);
                avisar(TipoAviso.ANOMALIA_AGREGADA);
                break;
            default:
                // Si el código no coincide con ninguna opción, no se hace nada
                break;
        }
    }

    public ArrayList<Anomalia> getAnomalias() {
        return this.anomalias;
    }
    
    public Estadia finalizarEstadia(Estadia e) throws MultaException, AnomaliaException, VehiculoException, CocheraException, ParkingException, PropietarioException, PropietarioException, TipoVehiculoException {
        Estadia encontrada = null;
        
        for (Estadia est : this.listaEstadia) {
            if (est.getSalida() == null && est.getCochera().equals(e.getCochera())
                    && est.getCochera().getParking().equals(e.getCochera().getParking())) {
                if (est.getVehiculo().getPatente().equals(e.getVehiculo().getPatente())) {
                    est.finalizarEstadia();
                    encontrada = est;
                    avisar(TipoAviso.ESTADIA_FINALIZADA);
                } else {
                    this.finalizarEstadiaAnomala("TRANSPORTADOR1", est);
                }
            }
        }
        return encontrada;
    }
    public ArrayList<Estadia> getListaEstadiaUltimos10Ingresos() {
        // Obtener la fecha y hora actual
        LocalDateTime ahora = LocalDateTime.now();
        // Calcular la fecha y hora hace 10 segundos
        LocalDateTime hace10Segundos = ahora.minusSeconds(UT.getInstancia().convertir_a_ut(10));

        // Crear una lista para almacenar los elementos de los últimos 10 segundos
        ArrayList<Estadia> ingresosUltimos10Segundos = new ArrayList<>();

        for (Estadia est : this.listaEstadia) {
            // Comprobar si el elemento es posterior a hace10Segundos
            if (est.getEntrada().isAfter(hace10Segundos) && est.getCochera().getParking().equals(this)) {
                ingresosUltimos10Segundos.add(est);
                //UIConsola.println(
            }
        }
        return ingresosUltimos10Segundos;
    }
    public ArrayList<Estadia> getListaEstadiaUltimos10Egresos() {
        // Obtener la fecha y hora actual
        LocalDateTime ahora = LocalDateTime.now();
        // Calcular la fecha y hora hace 10 segundos
        LocalDateTime hace10Segundos = ahora.minusSeconds(UT.getInstancia().convertir_a_ut(10));

        // Crear una lista para almacenar los elementos de los últimos 10 segundos
        ArrayList<Estadia> egresosUltimos10Segundos = new ArrayList<>();
        for (Estadia est : this.listaEstadia) {
            // Comprobar si el elemento es posterior a hace10Segundos
            if (est.getSalida() != null && est.getSalida().isAfter(hace10Segundos) && est.getCochera().getParking().equals(this)) {
                egresosUltimos10Segundos.add(est);
            }
        }
        return egresosUltimos10Segundos;
    }
    
    public ArrayList<Tarifario> getTarifario() {
        return tarifario;
    }

    public ArrayList<Cochera> getCocheras() {
        return this.listaCochera;
    }

    public ArrayList<Estadia> getEstadias() {
        return this.listaEstadia;
    }

    public int getCapacidad() {
        return listaCochera.size();
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public void agregarCochera(Cochera c) {
        if (c != null) {
            listaCochera.add(c);
        } else {
            throw new IllegalArgumentException("La cochera no puede ser nula.");
        }

    }

    public void agregarEstadia(Estadia e) throws VehiculoException, CocheraException, ParkingException, PropietarioException, TipoVehiculoException {
        if (e != null) {
            e.Validar();
            if (this.listaEstadia.add(e)) {
                e.getCochera().setOcupada();
                avisar(TipoAviso.ESTADIA_AGREGADA);
        }
        } else {
            throw new IllegalArgumentException("La estadia no puede ser nula.");
        }
    }

    public ArrayList<Cochera> getOcupadas() {
        ArrayList<Cochera> ocupadas = new ArrayList<>();

        for (Cochera c : listaCochera) {
            if (c.isOcupada()) {
                ocupadas.add(c);
            }
        }
        return ocupadas;
    }

    public ArrayList<Cochera> getLibres() {
        ArrayList<Cochera> libres = new ArrayList<>();

        for (Cochera c : listaCochera) {
            if (!c.isOcupada()) {
                libres.add(c);
            }
        }
        return libres;
    }

    public String getEstado() {
        return this.tp.getEstado();
    }

    public double getFactorDemanda() {
        return this.FactorDemanda;
    }

    public TipoTendencia getTipoTendencia() {
        return this.tp;
    }

    public void actualizarTarifa(double nuevaTarifa, TipoVehiculo tipov) {
        if (tipov != null) {
            for (Tarifario t : this.tarifario) {
                if (t.getTipoVehiculo().getDescripcion().equals(tipov.getDescripcion())) {
                    t.EstablecerTarifa(nuevaTarifa);
                    avisar(TipoAviso.TARIFA_ACTUALIZADA);
                }
            }
        }
    }

    public void RecalcularTendencia(Parking pPark) {
        int espacioParking = pPark.getCapacidad();
        Fachada f = Fachada.getInstancia();
        int diferencia = pPark.getListaEstadiaUltimos10Ingresos().size() - pPark.getListaEstadiaUltimos10Egresos().size();
        double porcentajeParking = espacioParking * 0.10;
        if (diferencia > 0 && diferencia <= porcentajeParking) {
            this.tp.setEstado("Estable");
        } else if (diferencia > 0 && diferencia > porcentajeParking) {
            this.tp.setEstado("Positiva");
        } else if (diferencia < 0) {
            this.tp.setEstado("Negativa");
        }
    }

    public void Validar() throws ParkingException {
        try {
            this.ValidarNombre();
            this.ValidarDireccion();
        } catch (ParkingException e) {
            throw new ParkingException(e.getMessage());
        }
    }

    private void ValidarNombre() throws ParkingException {
        if (this.Nombre == null) {
            throw new ParkingException("El nombre no puede ser nulo.");
        }
    }

    private void ValidarDireccion() throws ParkingException {
        if (this.Direccion == null) {
            throw new ParkingException("La direccion no puede ser nula.");
        }
    }

    private void setEstrategia(String estado) {
        switch (estado) {
            case "Estable":
                this.context.cambiarEstrategia(new FormulaEstable());
                break;
            case "Positiva":
                this.context.cambiarEstrategia(new FormulaPositiva());
                break;
            case "Negativa":
                this.context.cambiarEstrategia(new FormulaNegativa());
                break;
        }
    }

    public void actualizarFactorDemanda(String estado, int cantSegundos) {
        setEstrategia(estado);
        this.FactorDemanda = this.context.ejecutarEstrategia(this, cantSegundos);
    }

    public double getTarifaPorTipoVehiculo(TipoVehiculo tp) {
        double tarifa = 0;

        for (Tarifario t : tarifario) {
            if (t.getTipoVehiculo().equals(tp)) {
                tarifa = t.getTarifa();
            }
        }

        return tarifa;
    }

    public double calcularPorcentajeOcupacion() {
        double porcentaje = 0;
        int totalCocheras = this.getCapacidad();

        if (totalCocheras == 0) {
            return porcentaje;
        }

        int cocherasOcupadas = 0;
        for (Cochera cochera : listaCochera) {
            if (cochera.isOcupada()) {
                cocherasOcupadas++;
            }
        }

        return (cocherasOcupadas / (double) totalCocheras) * 100;
    }

    public void agregarTarifarios(ArrayList<Tarifario> tarifarios) {
        this.tarifario.addAll(tarifarios);
    }

    // Implementación del método equals()
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Parking parking = (Parking) o;
        return Objects.equals(Nombre, parking.Nombre) && Objects.equals(Direccion, parking.Direccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Nombre, Direccion);
    }

    public int getDisponibilidadDiscapacitado() {
        int contador = 0;
        for (Cochera cochera : listaCochera) {
            if (!cochera.isOcupada() && cochera.esDiscapacitado()) {
                contador++;
            }
        }
        return contador;
    }

    public int getDisponibilidadElectrico() {
        int contador = 0;
        for (Cochera cochera : listaCochera) {
            if (!cochera.isOcupada() && cochera.esElectrico()) {
                contador++;
            }
        }
        return contador;
    }

    public int getDisponibilidadEmpleado() {
        int contador = 0;
        for (Cochera cochera : listaCochera) {
            if (!cochera.isOcupada() && cochera.esEmpleado()) {
                contador++;
            }
        }
        return contador;
    }

    public double getSubtotalParkingMultas() {
        double monto = 0;
        for (Estadia est : this.listaEstadia) {
            if (est.GetMontoInfracciones() != 0) {
                monto += est.GetMontoInfracciones();
            }
        }
        return monto;
    }

    public double getSubtotalParking() {
        double monto = 0;
        for (Estadia est : listaEstadia) {
            if (est.CalcularTotal() != 0) {
                monto += est.CalcularTotal();
            }
        }
        return monto;
    }
}
