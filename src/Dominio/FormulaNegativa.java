
package Dominio;


/*
El factor de demanda se
reduce significativamente.
*/
public class FormulaNegativa implements FactorDemandaStrategy {

    @Override
    public double calcularFactorDemanda(Parking parking, int cantSegundos) {
        double factorActual = parking.getFactorDemanda();
        
        if(factorActual > 1){
            factorActual = 1;
        }
        else{
            double cantRestante = 0.05 * cantSegundos;
            factorActual -= cantRestante;
        }
        
        if(factorActual < 0.25){
            factorActual = 0.25;
        }
        return factorActual;
    }
    
}
