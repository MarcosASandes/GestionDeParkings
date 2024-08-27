package Dominio;

import Excepciones.PropietarioException;
import Excepciones.TipoVehiculoException;
import Excepciones.VehiculoException;
import java.util.ArrayList;

public class Propietario {
    private String Cedula;
    private String NombreCompleto;
    private double CuentaCorriente;
    private ArrayList<Vehiculo> vehiculos;
        
    public Propietario(String pCedula, String pNombre){
        this.Cedula = pCedula;
        this.NombreCompleto = pNombre;
        this.CuentaCorriente = 0;
        this.vehiculos = new ArrayList<Vehiculo>();
    }

    
    public void pagarEstadia(double valor){
        double valorRestante = this.CuentaCorriente -= valor;
        if(valorRestante < -10){
            this.CuentaCorriente = -10;
        }else{
            this.CuentaCorriente = valorRestante;
        }
    }
    
    public void Validar() throws PropietarioException{
        try{
            this.ValidarNombre();
            this.ValidarCI();
        } catch (PropietarioException e) {
            throw new PropietarioException(e.getMessage());
        }
    }
    
    private void ValidarNombre() throws PropietarioException{
        if (this.NombreCompleto == null) {
            throw new PropietarioException("El nombre no puede ser nulo.");
        }
    }
    
    private void ValidarCI() throws PropietarioException{
        if (this.Cedula == null) {
            throw new PropietarioException("La cedula no puede ser nula.");
        }
    }
    
    
    
    
    
    /**
     * @return the Cedula
     */
    public String getCedula() {
        return Cedula;
    }

    /**
     * @param Cedula the Cedula to set
     */
    public void setCedula(String Cedula) {
        this.Cedula = Cedula;
    }

    /**
     * @return the NombreCompleto
     */
    public String getNombreCompleto() {
        return NombreCompleto;
    }

    /**
     * @param NombreCompleto the NombreCompleto to set
     */
    public void setNombreCompleto(String NombreCompleto) {
        this.NombreCompleto = NombreCompleto;
    }

    /**
     * @return the CuentaCorriente
     */
    public double getCuentaCorriente() {
        return CuentaCorriente;
    }

    /**
     * @param CuentaCorriente the CuentaCorriente to set
     */
    public void setCuentaCorriente(double CuentaCorriente) {
        this.CuentaCorriente = CuentaCorriente;
    }
    
    
    public ArrayList<Vehiculo> getVehiculos(){
        return this.vehiculos;
    }
    
    public void addVehiculo(Vehiculo v) throws VehiculoException, PropietarioException, TipoVehiculoException{
        if(v != null){
            v.Validar();
            this.vehiculos.add(v);
        }else{
            throw new IllegalArgumentException("El vehiculo pasado es nulo.");
        }
    }

}
