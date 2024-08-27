package Dominio;

import Excepciones.CocheraException;
import Excepciones.MultaException;
import Excepciones.ParkingException;
import Excepciones.PropietarioException;
import Excepciones.TipoVehiculoException;
import Excepciones.VehiculoException;

public abstract class Multa {
    private String Descripcion;
    private Estadia estadia;

    public abstract double CalcularMonto(Estadia pEstadia);

    public Multa(String desc, Estadia pEst){
        this.Descripcion = desc;
        this.estadia = pEst;
    }
    
    
    /**
     * @return the Descripcion
     */
    public String getDescripcion() {
        return Descripcion;
    }

    /**
     * @param Descripcion the Descripcion to set
     */
    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
    
    
    public Estadia getEstadia(){
        return this.estadia;
    }
    
    
    public void Validar() throws VehiculoException, CocheraException, ParkingException, PropietarioException, MultaException, TipoVehiculoException{
        try{
            ValidarDescripcion();
            ValidarEstadia();
        }catch(MultaException e){
            throw new MultaException(e.getMessage());
        }
    }
    
    private void ValidarDescripcion() throws MultaException{
        if(this.Descripcion == null){
            throw new MultaException("La descripcion es nula.");
        }
    }
    
    private void ValidarEstadia() throws VehiculoException, CocheraException, ParkingException, PropietarioException, TipoVehiculoException, MultaException{
        if(this.estadia != null){
            this.estadia.Validar();
        }else{
            throw new MultaException("La estadia es nula.");
        }
    }
    
    
}
