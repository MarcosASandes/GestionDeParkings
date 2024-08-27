
package Dominio;

import Pantallas.UIConsola;
import Servicios.Fachada;

public class TipoTendencia {
    private String EstadoTendencia;
    private static TipoTendencia instancia = new TipoTendencia();
    
    public static TipoTendencia getInstancia() {
        return instancia;
    }

    public TipoTendencia() {
        EstadoTendencia = "Estable";
    }
    
    
    public String getEstado(){
        return this.EstadoTendencia;
    }
    
    public void setEstado(String estado){
        this.EstadoTendencia = estado;
    }

    
    

}
