package Servicios;

import Dominio.Anomalia;
import Dominio.Cochera;
import Servicios.ServicioParking;
import Servicios.ServicioPropietarios;
import Dominio.Estadia;
import Dominio.Etiqueta;
import Dominio.Parking;
import Dominio.Propietario;
import Dominio.TipoVehiculo;
import Dominio.Vehiculo;
import Excepciones.EstadiaException;
import Excepciones.ParkingException;
import Excepciones.PropietarioException;
import Excepciones.TipoVehiculoException;
import Excepciones.VehiculoException;
import Observador.Observable;
import Observador.Observador;
import Pantallas.UIConsola;
import java.util.ArrayList;
import java.util.Random;
import simuladortransito.Estacionable;
import simuladortransito.SimuladorTransito;
import simuladortransito.Transitable;

public class Fachada {

    private ServicioParking servicioParking = ServicioParking.getInstancia();
    private ServicioVehiculos servicioVehiculos = ServicioVehiculos.getInstancia();
    private ServicioPropietarios servicioPropietarios = ServicioPropietarios.getInstancia();
    private static Fachada instancia = new Fachada();

    public static Fachada getInstancia() {
        return instancia;
    }

    private Fachada() {
    }

    public ArrayList<Estadia> getEstadias() {
        return this.servicioParking.getListaEstadiasTotales();
    }

    public void agregarParking(Parking p) throws ParkingException {
        servicioParking.agregarParking(p);
    }

    public ArrayList<Parking> getParkings() {
        return servicioParking.getListaParking();
    }

    public void agregarPropietario(Propietario prop) throws PropietarioException {
        this.servicioPropietarios.agregarPropietario(prop);
    }

    public ArrayList<Propietario> getPropietarios() {
        return this.servicioPropietarios.getListaPropietario();
    }

    public void agregarVehiculo(Vehiculo v) throws VehiculoException, PropietarioException, TipoVehiculoException {
        this.servicioVehiculos.agregarVehiculo(v);
    }

    public void agregarTipoVehiculo(TipoVehiculo tp) throws TipoVehiculoException {
        this.servicioVehiculos.agregarTipoVehiculo(tp);
    }

    public void RecalcularTendencia(Parking pPark) {
        pPark.RecalcularTendencia(pPark);
    }

    public void agregarParking(ArrayList<Parking> parks) {
        this.servicioParking.agregarParking(parks);
    }

    public void agregarVehiculo(ArrayList<Transitable> vehiculos) {
        this.servicioVehiculos.agregarVehiculo(vehiculos);
    }

    public void agregarTipoVehiculo(ArrayList<TipoVehiculo> tipos) {
        this.servicioVehiculos.agregarTipoVehiculo(tipos);
    }

    public void agregarPropietario(ArrayList<Propietario> props) {
        this.servicioPropietarios.agregarPropietario(props);
    }

    public Estadia getUltimaEstadiaDeCochera(Cochera c) {
        return this.servicioParking.getUltimaEstadiaDeCochera(c);
    }

    public ArrayList<Anomalia> getAnomalias() {
        return this.servicioParking.getAnomalias();
    }

    public ArrayList<Transitable> getTransitables() {
        return this.servicioVehiculos.getTransitables();
    }

    public ArrayList<Estacionable> getEstacionables() {
        return this.servicioParking.getEstacionables();
    }

    public ArrayList<TipoVehiculo> getTiposDeVehiculo() {
        return this.servicioVehiculos.getTiposDeVehiculo();
    }

    public ArrayList<Etiqueta> verificarEtiquetasFaltantes(ArrayList<Etiqueta> todasLasEtiquetas) {
        return this.servicioVehiculos.verificarEtiquetasFaltantes(todasLasEtiquetas);
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return this.servicioVehiculos.getVehiculos();
    }

    public double CalcularPromedioTarifa(TipoVehiculo tp){
        return this.servicioParking.CalcularPromedioTarifa(tp);
    }
    
}
