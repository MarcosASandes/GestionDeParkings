
package Dominio;

public class ContextoFactorDemanda {
    private FactorDemandaStrategy estrategia;
    
    public ContextoFactorDemanda(){
        this.estrategia = new FormulaEstable();
    }
    
    public void cambiarEstrategia(FactorDemandaStrategy st){
        this.estrategia = st;
    }
    
    /*
        Metodo utilizado para calcular el factor demanda en base a la estrategia elegida.
        -Formula estable.
        -Formula positiva.
        -Formula negativa.
    */
    public double ejecutarEstrategia(Parking parking, int cantSegundos){
        return this.estrategia.calcularFactorDemanda(parking, cantSegundos);
    }
}
