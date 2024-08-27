package Dominio;

import Excepciones.TarifarioException;

public class Tarifario {
    private TipoVehiculo tp;
    private double Tarifa;

    
    public Tarifario(double pTarifa, TipoVehiculo tipoV){
        this.Tarifa = pTarifa;
        this.tp = tipoV;
    }
    
    public TipoVehiculo getTipoVehiculo(){
        return this.tp;
    }

    public TipoVehiculo getTp() {
        return tp;
    }
    
    
    public double getTarifa(){
        return this.Tarifa;
    }
    
    public void EstablecerTarifa(double pTarifa) {
        this.Tarifa = pTarifa;
    }
    
    public void Validar() throws TarifarioException{
        try{
            this.ValidarTarifa();
        } catch (TarifarioException e) {
            throw new TarifarioException(e.getMessage());
        }
    }
    
    private void ValidarTarifa() throws TarifarioException{
        if (this.Tarifa < 0) {
            throw new TarifarioException("La tarifa no puede ser negativa.");
        }
    }

}
