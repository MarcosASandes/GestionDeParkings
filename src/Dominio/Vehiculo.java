package Dominio;

import Excepciones.EtiquetaException;
import Excepciones.PropietarioException;
import Excepciones.TipoVehiculoException;
import Excepciones.VehiculoException;
import java.util.ArrayList;
import java.util.Objects;
import simuladortransito.Transitable;

public class Vehiculo implements Transitable {
    private String Patente;
    private Propietario prop;
    private TipoVehiculo tipoVehiculo;
    private ArrayList<Etiqueta> etiquetas;
    
    public Vehiculo(String pPatente, TipoVehiculo pTipo, Propietario propietario){
        this.Patente = pPatente;
        this.prop = propietario;
        this.tipoVehiculo = pTipo;
        this.etiquetas = new ArrayList<Etiqueta>();
    }



    /**
     * @param Patente the Patente to set
     */
    public void setPatente(String Patente) {
        this.Patente = Patente;
    }

    /**
     * @return the tipoVehiculo
     */
    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    /**
     * @param tipoVehiculo the tipoVehiculo to set
     */
    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    @Override
    public String getPatente() {
        return this.Patente;
    }

    @Override
    public boolean esDiscapacitado() {
        boolean encontrado = false;
        for(Etiqueta et : etiquetas){
            if(et.getNombre() == "Discapacitado"){
                encontrado = true;
            }
        }
        return encontrado;
    }

    @Override
    public boolean esElectrico() {
        boolean encontrado = false;
        for(Etiqueta et : etiquetas){
            if(et.getNombre() == "Electrico"){
                encontrado = true;
            }
        }
        return encontrado;
    }

    @Override
    public boolean esEmpleado() {
        boolean encontrado = false;
        for(Etiqueta et : etiquetas){
            if(et.getNombre() == "Empleado"){
                encontrado = true;
            }
        }
        return encontrado;
    }
    
    
    public void agregarEtiqueta(Etiqueta e) throws EtiquetaException{
        try{
            e.Validar();
            this.etiquetas.add(e);
        } catch(EtiquetaException ex){
            throw new EtiquetaException(ex.getMessage());
        }
    }
    
    
    public void Validar() throws VehiculoException, PropietarioException, TipoVehiculoException{
        try{
            this.ValidarPatente();
            this.ValidarPropietario();
            this.ValidarTipoVehiculo();
        } catch (VehiculoException e) {
            throw new VehiculoException (e.getMessage());
        }
    }
    
    private void ValidarPatente() throws VehiculoException{
        if (this.Patente == null) {
            throw new VehiculoException("La patente del vehiculo no puede ser nula.");
        }
    }
    
    private void ValidarPropietario() throws VehiculoException, PropietarioException{
        if (this.prop == null) {
            throw new VehiculoException("El propietario del vehiculo no puede ser nulo.");
        }else{
            this.prop.Validar();
        }
    }
    
    private void ValidarTipoVehiculo() throws VehiculoException, TipoVehiculoException, TipoVehiculoException{
        if (this.tipoVehiculo == null) {
            throw new VehiculoException("El tipo de vehiculo no puede ser nulo.");
        }else{
            this.tipoVehiculo.Validar();
        }
    }
    
    
    public Propietario getPropietario(){
        return this.prop;
    }
    
    public void setPropietario(Propietario p){
        this.prop = p;
    }
    
    public ArrayList<Etiqueta> getEtiquetas(){
        return this.etiquetas;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vehiculo vehiculo = (Vehiculo) o;
        return Objects.equals(Patente, vehiculo.Patente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Patente);
    }
    
}
