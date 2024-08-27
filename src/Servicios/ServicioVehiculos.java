
package Servicios;

import Dominio.Etiqueta;
import Dominio.TipoVehiculo;
import Dominio.Vehiculo;
import Excepciones.PropietarioException;
import Excepciones.TipoVehiculoException;
import Excepciones.VehiculoException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import simuladortransito.Transitable;

public class ServicioVehiculos {
    private ArrayList<Vehiculo> vehiculos;
    private ArrayList<TipoVehiculo> tiposVehiculos;
    private static ServicioVehiculos instancia = new ServicioVehiculos();

    
    public static ServicioVehiculos getInstancia() {
	return instancia;
    }

    private ServicioVehiculos() {
        this.vehiculos = new ArrayList<Vehiculo>();
        this.tiposVehiculos = new ArrayList<TipoVehiculo>();
    }
    
    public ArrayList<TipoVehiculo> getTiposDeVehiculo(){
        return this.tiposVehiculos;
    }
    
    public ArrayList<Transitable> getTransitables(){
        ArrayList<Transitable> transitables = new ArrayList<Transitable>();
        for(Vehiculo v : this.vehiculos){
            transitables.add((Transitable)v);
        }
        return transitables;
    }
    
    
    public void agregarVehiculo(Vehiculo pVehiculo) throws VehiculoException, PropietarioException, TipoVehiculoException {
        if (pVehiculo != null) {
            pVehiculo.Validar();
            this.vehiculos.add(pVehiculo);
        } else {
            throw new VehiculoException("El vehiculo no puede ser nulo.");
        } 
    }
    
    public void agregarVehiculo(ArrayList<Transitable> vehiculos){
        for(Transitable v : vehiculos){
            Vehiculo veh = (Vehiculo)v;
            this.vehiculos.add(veh);
        }
    }
    
    public void agregarTipoVehiculo(TipoVehiculo pTipoVehiculo) throws TipoVehiculoException {
        if (pTipoVehiculo != null) {
            pTipoVehiculo.Validar();
            this.tiposVehiculos.add(pTipoVehiculo);
        } else {
            throw new TipoVehiculoException("El tipo de vehiculo no puede ser nulo.");
        }     
    }
    
    public void agregarTipoVehiculo(ArrayList<TipoVehiculo> tipos){
        for(TipoVehiculo tp : tipos){
            this.tiposVehiculos.add(tp);
        }
    }
    
    
    public ArrayList<Etiqueta> verificarEtiquetasFaltantes(ArrayList<Etiqueta> todasLasEtiquetas) {
        Set<Etiqueta> etiquetasEncontradas = new HashSet<>();

        for (Vehiculo v : this.vehiculos) {
            etiquetasEncontradas.addAll(v.getEtiquetas());
        }

        ArrayList<Etiqueta> etiquetasFaltantes = new ArrayList<>();
        for (Etiqueta etiqueta : todasLasEtiquetas) {
            if (!etiquetasEncontradas.contains(etiqueta)) {
                etiquetasFaltantes.add(etiqueta);
            }
        }

        return etiquetasFaltantes;
    }
    
    
    public ArrayList<Vehiculo> getVehiculos(){
        return this.vehiculos;
    }
    
}
